package list;

/**
 * 5.3.4. Задан связанный список. Определить цикличность. [#161].
 * Created by Алексей on 23.10.2017.
 */
class Node<T> {
    T value;
    Node<T> next;

    public Node(T value) {
        this.value = value;
    }

    boolean hasCycle(Node first) {
        boolean result = true;
        Node second = first;
        do {
            first = first.next;
            if (second.next == null || second.next.next == null) {
                result = false;
                break;
            }
            second = second.next.next;

        } while (!first.equals(second));
        return result;
    }
}