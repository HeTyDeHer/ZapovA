package ru.start.chess;

/**
 * Шахматная доска.
 * Created by Алексей on 27.09.2017.
 */
public class Board {
    /** количество фигур на доске. */
    private int count = 0;
    /** массив фигур на доске. */
    private Figure[] figures = new Figure[64];

    /**
     * Добавление фигуры на доску.
     * @param figure фигруа.
     */
    public void add(Figure figure) {
        figures[count++] = figure;
    }

    /**
     * Свободен ли выбранный путь.
     * @param way Массив клеток пути.
     * @return свободен?
     */
    public boolean checkWay(Cell[] way) {
        boolean empty = true;
        for (int i = 0; i < count; i++) {
            for (Cell c : way) {
                if (c.equals(figures[i].getPosition())) {
                    empty = false;
                    break;
                }
            }
        }
        return empty;
    }

    /**
     * Движение фигуры.
     * @param source из клетки.
     * @param dest в клетку.
     * @throws ImpossibleMoveException невозможный ход.
     * @throws OccupiedWayException путь занят.
     * @throws FigureNotFoundException в source нет фигуры.
     */
    public void move(Cell source, Cell dest) throws ImpossibleMoveException, OccupiedWayException, FigureNotFoundException {
        boolean notFound = true;
        int position = 0;
        for (int i = 0; i < count; i++) {
            if (figures[i].getPosition().equals(source)) {
                notFound = false;
                position = i;
            }
        }
        if (notFound) {
            throw new FigureNotFoundException("В исходной клетке нет фигуры.");
        }
        if (!checkWay(figures[position].way(dest))) {
            throw new OccupiedWayException("На траектории другая фигура");
        }
        figures[position].clone(dest);
    }

    /**
     * отображение фигур на доске.
     */
    public void showFigures() {
        for (int i = 0; i < count; i++) {
            System.out.println(figures[i]);
        }
    }
}
