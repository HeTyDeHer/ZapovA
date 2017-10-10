package ru.start.chess;

/**
 * Фигуры.
 * Created by Алексей on 27.09.2017.
 */
public abstract class Figure {
    /** Цвет. */
    private boolean white;
    /** Текущее ячейка. */
    private final Cell position;
    /** название.*/
    private String name;
    /** Конструктор. */
    private int key;

    /**
     * Конструктор.
     * @param position позиция.
     * @param name Имя.
     */
    public Figure(Cell position, String name) {
        this.position = position;
        this.name = name;
    }

    /**
     * Геттер позиция.
     * @return Cell позиция.
     */
    public Cell getPosition() {
        return position;
    }

    /** Инфо о фигуре.
     * @return String ключ, имя. */
    public String info() {
        return String.format("%s. %s.", this.key, this.name);
    }
    /**
     * Получаем массив клеток, которые должна пройти фигура.
     * @param dest - точка назначение.
     * @return массив.
     * @throws ImpossibleMoveException невозможный ход.
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
