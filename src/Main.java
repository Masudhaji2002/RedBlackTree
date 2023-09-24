import java.util.Date;
import java.util.Iterator;
class RedBlackTree {
    Node root;

    enum Color {
        RED,
        BLACK
    }

    class Node {
        int value;
        Node left;
        Node right;
        Node parent;
        Color color;

        public Node(int value, Color color) {
            this.value = value;
            this.color = color;
        }
    }

    public boolean find(int value) {
        return find(root, value);
    }

    private boolean find(Node node, int value) {
        if (node == null) {
            return false;
        }
        if (node.value == value) {
            return true;
        }
        if (node.value < value) {
            return find(node.right, value);
        } else {
            return find(node.left, value);
        }
    }

    public void insert(int value) {
        root = insert(root, null, value);
        root.color = Color.BLACK; // Убедимся, что корень всегда черный
    }

    private Node insert(Node node, Node parent, int value) {
        if (node == null) {
            return new Node(value, Color.RED);
        }

        if (value < node.value) {
            node.left = insert(node.left, node, value);
        } else if (value > node.value) {
            node.right = insert(node.right, node, value);
        } else {
            // Если значение уже существует, не добавляем его ещё раз
            return node;
        }

        // Проверяем и балансируем дерево после вставки
        if (isRed(node.right) && !isRed(node.left)) {
            node = rotateLeft(node);
        }
        if (isRed(node.left) && isRed(node.left.left)) {
            node = rotateRight(node);
        }
        if (isRed(node.left) && isRed(node.right)) {
            flipColors(node);
        }

        return node;
    }

    private boolean isRed(Node node) {
        if (node == null) {
            return false;
        }
        return node.color == Color.RED;
    }

    private Node rotateLeft(Node node) {
        Node newRoot = node.right;
        node.right = newRoot.left;
        newRoot.left = node;
        newRoot.color = node.color;
        node.color = Color.RED;
        return newRoot;
    }

    private Node rotateRight(Node node) {
        Node newRoot = node.left;
        node.left = newRoot.right;
        newRoot.right = node;
        newRoot.color = node.color;
        node.color = Color.RED;
        return newRoot;
    }

    private void flipColors(Node node) {
        node.color = Color.RED;
        node.left.color = Color.BLACK;
        node.right.color = Color.BLACK;
    }
}
public class Main {
    public static void main(String[] args) {
        RedBlackTree tree = new RedBlackTree();

        tree.insert(5);
        tree.insert(3);
        tree.insert(1);
        tree.insert(2);
        tree.insert(4);
        tree.insert(7);
        tree.insert(6);
        tree.insert(8);

        // Проверка наличия элементов в дереве
        System.out.println("Поиск 4: " + tree.find(4)); // Вывод: true
        System.out.println("Поиск 9: " + tree.find(9)); // Вывод: false
    }
}