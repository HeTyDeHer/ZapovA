package testtask;

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
    /** Тред героя. */
    private final Thread mainThread;
    /** Ввод. */
    private final Input input;

    private final static int TRY_LOCK_TIME = 500;
    private final static int WAITING_BETWEEN_MOVES_TIME = 1000;

    public Monster(int xPos, int yPos, Board board, Thread mainThread, Input input) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.board = board;
        this.mainThread = mainThread;
        this.input = input;
    }

    @Override
    public void run() {
        boolean hero = mainThread == null;
        board.getBoard()[xPos][yPos].lock();                          // блокируем стартовую позицию.
        while (!Thread.currentThread().isInterrupted()) {
            boolean moved = false;
            int nextX = 0;
            int nextY = 0;
            while (!moved && !Thread.currentThread().isInterrupted()) {
                do {
                    Position newPosition = move();
                    nextX = newPosition.getX();
                    nextY = newPosition.getY();
                } while (!this.board.checkPosition(nextX, nextY));       //проверяем, есть ли возможность такого перемещения с т.з. доски.
                try {
                    if (!hero && board.getBoard()[nextX][nextY].owner().equals("Hero")) {          // если в клетке, на которую мы собираемся переместиться, стоит герой, то конец игры.
                        System.out.printf("%s перешел на [ %d, %d ] %n ", Thread.currentThread().getName(), nextX, nextY);
                        System.out.printf("Game Over [ %d, %d ]%n", nextX, nextY);
                        mainThread.interrupt();
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

    /**
     * Выбираем позицию для перемещения на основе вввода.
     * Движения выбраны под Numpad.
     * @return позиция, на которую перемещаемся.
     */
    private Position move() {
        switch (this.input.nextMove()) {
            case 1: return new Position(this.getxPos() - 1, this.getyPos() - 1);
            case 2: return new Position(this.getxPos(), this.getyPos() - 1);
            case 3: return new Position(this.getxPos() + 1, this.getyPos() - 1);
            case 4: return new Position(this.getxPos() - 1, this.getyPos());
            case 6: return new Position(this.getxPos() + 1, this.getyPos());
            case 7: return new Position(this.getxPos() - 1, this.getyPos() + 1);
            case 8: return new Position(this.getxPos(), this.getyPos() + 1);
            case 9: return new Position(this.getxPos() + 1, this.getyPos() + 1);
        }
        return new Position(this.getxPos(), this.getyPos());
    }

    public int getyPos() {

        return yPos;
    }

    public int getxPos() {

        return xPos;
    }

}


