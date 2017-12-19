package testtask;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Тестовое задание. [#1108]. Реализовать игру бомбермен.
 * Управление. Старт, стоп, исходные параметры.
 * Created by Алексей on 13.12.2017.
 */
public class ControlGame {
    /** Все созданные треды. */
    private ArrayList<Thread> threads = new ArrayList<>();

    /**
     * Стартуем.
     * @param size размер квадратного поля (size * size).
     * @param monsters количество монстров на поле со старта.
     * @param forbidden запрещенные позиции.
     */
    private void startGame(int size, int monsters, int forbidden) {
        Board board = new Board(size);
        Random rnd = new Random();
        for (int i = 1; i <= monsters; i++) {
            Thread monster = new Thread(new Monster(rnd.nextInt(size - 1), rnd.nextInt(size - 1), board, this), "Monster " + i);
            threads.add(monster);
        }
        for (int i = 0; i < forbidden; i++) {
            board.addForbiddenPosition(new Position(rnd.nextInt(size - 1), rnd.nextInt(size - 1)));
        }

        Thread hero = new Thread(new Hero(size - 1, size - 1, board, this, new RandomInput()), "Hero");
        threads.add(hero);

        for (Thread t : threads) {
            t.start();
        }

    }

    /**
     * Остановка.
     */
    void stopGame() {
        for (Thread t : threads) {
            t.interrupt();
        }
    }

    public static void main(String[] args) {
        ControlGame cg = new ControlGame();
        Scanner sc = new Scanner(System.in);
        System.out.println("Размеры поля? ");
        int size = Integer.parseInt(sc.nextLine());
        System.out.println("Количество монстров? ");
        int monsters = Integer.parseInt(sc.nextLine());
        System.out.println("Запрещенных позиций? ");
        int forbidden = Integer.parseInt(sc.nextLine());
        cg.startGame(size, monsters, forbidden);
    }
}
