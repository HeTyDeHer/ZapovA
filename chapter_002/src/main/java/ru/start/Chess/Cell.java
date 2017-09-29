package ru.start.Chess;

/**
 * Клетка.
 * Created by Алексей on 27.09.2017.
 */
public class Cell {
    /** x. */
    private int x;
    /** y. */
    private int y;
    /** конструктор. */
    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }
    /** геттеры. */
    public int getX() {
        return x;
    }
    /** геттеры. */
    public int getY() {
        return y;
    }
    /** сеттер. */
    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Сравнение клеток.
     * @param obj клетка.
     * @return одна и та же или нет.
     */
    @Override
    public boolean equals(Object obj) {
        boolean equals = false;
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        Cell cell = (Cell) obj;
        if (this == obj) {
            equals = true;
        } else if (cell.getX() == this.getX() && cell.getY() == this.getY()){
            equals = true;
        }
        return equals;
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
