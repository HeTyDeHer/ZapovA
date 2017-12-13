package testtask;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * Created by Алексей on 12.12.2017.
 */
public class Board {
    final ReentrantLock[][] board;


    public Board(int x, int y) {
        this.board = new ReentrantLock[x][y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                board[i][j] = new ReentrantLock();
            }
        }
    }
}

class Hero implements Runnable {
    private int xPos;
    private int yPos;
    private Board bg;

    public Hero(int xPos, int yPos, Board bg) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.bg = bg;
    }

    @Override
    public void run() {
        Random rnd = new Random();
        bg.board[xPos][yPos].lock();
        while (!Thread.currentThread().isInterrupted()) {
            boolean moved = false;
            int nextX = 0;
            int nextY = 0;
            while (!moved && !Thread.currentThread().isInterrupted()) {
                do {
                    nextX = xPos + (-1 + rnd.nextInt(3));
                    nextY = yPos + (-1 + rnd.nextInt(3));
                } while (!checkPosition(nextX, nextY));
                try {
                    moved = bg.board[nextX][nextY].tryLock(500, TimeUnit.MILLISECONDS);
                    System.out.println(Thread.currentThread().getName() + " " + nextX + " " + nextY + " " + moved);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    if(moved || Thread.currentThread().isInterrupted()) {
                        bg.board[xPos][yPos].unlock();
                    }
                }
            }

            xPos = nextX;
            yPos = nextY;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private boolean checkPosition(int xPos, int yPos) {
        boolean valid = false;

        if (xPos >= 0 && xPos < bg.board.length && yPos >= 0 && yPos < bg.board.length) {
            valid = true;
        }
        return valid;
    }

    public static void main(String[] args) throws InterruptedException {
        Board bg = new Board(5, 5);
        Thread t1 = new Thread(new Hero(1,1, bg));
        Thread t2 = new Thread(new Hero(2,2, bg));
        Thread t3 = new Thread(new Hero(3,3, bg));
        Thread t4 = new Thread(new Hero(4,4, bg));

        t1.start();
        Thread.sleep(150);
        t2.start();
        Thread.sleep(150);
        t3.start();
        Thread.sleep(150);
        t4.start();

       Thread.sleep(5000);
        t1.interrupt();
        t2.interrupt();
        t3.interrupt();
        t4.interrupt();

    }

}
