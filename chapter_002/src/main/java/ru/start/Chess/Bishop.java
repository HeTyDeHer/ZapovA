package ru.start.Chess;

/**
 * Слон.
 * Created by Алексей on 27.09.2017.
 */
public class Bishop extends Figure {
    /** Конструктор */
    public Bishop(Cell position, String name) {
        super(position, name);
    }

    /**
     * Путь от точки до точки.
     * @param dest - точка назначения.
     * @return  массив всех Cell на пути следования
     * @throws ImpossibleMoveException невозможный ход.
     */
    @Override
    public Cell[] way(Cell dest) throws ImpossibleMoveException {
        int x1 = position.getX();
        int x2 = dest.getX();
        int y1 = position.getY();
        int y2 = dest.getY();
        if (x2 < 0 || x2 > 7 || y2 < 0 || y2 > 7) {
            throw new ImpossibleMoveException("Алло, доска 8х8");
        }
        if (x1 == x2) {
            throw new ImpossibleMoveException("Та же клетка");
        }
        if ((Math.abs(x1 - x2) - Math.abs(y1 - y2)) != 0) {
            throw new ImpossibleMoveException("Слон так не ходит");
        }
        Cell[] way = new Cell[Math.abs(x1 - x2)];
        if ((x2 - x1) < 0 && (y2 - y1) < 0) {
            for (int i = 0; i < way.length; i++){
                way[i] = new Cell(--x1, --y1);
            }
        } else if ((x2 - x1) > 0 && (y2 - y1) < 0) {
            for (int i = 0; i < way.length; i++){
                way[i] = new Cell(++x1, --y1);
            }
        } else if ((x2 - x1) < 0 && (y2 - y1) > 0) {
            for (int i = 0; i < way.length; i++){
                way[i] = new Cell(--x1, ++y1);
            }
        } else if ((x2 - x1) > 0 && (y2 - y1) > 0) {
            for (int i = 0; i < way.length; i++){
                way[i] = new Cell(++x1, ++y1);
            }
        }
        return way;
    }
}
