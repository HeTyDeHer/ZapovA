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
    }
}

class Hero implements Runnable {
    private int xPos;
    private int yPos;
    private Board bg;
    private Random rnd = new Random();

    public Hero(int xPos, int yPos, Board bg) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.bg = bg;
        bg.board[xPos][yPos] = new ReentrantLock();
    }

    @Override
    public void run() {
        bg.board[xPos][yPos].lock();
        while (!Thread.interrupted()) {
            boolean moved = false;
            int nextX = 0;
            int nextY = 0;
            while (!moved) {
                nextX = next(xPos);
                nextY = next(yPos);
                if (bg.board[nextX][nextY] == null) {
                    bg.board[nextX][nextY] = new ReentrantLock();
                    moved = bg.board[nextX][nextY].tryLock();
//                   System.out.println(Thread.currentThread().getName() + " Новый замок " + " [ " + nextX + ", " + nextY + " ]" + moved);
                } else {
                    try {
                        moved = bg.board[nextX][nextY].tryLock(500, TimeUnit.MILLISECONDS);
//                        if(!moved) {
//                            System.out.println(Thread.currentThread().getName() + " Старый замок " + " [ " + nextX + ", " + nextY + " ]" + moved);
//                        }
//                        System.out.println(Thread.currentThread().getName() + " Старый замок " + " [ " + nextX + ", " + nextY + " ]" + moved);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
            bg.board[xPos][yPos].unlock();
            xPos = nextX;
            yPos = nextY;
            System.out.println(Thread.currentThread().getName() + " [ " + xPos + ", " + yPos + " ]");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private int next(int current) {
        int next;
        do {
            next = current + (-1 + rnd.nextInt(3));
        } while (next < 0 || next > (bg.board.length - 1));

        return next;
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
