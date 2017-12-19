package testtask;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Тестовое задание. [#1108]. Реализовать игру бомбермен.
 * Тред монстра.
 * Created by Алексей on 13.12.2017.
 */
class Monster implements Runnable {
    /** Текущая позиция по Х. */
    private int xPos;
    /** Текущая позиция по Y. */
    private int yPos;
    /** Игровое поле. */
    private final Board board;
    /** Контроль. Используется для остановки игры в случае поражения. */
    private final ControlGame control;

    private final static int TRY_LOCK_TIME = 500;
    private final static int WAITING_BETWEEN_MOVES_TIME = 1000;

    public Monster(int xPos, int yPos, Board board, ControlGame control) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.board = board;
        this.control = control;
    }

    @Override
    public void run() {
        Random rnd = new Random();
        board.getBoard()[xPos][yPos].lock();                     // блокируем стартовую позицию.
        while (!Thread.currentThread().isInterrupted()) {
            boolean moved = false;
            int nextX = 0;
            int nextY = 0;
            while (!moved && !Thread.currentThread().isInterrupted()) {
                do {
                    nextX = xPos + (-1 + rnd.nextInt(3));    //выбираем позицию для перемещения на +-1 единицу от текущей.
                    nextY = yPos + (-1 + rnd.nextInt(3));
                } while (!board.checkPosition(nextX, nextY));       //проверяем, есть ли возможность такого перемещения с т.з. доски.
                try {
                    if (board.getBoard()[nextX][nextY].owner().equals("Hero")) {          // если в клетке, на которую мы собираемся переместиться, стоит герой, то конец игры.
                        System.out.printf("%s перешел на [ %d, %d ] %n ", Thread.currentThread().getName(), nextX, nextY);
                        System.out.printf("Game Over [ %d, %d ]", nextX, nextY);
                        control.stopGame();
                        return;
                    }
                    moved = board.getBoard()[nextX][nextY].tryLock(TRY_LOCK_TIME, TimeUnit.MILLISECONDS);  // проверяем, есть ли возможность перемещения на выбранную позицию с т.з. её занятости.
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    if (moved || Thread.currentThread().isInterrupted()) {  //если переместились или закончили игру, освобождаем занимаемое поле.
                        board.getBoard()[xPos][yPos].unlock();
                    }
                }
            }
            System.out.printf("%s перешел на [ %d, %d ] %n ", Thread.currentThread().getName(), nextX, nextY);
            xPos = nextX;
            yPos = nextY;
            try {
                Thread.sleep(WAITING_BETWEEN_MOVES_TIME);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static int getWaitingBetweenMovesTime() {
        return WAITING_BETWEEN_MOVES_TIME;
    }

    public static int getTryLockTime() {

        return TRY_LOCK_TIME;
    }

    public Board getBoard() {

        return board;
    }

    public int getyPos() {

        return yPos;
    }

    public int getxPos() {

        return xPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public void setxPos(int xPos) {

        this.xPos = xPos;
    }


}


