import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


// test case is unsucsesfull so I will fix it later

public class Lab11 {
    public static void main(String[] args) {
        RBTree<Integer> tree = new RBTree<>();
        tree.insert(20);
        tree.insert(10);
        tree.insert(25);
        tree.insert(15);

        List<Integer> list = new ArrayList<>();
        tree.levelorder(list);
        for (Integer integer : list) {
            System.out.print(integer + " ");
        }
        System.out.println(" ");
        System.out.println(tree.getRoot().color);
        System.out.println(tree.getRoot().left.color);
        System.out.println(tree.getRoot().right.color);
        System.out.println(tree.getRoot().left.right.color);





        }
    }


interface IList<T> {
    int size();
    boolean isEmpty();
}

interface ITree<T> extends IList<T> {
    void insert(T element);
    boolean remove(T element);
    boolean contains(T element);
    void inorder(List<T> list);
    void levelorder(List<T> list);
}

interface IRB<T> extends ITree<T> {
    Node<T> leftRotate(Node<T> node);
    Node<T> rightRotate(Node<T> node);
    Node<T> reconstruction(Node<T> node);
    void recolor(Node<T> node);
}

class Node<T> {
    boolean color; // False: black, True: red
    T element;
    Node<T> parent;
    Node<T> left;
    Node<T> right;

    Node(T element, boolean color) { 
        this.element = element;
        this.color = color;
    }

}

/*
 * Every node is red or black
 * New insertions always red
 * every path from root to leaf has the same # black
 * no path can have two consecutive red
 * every leaf (null) is considered black
 */


class RBTree <T extends Comparable<? super T>> implements ITree<T> {
     Node<T> root;
    private final boolean RED = true;
    private final boolean BLACK = false;
    private int size;

    RBTree() {
        this.root = null;
        this.size = 0;
    }

    public Node<T> getRoot() {
        return root;
    }


    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void insert(T element) {
        Node<T> newNode = new Node<>(element, RED);
        if (root == null) {
            root = newNode;
            root.color = BLACK;
        } else {
            root = insertRec(root, newNode);
        }
        size++;
    }

    private Node<T> insertRec(Node<T> root, Node<T> newNode) {
        if (root == null) {
            return newNode;
        }
        if (newNode.element.compareTo(root.element) < 0) {
            root.left = insertRec(root.left, newNode);
            root.left.parent = root;
        } else if (newNode.element.compareTo(root.element) > 0) {
            root.right = insertRec(root.right, newNode);
            root.right.parent = root;
        }
        return fixViolations(root);
    }

    private Node<T> fixViolations(Node<T> node) {
        if (node == null) return null;

        if (node.left != null && node.left.color == RED && node.left.left != null && node.left.left.color == RED) {
            node = rightRotate(node);
            recolor(node);
        }

        if (node.right != null && node.right.color == RED && node.right.right != null && node.right.right.color == RED) {
            node = leftRotate(node);
            recolor(node);
        }

        if (node.left != null && node.left.color == RED && node.right != null && node.right.color == RED) {
            recolor(node);
        }

        return node;
    }

    private Node<T> getUncle(Node<T> node) {
        if (node.parent == null || node.parent.parent == null) {
            return null; // No uncle exists
        }
        if (node.parent == node.parent.parent.left) {
            return node.parent.parent.right;
        } else {
            return node.parent.parent.left;
        }
    }


    public Node<T> leftRotate(Node<T> node) {
        Node<T> newRoot = node.right;
        node.right = newRoot.left;
        if (newRoot.left != null) {
            newRoot.left.parent = node;
        }
        newRoot.parent = node.parent;
        if (node.parent == null) {
            root = newRoot;
        } else if (node == node.parent.left) {
            node.parent.left = newRoot;
        } else {
            node.parent.right = newRoot;
        }
        newRoot.left = node;
        node.parent = newRoot;
        return newRoot;
    }


    public Node<T> rightRotate(Node<T> node) {
        Node<T> newRoot = node.left;
        node.left = newRoot.right;
        if (newRoot.right != null) {
            newRoot.right.parent = node;
        }
        newRoot.parent = node.parent;
        if (node.parent == null) {
            root = newRoot;
        } else if (node == node.parent.right) {
            node.parent.right = newRoot;
        } else {
            node.parent.left = newRoot;
        }
        newRoot.right = node;
        node.parent = newRoot;
        return newRoot;
    }


    public void recolor(Node<T> node) {
        Node<T> uncle = getUncle(node);
        node.color = !node.color;
        if (uncle != null) {
            uncle.color = !uncle.color;
        }
        if (node.parent != null) {
            node.parent.color = !node.parent.color;
        }
    }


    public boolean remove(T element) {
        throw new UnsupportedOperationException("Remove not implemented yet.");
    }

    @Override
    public boolean contains(T element) {
        Node<T> current = root;
        while (current != null) {
            int cmp = element.compareTo(current.element);
            if (cmp == 0) return true;
            current = cmp < 0 ? current.left : current.right;
        }
        return false;
    }

    @Override
    public void inorder(List<T> list) {
        inorderRec(root, list);
    }

    private void inorderRec(Node<T> node, List<T> list) {
        if (node == null) return;
        inorderRec(node.left, list);
        list.add(node.element);
        inorderRec(node.right, list);
    }

    @Override
    public void levelorder(List<T> list) {
        if (root == null) return;
        Queue<Node<T>> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node<T> current = queue.poll();
            list.add(current.element);
            if (current.left != null) queue.add(current.left);
            if (current.right != null) queue.add(current.right);
        }
    }





}