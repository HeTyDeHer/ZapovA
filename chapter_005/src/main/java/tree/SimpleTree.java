package tree;


import java.util.*;

/**
 * 1. Создать элементарную структуру дерева [#1711].
 * Created by Алексей on 03.11.2017.
 */
public class SimpleTree<T extends Comparable<T>> implements Iterable<T> {
    /** Стартовый элемент. Общий корень. */
    private Node<T> startNode;
    /** Количество хранимых элементов. */
    private int size = 0;

    /**
     * Геттер количесвто хранимых элементов.
     * @return Количество хранимых элементов.
     */
    public int getSize() {
        return size;
    }

    private class Node<T> {
        /** Левый лист. Значения <=. */
        Node<T> leftChild;
        /** Правый лист. Значения > . */
        Node<T> rightChild;
        /** Корень. */
        Node<T> parent;
        /** Хранимое значение. */
        T key;

        /**
         * Конструктор.
         * @param parent Корень.
         * @param key Хранимое значение.
         */
        public Node(Node<T> parent, T key) {
            this.parent = parent;
            this.key = key;
            leftChild = null;
            rightChild = null;
        }

        /**
         * Геттер левый лист.
         * @return левый лист.
         */
        public Node<T> getLeftChild() {
            return leftChild;
        }
        /**
         * Cеттер левый лист.
         * @param leftChild левый лист.
         */
        public void setLeftChild(Node<T> leftChild) {
            this.leftChild = leftChild;
        }
        /**
         * Геттер правый лист.
         * @return правый лист.
         */
        public Node<T> getRightChild() {
            return rightChild;
        }
        /**
         * Сеттер правый лист.
         * @param rightChild правый лист.
         */
        public void setRightChild(Node<T> rightChild) {
            this.rightChild = rightChild;
        }

        /**
         * Геттер корень.
         * @return корень.
         */
        public Node<T> getParent() {
            return parent;
        }

        /**
         * Сеттер корен.
         * @param parent корень.
         */
        public void setParent(Node<T> parent) {
            this.parent = parent;
        }

        /**
         * Геттер значение.
         * @return значение.
         */
        public T getKey() {
            return key;
        }

        /**
         * Сеттер значение.
         * @param key значение.
         */
        public void setKey(T key) {
            this.key = key;
        }
    }

    /**
     * Добавление значения.
     * @param key значение.
     */
    public void add(T key) {
        Node<T> node = new Node<>(null, key);
        if (startNode == null) {
            startNode = node;
        } else {
            findPositionAndAdd(startNode, key);
        }
        size++;
    }

    /**
     * Поиск позиции для добавления элемента и его добавление.
     * @param node начальный элемент, от которого начинается поиск.
     * @param key хранимое значение.
     */
    private void findPositionAndAdd(Node<T> node, T key) {
        if (node.getKey().compareTo(key) >= 0) {
            if (node.getLeftChild() == null) {
                node.setLeftChild(new Node<>(node, key));
            } else {
                findPositionAndAdd(node.getLeftChild(), key);
            }
        } else {
            if (node.getRightChild() == null) {
                node.setRightChild(new Node<>(node, key));
            } else {
                findPositionAndAdd(node.getRightChild(), key);
            }
        }
    }

/*   public void add(T key) {
        Node<T> node = new Node<T> (null, key);
        if (startNode == null) {
            startNode = node;
            size++;
            return;
        }
        Node<T> currentNode = startNode;
        Node<T> parent = null;
        boolean right = false;
        while (currentNode != null) {
            if (currentNode.getKey().compareTo(node.getKey()) >= 0) {
                parent = currentNode;
                currentNode = currentNode.getLeftChild();
                right = false;
            } else {
                parent = currentNode;
                currentNode = currentNode.getRightChild();
                right = true;
            }
        }
        node.setParent(parent);
        if(right) {
            parent.setRightChild(node);
        } else {
            parent.setLeftChild(node);
        }
        size++;
    }*/

    /**
     * Содержится ли данное значение в дереве?
     * @param value значение для поиска.
     * @return да/нет.
     */
    public boolean contains(T value) {
        Node<T> currentNode = startNode;
        while (currentNode != null) {
            if (value.equals(currentNode.getKey())) {
                return true;
            } else if (currentNode.getKey().compareTo(value) > 0) {
                currentNode = currentNode.getLeftChild();
            } else {
                currentNode = currentNode.getRightChild();
            }
        }
        return false;
    }

    /**
     * Удаление значения из дерева.
     * @param value значение.
     * @return удалось/нет. Нет  - в случае, если элемент не содержится в дереве.
     */
    public boolean delete(T value) {
        Node<T> currentNode = startNode;
        boolean isRight = false;
        while (!value.equals(currentNode.getKey())) {
            if (currentNode.getKey().compareTo(value) > 0) {
                currentNode = currentNode.getLeftChild();
                isRight = false;
            } else {
                currentNode = currentNode.getRightChild();
                isRight = true;
            }
            if (currentNode == null) {
                return false;
            }
        }
        if (currentNode.getRightChild() == null && currentNode.getLeftChild() == null) {
            if (isRight) {
                currentNode.getParent().setRightChild(null);
            } else {
                currentNode.getParent().setLeftChild(null);
            }
        } else if (currentNode.getRightChild() == null) {
            if (isRight) {
                currentNode.getParent().setRightChild(currentNode.getLeftChild());
            } else {
                currentNode.getParent().setLeftChild(currentNode.getLeftChild());
            }
        } else if (currentNode.getLeftChild() == null) {
            if (isRight) {
                currentNode.getParent().setRightChild(currentNode.getRightChild());
            } else {
                currentNode.getParent().setLeftChild(currentNode.getRightChild());
            }
        } else {
            Node<T> replacement = findMinimumInBranch(currentNode.getRightChild());
            if (replacement.getKey().compareTo(currentNode.getRightChild().getKey()) != 0) {
                replacement.getParent().setLeftChild(null);
                replacement.setRightChild(currentNode.getRightChild());
            }
            replacement.setLeftChild(currentNode.getLeftChild());
            replacement.setParent(currentNode.getParent());
            currentNode.getRightChild().setParent(replacement);
            currentNode.getLeftChild().setParent(replacement);
            if (currentNode.getParent() == null) {
                startNode = replacement;
            } else {
                if (isRight) {
                    currentNode.getParent().setRightChild(replacement);
                } else {
                    currentNode.getParent().setLeftChild(replacement);
                }
            }
        }
        size--;
        return true;
    }

    /**
     * Минимальный элемент в ветви.
     * @param node корень ветви.
     * @return Node с минимальным значением.
     */
    private Node<T> findMinimumInBranch(Node<T> node) {
        while (node.getLeftChild() != null) {
            node = node.getLeftChild();
        }
        return node;
    }


    @Override
    public Iterator<T> iterator() {

        return new Iterator<T>() {
            private List<T> list = new ArrayList<>();
            private int index = 0;
            private boolean init = false;

            private void treeToArray(Node<T> node) {
                if (node != null) {
                    treeToArray(node.getLeftChild());
                    list.add(node.getKey());
                    treeToArray(node.getRightChild());
                }
            }

            private void check() {
                if (!init) {
                    list.clear();
                    treeToArray(startNode);
                    init = true;
                }
                if (list.size() != size) {
                    throw new ConcurrentModificationException();
                }
            }

            @Override
            public boolean hasNext() {
                check();
                return index < list.size();
            }

            @Override
            public T next() {
                check();
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return list.get(index++);
            }

            @Override
            public void remove() {
                delete(list.get(--index));
                init = false;
            }
        };
    }


}
