package testtask;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Тестовое задание. [#1108]. Реализовать игру бомбермен.
 * Класс Board - игровое поле.
 * Created by Алексей on 12.12.2017.
 */
public class Board {
    /** Само поле. */
    private final MyLock[][] board;
    /** Массив запрещенных позиций. */
    private final ArrayList<Position> forbiddenPositions = new ArrayList<>();

    /**
     * Конструктор.
     * После создания поля заполняем локами.
     * @param size сторона квадртаного поля.
     */
    public Board(int size) {
        this.board = new MyLock[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = new MyLock();
            }
        }
    }

    /**
     * Добавление запрещенных позиций.
     * @param p запрещенная позиция.
     */
    public void addForbiddenPosition(Position p) {
        forbiddenPositions.add(p);
    }

    /**
     * Проверка возможности хода на выбранную позицию.
     * Проверка выполняется только на соответсвие границам поля и на запрещенные позиции,
     * без проверки занятости ячейки.
     * @param xPos x позиции.
     * @param yPos y позиции.
     * @return возможен ход/нет.
     */
    public boolean checkPosition(int xPos, int yPos) {
        boolean valid = false;
        if (xPos >= 0 && xPos < board.length && yPos >= 0 && yPos < board.length) {
            valid = true;
        }
        Position p = new Position(xPos, yPos);
        for (Position forbidPos : forbiddenPositions) {
            if (p.equals(forbidPos)) {
                valid = false;
                break;
            }
        }
        return valid;
    }
    /** Расширяем ReentrantLock для возможности получения имени владельца замка. */
    class MyLock extends ReentrantLock {
        public String owner() {
            Thread t =  this.getOwner();
            if (t != null) {
                return t.getName();
            } else {
                return "None";
            }
        }
    }

    public MyLock[][] getBoard() {
        return board;
    }
}

/**
 * Обертка для позиции, храним x и y.
 */
class Position {
    private int x;
    private int y;

    Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (x != position.x) return false;
        return y == position.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}
