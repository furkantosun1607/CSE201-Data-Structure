import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Lab10 {
    public static void main(String[] args) {
        AVL<Integer> avlTree = new AVL<>();

        System.out.println("AVL Ağacına Elemanlar Ekleniyor...");
        avlTree.insert(10);
        avlTree.insert(20);
        avlTree.insert(30);
        avlTree.insert(40);
        avlTree.insert(50);
        avlTree.insert(25);

        // Ağacı yazdır
        System.out.println("\nAVL Ağacındaki Elemanlar (levelorder Traversal):");

        List<Integer> inOrderTraversal = new LinkedList<>();
        avlTree.levelorder(inOrderTraversal);
        for (Integer i : inOrderTraversal) {
            System.out.println(i + " ");
        }


        // Ağacın root'unu yazdır
      //  System.out.println("\nAğacın Root Değeri: " + avlTree.getRoot().element);

        // Silme işlemi
        System.out.println("\nAVL Ağacından Eleman Siliniyor (30):");
        avlTree.delete(30);

        System.out.println("AVL Ağacındaki Elemanlar (In-Order Traversal):");


        // Eleman kontrolü (contains)
        System.out.println("\nAVL Ağacında 20 var mı? " + avlTree.contains(20));
        System.out.println("AVL Ağacında 30 var mı? " + avlTree.contains(30));

        // Ağacı boşaltma
        System.out.println("\nAVL Ağacını Boşaltma:");


        System.out.println("AVL Ağacı Boş mu? " + avlTree.isEmpty());
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

 interface IAVL<T> extends ITree<T> {
    Node<T> rebalance(Node<T> node);
    Node<T> leftRotate(Node<T> node);
    Node<T> rightRotate(Node<T> node);
    Node<T> doubleRotateLR(Node<T> node);
    Node<T> doubleRotateRL(Node<T> node);
 }

 class Node <T> {
    int height;
    T element;
    Node<T> parent;
    Node<T> left;
    Node<T> right;

    Node(T element) { this.element = element; }
 }

 class BST <T extends Comparable<? super T>> implements ITree<T> {
    private Node<T> root;
    private int size;

    public Node<T> getRoot() {
        return root;
    }

    public BST() {}

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
        root = insertRec(root, element);
    }

    protected Node<T> insertRec(Node<T> node, T element) {
        if (node == null) {
            size++;
            return new Node<>(element);
        }

        if (element.compareTo(node.element) < 0) {
            node.left = insertRec(node.left, element);
            node.left.parent = node;
        }

        if (element.compareTo(node.element) > 0) {
            node.right = insertRec(node.right, element);
            node.right.parent = node;
        }

        return node;
    }

    @Override
    public boolean remove(T element) {
        if (contains(element)) {
            root = removeRec(root, element);
            size--;
            return true;
        }
        return false;
    }


    protected Node<T> removeRec(Node<T> node, T element) {
        if (node == null)
            return null;
        if (element.compareTo(node.element) < 0)
            node.left = removeRec(node.left, element);
        else if (element.compareTo(node.element) > 0)
            node.right = removeRec(node.right, element);
        else {
            if (node.left == null)
                return node.right;
            if (node.right == null)
                return node.left;
            node.element = findMin(node.right);
            node.right = removeRec(node.right, node.element);
        }
        return node;
    }

    private T findMin(Node<T> node) {
        Node<T> current = node;
        while (current.left != null)
            current = current.left;
        return current.element;
    }

    private T findMax(Node<T> node) {
        Node<T> current = node;
        while(current.right != null)
            current = current.right;
        return current.element;
    }

    @Override
    public boolean contains(T element) {
        return containsRec(root, element);
    }

    private boolean containsRec(Node<T> node, T element) {
        if (node == null)
            return false;
        if (element.compareTo(node.element) == 0)
            return true;
        return element.compareTo(node.element) < 0
                ? containsRec(node.left, element)
                : containsRec(node.right, element);
    }

    @Override
    public void inorder(List<T> list) {
        inorderRec(root, list);
    }

    private void inorderRec(Node<T> node, List<T> list) {
        if (node == null)
            return;

        inorderRec(node.left, list);
        list.add(node.element);
        inorderRec(node.right, list);
    }

    @Override
    public void levelorder(List<T> list) {
        if (root == null) {
            return;
        }

        Queue<Node<T>> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            Node<T> current = queue.poll();
            list.add(current.element);
            if (current.left != null) {
                queue.add(current.left);
            }
            if (current.right != null) {
                queue.add(current.right);
            }
        }
    }
 }

class AVL<T extends Comparable<? super T>> extends BST<T> implements IAVL<T> {
    Node<T> root;

    int height(Node<T> node) {
        return node == null ? 0 : node.height;
    }

    // Utility function to get the balance factor of a node
    int getBalance(Node<T> node) {
        return node == null ? 0 : height(node.left) - height(node.right);
    }

    private void updateHeight(Node<T> node) {
        if (node != null) {
            node.height = 1 + Math.max(height(node.left), height(node.right));
        }
    }

    @Override
    public Node<T> rebalance(Node<T> node) {
        if (node == null) return node;

        updateHeight(node);
        int balance = getBalance(node);

        // Left-heavy case
        if (balance > 1 && getBalance(node.left) >= 0) {
            return rightRotate(node);
        }
        // Left-Right-heavy case
        if (balance > 1 && getBalance(node.left) < 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        // Right-heavy case
        if (balance < -1 && getBalance(node.right) <= 0) {
            return leftRotate(node);
        }
        // Right-Left-heavy case
        if (balance < -1 && getBalance(node.right) > 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    @Override
//    public Node<T> leftRotate(Node<T> node) {
//        if (node == null || node.right == null) return node;
//
//        Node<T> newRoot = node.right;
//        node.right = newRoot.left;
//        newRoot.left = node;
//
//        updateHeight(node);
//        updateHeight(newRoot);
//
//        return newRoot;
//    }
//
//    @Override
//    public Node<T> rightRotate(Node<T> node) {
//        if (node == null || node.left == null) return node;
//
//        Node<T> newRoot = node.left;
//        node.left = newRoot.right;
//        newRoot.right = node;
//
//        updateHeight(node);
//        updateHeight(newRoot);
//
//        return newRoot;
//    }

    public Node<T> rightRotate(Node<T> y) {
        Node<T> x = y.left;
        Node<T> T2 = x.right;

        // Perform rotation
        x.right = y;
        y.left = T2;

        // Update heights
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        // Return new root
        return x;
    }

    // Left rotate subtree rooted with x
    public Node<T> leftRotate(Node<T> x) {
        Node<T> y = x.right;
        Node<T> T2 = y.left;

        // Perform rotation
        y.left = x;
        x.right = T2;

        // Update heights
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        // Return new root
        return y;
    }

    @Override
    public Node<T> doubleRotateLR(Node<T> node) {
        if (node == null || node.left == null) return node;

        node.left = leftRotate(node.left);
        return rightRotate(node);
    }

    @Override
    public Node<T> doubleRotateRL(Node<T> node) {
        if (node == null || node.right == null) return node;

        node.right = rightRotate(node.right);
        return leftRotate(node);
    }

//    public void insert(T element) {
//        root = insertRec(root, element);
//    }
//
//    protected Node<T> insertRec(Node<T> node, T element) {
//        if (node == null) {
//            return new Node<>(element);
//        }
//
//        if (element.compareTo(node.element) < 0) {
//            node.left = insertRec(node.left, element);
//        } else if (element.compareTo(node.element) > 0) {
//            node.right = insertRec(node.right, element);
//        } else {
//            // Duplicate keys are not allowed in AVL tree
//            return node;
//        }
//
//        return rebalance(node);
//    }


    public Node<T> insert(Node<T> node, T key) {
        // Perform the normal BST insertion
        if (node == null) {
            return new Node(key);
        }

        if (key.compareTo(node.element) < 0) {
            node.left = insert(node.left, key);
        } else if (key.compareTo(node.element) > 0) {
            node.right = insert(node.right, key);
        } else {
            // Duplicate keys are not allowed in BST
            return node;
        }


        // Update height of this ancestor node
        node.height = Math.max(height(node.left), height(node.right)) + 1;

        // Get the balance factor
        int balance = getBalance(node);

        // If the node becomes unbalanced, then there are 4 cases

        // Left Left Case
        if (balance > 1 && key.compareTo(node.left.element)<0) {
            return rightRotate(node);
        }

        // Right Right Case
        if (balance < -1 && key.compareTo(node.right.element) > 0) {
            return leftRotate(node);
        }

        // Left Right Case
        if (balance > 1 && key.compareTo(node.left.element) > 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right Left Case
        if (balance < -1 && key.compareTo(node.right.element)<0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }


        // Return the (unchanged) node pointer
        return node;
    }

    public void insert(T key) {
        root = insert(root, key);
    }


    public void delete(T element) {
        root = deleteRec(root, element);
    }

    private Node<T> deleteRec(Node<T> node, T element) {
        if (node == null) return node;

        if (element.compareTo(node.element) < 0) {
            node.left = deleteRec(node.left, element);
        } else if (element.compareTo(node.element) > 0) {
            node.right = deleteRec(node.right, element);
        } else {
            // Node with one child or no child
            if ((node.left == null) || (node.right == null)) {
                node = (node.left != null) ? node.left : node.right;
            } else {
                // Node with two children: Get the inorder successor
                Node<T> temp = findMin(node.right);
                node.element = temp.element;
                node.right = deleteRec(node.right, temp.element);
            }
        }

        if (node == null) return node;

        return rebalance(node);
    }

    private Node<T> findMin(Node<T> node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    public boolean contains(T element) {
        return containsRec(root, element);
    }

    private boolean containsRec(Node<T> node, T element) {
        if (node == null) return false;

        if (element.compareTo(node.element) < 0) {
            return containsRec(node.left, element);
        } else if (element.compareTo(node.element) > 0) {
            return containsRec(node.right, element);
        } else {
            return true;
        }
    }

    @Override
    public void inorder(List<T> list) {
        super.inorder(list);
    }


    public void levelorder(List<T> list) {
        if (root == null) {
            return;
        }

        Queue<Node<T>> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            Node<T> current = queue.poll();
            list.add(current.element);
            if (current.left != null) {
                queue.add(current.left);
            }
            if (current.right != null) {
                queue.add(current.right);
            }
        }
    }


}



