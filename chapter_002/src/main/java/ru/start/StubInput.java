package ru.start;

/**
 * Имитация ввода с консоли.
 * Created by Алексей on 25.09.2017.
 */
public class StubInput implements Input {
    /** Массив ответов. */
    private String[] answers;
    /** Счетчик выдачи. */
    private int position = 0;
    /** Конструктор.
     * @param answers массив ответов.
     */
    public StubInput(String[] answers) {
        this.answers = answers;
    }

    @Override
    public String ask(String question) {
        return answers[position++];

    }
}
