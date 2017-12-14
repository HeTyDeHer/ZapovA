package testtask;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Тестовое задание. [#1108]. Реализовать игру бомбермен.
 * Тред главного героя.
 * Created by Алексей on 13.12.2017.
 */
class Hero extends Monster {

    private final Input input;

    Hero(int xPos, int yPos, Board board, ControlGame control, Input input) {
        super(xPos, yPos, board, control);
        this.input = input;
    }

    @Override

    public void run() {
        Random rnd = new Random();
        board.board[xPos][yPos].lock();
        while (!Thread.currentThread().isInterrupted()) {
            boolean moved = false;
            int nextX = 0;
            int nextY = 0;
            while (!moved && !Thread.currentThread().isInterrupted()) {
                do {
                    Position newPosition = move();
                    nextX = newPosition.getX();
                    nextY = newPosition.getY();
                } while (!board.checkPosition(nextX, nextY));
                try {
                    moved = board.board[nextX][nextY].tryLock(TRY_LOCK_TIME, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    if (moved || Thread.currentThread().isInterrupted()) {
                        board.board[xPos][yPos].unlock();
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
            case 1: return new Position(this.xPos - 1, this.yPos - 1);
            case 2: return new Position(this.xPos, this.yPos - 1);
            case 3: return new Position(this.xPos + 1, this.yPos - 1);
            case 4: return new Position(this.xPos - 1, this.yPos);
            case 6: return new Position(this.xPos + 1, this.yPos);
            case 7: return new Position(this.xPos - 1, this.yPos + 1);
            case 8: return new Position(this.xPos, this.yPos + 1);
            case 9: return new Position(this.xPos + 1, this.yPos + 1);
        }
        return new Position(this.xPos, this.yPos);
    }
}
