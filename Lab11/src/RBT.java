import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class RBT<T extends Comparable<T>> implements IRB<T> {

    private Node<T> root;
    private final boolean RED = true;
    private final boolean BLACK = false;

    @Override
    public int size() {
        return size(root);
    }

    private int size(Node<T> node) {
        if (node == null) return 0;
        return 1 + size(node.left) + size(node.right);
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public void insert(T element) {
        root = insertRec(root, element);
        root.color = BLACK;
    }

    private Node<T> insertRec(Node<T> node, T element) {
        if (node == null) return new Node<>(element, RED);

        int cmp = element.compareTo(node.element);
        if (cmp < 0) node.left = insertRec(node.left, element);
        else if (cmp > 0) node.right = insertRec(node.right, element);

        // Red-Black Tree properties
        if (isRed(node.right) && !isRed(node.left)) node = leftRotate(node);
        if (isRed(node.left) && isRed(node.left.left)) node = rightRotate(node);
        if (isRed(node.left) && isRed(node.right)) recolor(node);

        return node;
    }

    @Override
    public boolean remove(T element) {
        if (!contains(element)) return false;

        if (!isRed(root.left) && !isRed(root.right)) root.color = RED;
        root = remove(root, element);
        if (!isEmpty()) root.color = BLACK;
        return true;
    }

    private Node<T> remove(Node<T> node, T element) {
        int cmp = element.compareTo(node.element);

        if (cmp < 0) {
            if (!isRed(node.left) && !isRed(node.left.left)) node = moveRedLeft(node);
            node.left = remove(node.left, element);
        } else {
            if (isRed(node.left)) node = rightRotate(node);
            if (cmp == 0 && node.right == null) return null;
            if (!isRed(node.right) && !isRed(node.right.left)) node = moveRedRight(node);
            if (cmp == 0) {
                Node<T> min = min(node.right);
                node.element = min.element;
                node.right = deleteMin(node.right);
            } else {
                node.right = remove(node.right, element);
            }
        }

        return fixUp(node);
    }

    @Override
    public boolean contains(T element) {
        Node<T> current = root;
        while (current != null) {
            int cmp = element.compareTo(current.element);
            if (cmp < 0) current = current.left;
            else if (cmp > 0) current = current.right;
            else return true;
        }
        return false;
    }

    @Override
    public void inorder(List<T> list) {
        inorder(root, list);
    }

    private void inorder(Node<T> node, List<T> list) {
        if (node == null) return;
        inorder(node.left, list);
        list.add(node.element);
        inorder(node.right, list);
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

    @Override
    public Node<T> leftRotate(Node<T> node) {
        Node<T> x = node.right;
        node.right = x.left;
        if (x.left != null) x.left.parent = node;
        x.left = node;
        x.color = node.color;
        node.color = RED;
        return x;
    }

    @Override
    public Node<T> rightRotate(Node<T> node) {
        Node<T> x = node.left;
        node.left = x.right;
        if (x.right != null) x.right.parent = node;
        x.right = node;
        x.color = node.color;
        node.color = RED;
        return x;
    }

    @Override
    public Node<T> reconstruction(Node<T> node) {
        if (isRed(node.right)) node = leftRotate(node);
        if (isRed(node.left) && isRed(node.left.left)) node = rightRotate(node);
        if (isRed(node.left) && isRed(node.right)) recolor(node);
        return node;
    }

    @Override
    public void recolor(Node<T> node) {
        node.color = RED;
        if (node.left != null) node.left.color = BLACK;
        if (node.right != null) node.right.color = BLACK;
    }

    private boolean isRed(Node<T> node) {
        return node != null && node.color == RED;
    }

    private Node<T> moveRedLeft(Node<T> node) {
        recolor(node);
        if (isRed(node.right.left)) {
            node.right = rightRotate(node.right);
            node = leftRotate(node);
            recolor(node);
        }
        return node;
    }

    private Node<T> moveRedRight(Node<T> node) {
        recolor(node);
        if (isRed(node.left.left)) {
            node = rightRotate(node);
            recolor(node);
        }
        return node;
    }

    private Node<T> fixUp(Node<T> node) {
        if (isRed(node.right)) node = leftRotate(node);
        if (isRed(node.left) && isRed(node.left.left)) node = rightRotate(node);
        if (isRed(node.left) && isRed(node.right)) recolor(node);
        return node;
    }

    private Node<T> min(Node<T> node) {
        while (node.left != null) node = node.left;
        return node;
    }

    private Node<T> deleteMin(Node<T> node) {
        if (node.left == null) return null;
        if (!isRed(node.left) && !isRed(node.left.left)) node = moveRedLeft(node);
        node.left = deleteMin(node.left);
        return fixUp(node);
    }
}
