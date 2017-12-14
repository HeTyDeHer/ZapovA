package testtask;

import java.util.Random;

/**
 * Тестовое задание. [#1108]. Реализовать игру бомбермен.
 * Ввод.
 * Created by Алексей on 13.12.2017.
 */
public interface Input {
    int nextMove();
}

class RandomInput implements Input {

    public int nextMove() {
        Random rnd = new Random();
        return (1 + rnd.nextInt(9));
    }
}
