package testtask2;

import java.util.LinkedList;

/**
 * Дополнительное тестовое задание 1.
 * Напишите программу, которая возвращает минимальное количество прыжков,
 * что необходимо сделать лягушке для того, чтобы добраться из начального сегмента в конечном.
 * Created by Алексей on 18.11.2017.
 */

public class FrogWay {

    /**
     * Хранилище информации о позиции.
     * Храним координаты ячейки в массиве,
     * количество шагов до данной ячеки от стартовой,
     * и предыдущую ячейку массива (с которой было перемещение на текущую).
     */
    private class Position {
        private int x;
        private int y;
        private int movesToPosition;
        private Position previous;

        private Position(int x, int y) {
            this.x = x;
            this.y = y;
            movesToPosition = 0;
        }

        private void setMovesToPosition(int movesToPosition) {
            this.movesToPosition = movesToPosition;
        }

        private void setPrevious(Position previous) {
            this.previous = previous;
        }

        private int getMovesToPosition() {
            return movesToPosition;
        }

        private Position getPrevious() {
            return previous;
        }

        private int getX() {
            return x;
        }

        private int getY() {
            return y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Position that = (Position) o;

            if (x != that.x) return false;
            return y == that.y;
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            return result;
        }

        @Override
        public String toString() {
            return String.format("Шаг %d: x = %d, y = %d.", movesToPosition, x + 1, y + 1);
        }
    }

    /**
     * Инициализируем стартовую ячейку, после чего передаем в move()
     * для рассчета количества шагов до каждой ячейки от стартовой.
     * @param field поле заданных размеров.
     * @param x стартовая позиция по х.
     * @param y стартовая позиция по y.
     */
    private void start(Position[][] field, int x, int y) {
        if (field[x][y] == null) {
            field[x][y] = new Position(x, y);
        }
        move(field, x, y, 3, 0);
    }

    /**
     * Считаем количество шагов до каждой точки поля от ячейки.
     * @param field поле заданных размеров.
     * @param x х ячейки, от которой ведется расчет.
     * @param y у ячейки, от которой ведется расчет.
     * @param changeX изменение x за один шаг.
     * @param changeY изменение y за один шаг.
     */
    private void move(Position[][] field, int x, int y, int changeX, int changeY) {
        int newX = x + changeX;
        int newY = y + changeY;
        if (newX > 15) {
            newX -= 16;
        }
        if (newY < 0 || newY > 9) {
            return;
        }
        if (field[newX][newY] == null) {
            field[newX][newY] = new Position(newX, newY);
            field[newX][newY].setPrevious(field[x][y]);
        } else if (field[newX][newY].getMovesToPosition() < field[x][y].getMovesToPosition() + 1) {
            return;
        }
        field[newX][newY].setPrevious(field[x][y]);
        field[newX][newY].setMovesToPosition(field[x][y].getMovesToPosition() + 1);
        x = newX;
        y = newY;
        move(field, x, y, 3, 0);
        move(field, x, y, 2, 1);
        move(field, x, y, 1, 2);
        move(field, x, y, 1, -2);
        move(field, x, y, 2, -1);
    }

    /**
     * Основной метод.
     * Вызываем необходимые методы для подсчета количества шагов для достижения каждой точки поля из стартовой,
     * после чего выводим необходимое количесвто шагов и путь до точки финиша.
     * @param field поле заданных размеров.
     * @param startX стартовая позиция по x.
     * @param startY стартовая позиция по y.
     * @param finishX финиш x.
     * @param finishY финиш y.
     */
    public void calculate(Position[][] field, int startX, int startY, int finishX, int finishY) {
        startX--;
        startY--;
        finishX--;
        finishY--;
        start(field, startX, startY);
        Position result = field[finishX][finishY];
        System.out.printf("Для достижения точки (%d, %d) из точки (%d, %d) потребуется минимум %d шагов. Путь: %n",
                finishX + 1, finishY + 1, startX + 1, startY + 1, result.getMovesToPosition());
        LinkedList<Position> positions = new LinkedList<>();
        while (result.getPrevious() != null) {
            positions.addFirst(result);
            result = result.getPrevious();
        }
        for (Position p : positions) {
            System.out.println(p);
        }
    }
}
