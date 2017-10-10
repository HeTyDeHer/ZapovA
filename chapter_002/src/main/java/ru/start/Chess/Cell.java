package ru.start.chess;

/**
 * Клетка.
 * Created by Алексей on 27.09.2017.
 */
public class Cell {
    /** x. */
    private int x;
    /** y. */
    private int y;

    /**
     * Конструктор.
     * @param x х.
     * @param y у.
     */
    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }
    /**
     *  геттеры.
     * @return x.
     */
    public int getX() {
        return x;
    }
    /**
     *  геттеры.
     * @return y.
     */
    public int getY() {
        return y;
    }

    /**
     * Сеттер.
     * @param x x.
     * @param y y.
     */
    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Cell cell = (Cell) o;

        if (getX() != cell.getX()) {
            return false;
        }
        return getY() == cell.getY();
    }

    @Override
    public int hashCode() {
        int result = getX();
        result = 31 * result + getY();
        return result;
    }

    /**
     * для Board.showFigures().
     * @return х,у.
     */
    @Override
    public String toString() {
        return x + ", " + y;
    }
}
