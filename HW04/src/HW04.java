import java.util.*;

public class HW04 {
    public static void main(String[] args) {



    }

}



interface Tree<T> {
    int size();
    boolean isEmpty();
    void insert(T element);
    boolean remove(T element);
    boolean contains(T element);
    void levelorder(List<T> list);
    void inorder(List<T> list);
    void preorder(List<T> list);
    void postorder(List<T> list);
}


class TreeNode<T> {
    T element;
    TreeNode<T> left, right;

    public TreeNode(T element) {
        this.element = element;
    }

    public String toString() {
        return "Node(" + element + ")";
    }
    public TreeNode<T> getLeft() {
        return left;
    }
    public T getData() {return element;}
    public TreeNode<T> getRight() {
        return right;
    }
    public void setLeft(TreeNode<T> left) {
        this.left = left;
    }
    public void setRight(TreeNode<T> right) {
        this.right = right;
    }
    public void setData(T data) {this.element = data;}

}

/*
 * Node-based BST implementation
 */
class BSTNode <T extends Comparable<? super T>> implements Tree<T> {
    private TreeNode<T> root;
    private  int size;

    /*
     * Constructor()
     */


    public BSTNode() {
        this.root = null;
        int size=0;
    }
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }
    public TreeNode<T> getRoot() {
        return root;
    }


// aynı sekilde bu metodun doğrulugundan emin değilim kbeyde kendim yazmıstım
    public void insert(T value) {
//        TreeNode<T> node = new TreeNode<>(value);
//        if (root == null) {
//            this.root=node;
//
//
//        }
//        else{
//            TreeNode<T> current = root;
//            while(current.getLeft()!=null && current.getRight()!=null){
//                if(value.compareTo(current.getData())>0){
//                    current = current.getRight();
//                }else{
//                    current = current.getLeft();
//                }
//            }
//            if(value.compareTo(current.getData())<0 && current.getLeft()==null){
//                current.setLeft(node);
//            }
//            else
//                current.setRight(node);
//
//        }
//        size++;
        root = insertRec(root, value);

    }
    private TreeNode<T> insertRec(TreeNode<T> current, T element) {

        if (current == null) {
            return new TreeNode<>(element);
        }


        if (element.compareTo(current.getData()) < 0) {
            current.setLeft(insertRec(current.getLeft(), element));
        } else if (element.compareTo(current.getData()) > 0) {
            current.setRight(insertRec(current.getRight(), element));
        }


        return current;
    }


    public boolean contains(T element) {
        return containsRec(root, element);
    }

    private boolean containsRec(TreeNode<T> node, T element) {
        if (node == null)
            return false;
        if (element.compareTo(node.element) == 0)
            return true;
        return element.compareTo(node.element) < 0
                ? containsRec(node.left, element)
                : containsRec(node.right, element);
    }




    public boolean remove(T element) {
        if (contains(element)) {
            root = removeRec(root, element);
            size--;
            return true;
        }
        return false;
    }


    protected TreeNode<T> removeRec(TreeNode<T> node, T element) {
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

    // Min düğümü bulma
    private T findMin(TreeNode<T> node) {
        TreeNode<T> current = node;
        while (current.left != null)
            current = current.left;
        return current.element;
    }

    private T findMax(TreeNode<T> node) {
        TreeNode<T> current = node;
        while(current.right != null)
            current = current.right;
        return current.element;
    }



    // list parametresi ne alaka
    // listi levelorder traverse ile dolduracak
    @Override
    public void levelorder(List<T> list) {
        if (root == null) {
            return;
        }

        Queue<TreeNode<T>> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            TreeNode<T> current = queue.poll();
            list.add(current.element);
            if (current.left != null) {
                queue.add(current.left);
            }
            if (current.right != null) {
                queue.add(current.right);
            }
        }
    }


    @Override
    public void inorder(List<T> list) {

        inorderRec(root, list);
    }

    private void inorderRec(TreeNode<T> node, List<T> list) {
        if (node == null)
            return;

        inorderRec(node.left, list);
        list.add(node.element);
        inorderRec(node.right, list);
    }


    @Override
    public void preorder(List<T> list) {
        preorderRec(root,list);
    }
    private void preorderRec(TreeNode<T> node, List<T> list) {
        if (node == null)
            return;
        list.add(node.element);
        preorderRec(node.left, list);
        preorderRec(node.right, list);
    }

    @Override
    public void postorder(List<T> list) {
        postorderRec(root,list);
    }

    private void postorderRec(TreeNode<T> node, List<T> list) {
        if (node == null)
            return;

        postorderRec(node.left, list);
        postorderRec(node.right, list);
        list.add(node.element);
    }

}

/*
 * Array-based BST implementation
 */


class BSTArray <T extends Comparable<? super T>> implements Tree<T> {
    private int size;

    private T[] array;

    public T[] getArray() {
        return array;
    }

    public BSTArray(int capacity) {
        array = (T[]) new Comparable[capacity];
        size = 0;
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
        if (array[0] == null) {
            array[0] = element;
            size++;
            return;
        }
        insertRecursive(0, element);
    }
    private void insertRecursive(int index, T value) {

        if (array[index] == null) {
            array[index] = value;
            size++;
            return;
        }
        if (value.compareTo(array[index]) < 0) {
            // Left child
            insertRecursive(2 * index + 1, value);
        } else if (value.compareTo(array[index]) > 0) {
            // Right child
            insertRecursive(2 * index + 2, value);
        }

    }

    @Override
    public boolean remove(T element) {
        if (contains(element)) {
            removeRecursive(0, element);
            size--;
            return true;
        }
        return false;
    }
    private void removeRecursive(int index, T value) {
        if (index >= array.length || array[index] == null) {
            return;
        }
        if (value.compareTo(array[index]) == 0) {

            if (isLeaf(index)) {
                array[index] = null;
            } else if (hasOneChild(index)) {

                int childIndex = (array[2 * index + 1] != null) ? 2 * index + 1 : 2 * index + 2;
                array[index] = array[childIndex];
                removeRecursive(childIndex, array[childIndex]);
            } else {

                int minIndex = findMinIndex(2 * index + 2); // Right child subtree
                array[index] = array[minIndex];
                removeRecursive(minIndex, array[minIndex]);
            }
        } else if (value.compareTo(array[index]) < 0) {
            // Left child
            removeRecursive(2 * index + 1, value);
        } else {
            // Right child
            removeRecursive(2 * index + 2, value);
        }
    }

    private int findMinIndex(int index) {
        while (2 * index + 1 < array.length && array[2 * index + 1] != null) {
            index = 2 * index + 1;
        }
        return index;
    }
    private boolean isLeaf(int index) {
        return 2 * index + 1 >= array.length || (array[2 * index + 1] == null && array[2 * index + 2] == null);
    }

    private boolean hasOneChild(int index) {
        return (array[2 * index + 1] == null) ^ (array[2 * index + 2] == null); // XOR: only one child is non-null
    }


  // düzenle
    @Override
    public boolean contains(T element) {
        return containsRecursive(0,element);
    }

    @Override
    public void levelorder(List<T> list) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null) {
                System.out.print(array[i] + " ");
            }
        }
    }

    @Override
    public void inorder(List<T> list) {
    inOrderHelper(0,list);
    }
    private void inOrderHelper(int index,List<T> list) {
        if (index >= array.length || array[index] == null) {
            return;
        }
        inOrderHelper(2 * index + 1,list);
        list.add(array[index]); // Kök
        inOrderHelper(2 * index + 2,list);
    }

    @Override
    public void preorder(List<T> list) {
    preOrderHelper(0,list);
    }
    private void preOrderHelper(int index,List<T> list) {
        if (index >= array.length || array[index] == null) {
            return;
        }
        list.add(array[index]); // Kök
        preOrderHelper(2 * index + 1,list);
        preOrderHelper(2 * index + 2,list);
    }

    @Override
    public void postorder(List<T> list) {
        postOrderHelper(0,list);
    }
    private void postOrderHelper(int index,List<T> list) {
        if (index >= array.length || array[index] == null) {
            return;
        }
        postOrderHelper(2 * index + 1,list);
        postOrderHelper(2 * index + 2,list);
        list.add(array[index]); // Kök
    }

    private boolean containsRecursive(int index, T value) {
        if (index >= array.length || array[index] == null) {
            return false; // Value not found
        }
        if (value.compareTo(array[index]) == 0) {
            return true; // Value found
        }
        if (value.compareTo(array[index]) < 0) {
            // Search in left child
            return containsRecursive(2 * index + 1, value);
        } else {
            // Search in right child
            return containsRecursive(2 * index + 2, value);
        }

    }



}












class ListNode <T> {
    private T data;
    private ListNode<T> next;

    public ListNode(T data, ListNode<T> next) {
        this.data = data;
        this.next = next;
    }

    public T getData() {
        return data;
    }

    public ListNode<T> getNext() {
        return next;
    }

    public void setNext(ListNode<T> next) {
        this.next = next;
    }
}

class Entry <K extends Comparable<? super K>, V> {
    /*
     * key-value data fields
     * Constructor(K, V)
     * getKey()
     * getValue()
     */
    private K key;
    private V value;

    public Entry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

}

interface IPriorityQueue <P extends Comparable<? super P>, E> {
    void insert(P priority, E element);
    E remove();
    E peek();
    boolean isEmpty();
    int size();
}

abstract class AbstractPriorityQueue <P extends Comparable<? super P>, E> implements IPriorityQueue<P, E> {
    protected ListNode<Entry<P, E>> head;
    protected int size;

    public ListNode<Entry<P, E>> getHead() {
        // Convenience for me
        return head;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

}

/*
 * Sorted PQ implementation
 */
class SortedPriorityQueue <P extends Comparable<? super P>, E> extends AbstractPriorityQueue <P, E> {

    public SortedPriorityQueue() {
        this.head=null;
        this.size=0;
    }

    @Override
    public void insert(P priority, E element) {
        Entry<P, E> entry = new Entry<>(priority, element);
        ListNode<Entry<P, E>> newNode = new ListNode<>(entry, null);

        // Liste boşsa, elemanı başa ekle
        if (head == null) {
            this.head = newNode;
            size++;
            return;
        }


        if (head.getData().getKey().compareTo(priority) > 0) {
            newNode.setNext(head);
            head = newNode;
            size++;
            return;
        }


        ListNode<Entry<P, E>> current = head;
        while (current.getNext() != null && current.getNext().getData().getKey().compareTo(priority) <= 0) {
            current = current.getNext();
        }


        newNode.setNext(current.getNext());
        current.setNext(newNode);
        size++;
    }

    @Override
    public E remove() {
        if (head == null) {
            return null;
        }
        ListNode<Entry<P,E>> current = head;
        head = current.getNext();
        current.setNext(null);
        size--;
        return current.getData().getValue();
    }

    @Override
    public E peek() {
        return head.getData().getValue();
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty();
    }

    @Override
    public int size() {
        return super.size();
    }

}

/*
 * Unsorted PQ implementation
 */
class UnsortedPriorityQueue <P extends Comparable<? super P>, E> extends AbstractPriorityQueue <P, E> {

    public UnsortedPriorityQueue() {
        this.head=null;
        this.size=0;
    }

    @Override
    public void insert(P priority, E element) {
        Entry<P, E> entry = new Entry<>(priority, element);
        ListNode<Entry<P,E>> list = new ListNode<>(entry,null);
        if(isEmpty()){
            head=list;
            size++;
            return;
        }
        ListNode<Entry<P,E>> current = head;
        while (current.getNext() != null) {
            current = current.getNext();
        }

        current.setNext(list);
        size++;
    }
    private ListNode<Entry<P, E>> findMaxNode() {
        if (head == null)
            return null;

        ListNode<Entry<P, E>> current = head;
        ListNode<Entry<P, E>> maxNode = head;

        while (current != null) {
            if (current.getData().getKey().compareTo(maxNode.getData().getKey()) < 0) {
                maxNode = current;
            }
            current = current.getNext();
        }

        return maxNode;
    }

    @Override
    public E peek() {
        return findMaxNode().getData().getValue();
    }

    @Override
    public E remove() {
        if(head == null) {
            return null;
        }
        ListNode<Entry<P, E>> current = head, prev = null, maxPrev = null, maxNode = head;

        while (current != null) {
            if (current.getData().getKey().compareTo(maxNode.getData().getKey()) < 0) {
                maxPrev = prev;
                maxNode = current;
            }
            prev = current;
            current = current.getNext();
        }

        // En yüksek öncelikli düğümü bağlı listeden çıkar
        if (maxPrev == null) {
            head = head.getNext(); // Head'i güncelle
        } else {
            maxPrev.setNext(maxNode.getNext()); // Bağlantıyı atla
        }
        size--;
        return maxNode.getData().getValue();
    }

    @Override
    public int size() {
        return super.size();
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty();
    }
}