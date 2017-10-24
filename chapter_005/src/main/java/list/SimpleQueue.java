package list;

/**
 * 5.3.3. Используя контейнер на базе связанного списка создать контейнер Queue. [#160].
 * Created by Алексей on 23.10.2017.
 */
public class SimpleQueue<T> extends SimpleLinkedList<T> {

    public T poll() {
        if (this.getSize() == 0) {
            return null;
        }
        return super.remove();
    }
    public void offer(T value) {
        // Я так понимаю, что в queue тут должна быть ещё проверка на возможность этого действия.
        super.add(value);
        }
    }

