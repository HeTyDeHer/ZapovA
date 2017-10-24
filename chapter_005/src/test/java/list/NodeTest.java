package list;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * 5.3.4. Задан связанный список. Определить цикличность. [#161].
 * Created by Алексей on 24.10.2017.
 */
public class NodeTest {
    @Test
    public void hasCycle() {
        Node<Integer> node0 = new Node<>(1);
        Node<Integer> node1 = new Node<>(1);
        Node<Integer> node2 = new Node<>(1);
        Node<Integer> node3 = new Node<>(1);
        Node<Integer> node4 = new Node<>(1);
        node0.next = node1;
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        assertThat(node0.hasCycle(node0), is(false));
        node4.next = node0;
        assertThat(node0.hasCycle(node0), is(true));
        node4.next = node3;
        assertThat(node0.hasCycle(node0), is(true));
        node4.next = node4;
        assertThat(node0.hasCycle(node0), is(true));
    }

}