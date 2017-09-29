package ru.start.Chess;

/**
 * Фигуры.
 * Created by Алексей on 27.09.2017.
 */
public abstract class Figure {
    /** Цвет. */
    private boolean white;
    /** Текущее ячейка. */
    final Cell position;
    /** название*/
    String name;
    /** Конструктор. */
    public Figure(Cell position, String name) {
        this.position = position;
        this.name = name;
    }
    /**
     * Получаем массив клеток, которые должна пройти фигура.
     * @param dest - точка назначение.
     * @return массив.
     */
    public abstract Cell[] way(Cell dest) throws ImpossibleMoveException;
    /**
     * Клонируемся.
     * @param to - куда.
     */
    public void clone(Cell to) {
        this.position.setXY(to.getX(), to.getY());
    }

    /**
     * для Board.showFigures().
     * @return Имя фигуры и клетка.
     */
    @Override
    public String toString() {
        return name + " на " + position;
    }
}
