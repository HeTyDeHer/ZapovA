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

    /**
     * Стартуем.
     * @param size размер квадратного поля (size * size).
     * @param monsters количество монстров на поле со старта.
     * @param forbidden запрещенные позиции.
     */
    private void startGame(int size, int monsters, int forbidden) {
        Board board = new Board(size);
        Random rnd = new Random();
        ArrayList<Thread> threads = new ArrayList<>();
        Thread heroThread = new Thread(new Monster(size - 1, size - 1, board, null, new RandomInput()), "Hero");
        threads.add(heroThread);
        for (int i = 1; i <= monsters; i++) {
            Thread monster = new Thread(new Monster(rnd.nextInt(size - 1), rnd.nextInt(size - 1), board, heroThread, new RandomInput()), "Monster " + i);
            monster.setDaemon(true);
            threads.add(monster);
        }
        for (int i = 0; i < forbidden; i++) {
            board.addForbiddenPosition(new Position(rnd.nextInt(size - 1), rnd.nextInt(size - 1)));
        }

        for (Thread t : threads) {
            t.start();
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
