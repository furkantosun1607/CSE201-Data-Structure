import java.util.LinkedList;
import java.util.Queue;

public class Lab09 {

    public static void main(String[] args) {


        ArrayHeap<Integer, String> heap = new ArrayHeap<>(500);

        // Inserting elements into the heap
        heap.insert(10, "Ten");
        heap.insert(20, "Twenty");
        heap.insert(5, "Five");
        heap.insert(30, "Thirty");
        heap.insert(15, "Fifteen");
        heap.insert(25, "Twenty-Five");
        heap.insert(1, "One");

        // Display the heap (level-order)
        System.out.println("Heap after insertions (level-order):");
        heap.levelorder();

        //NodeHeap<Integer, String> heap2 = new NodeHeap<>();
        ArrayHeap<Integer, String> heap2 = new ArrayHeap<>(500);

        heap2.insert(8, "Ten");
        heap2.insert(23, "Twenty");
        heap2.insert(3, "Five");
        heap2.insert(27, "Thirty");
        heap2.insert(16, "Fifteen");
        heap2.insert(22, "Twenty-Five");
        heap2.insert(2, "One");

                // Display the heap (level-order)
        System.out.println("\nHeap after insertions (level-order):");
        heap2.levelorder();

        System.out.println("\nHeap after merge (level-order):");
        //var heap3 = NodeHeap.merge(heap, heap2, 6, "Six");
        var heap3 = ArrayHeap.merge(heap, heap2);
        heap3.levelorder();
        //System.out.println(heap3.getHeap()[1]);

         int[] array = {1, 5, 3, 2, 6, 4};
        heapSort(array);
        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i]);
        }

    }

    public static void heapSort(int[] array) {
        /*
         * heap sort implementation
         */
    }

    public static void heapify(int[] array, int n, int parent) {
        /*
         * heapify implementation
         */
    }

    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }


}

interface List <T> {
    int size();
    boolean isEmpty();
}

interface PriorityQueue <P, E>  {
    E remove();
    E peek();
    void insert(P priority, E element);
}





class Entry <K extends Comparable <? super K>, V> implements Comparable<K> {
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

    @Override
    public int compareTo(K o) {
        return key.compareTo(o);
    }
}







/*
 * Array-based Min-heap implementation
 */
//class ArrayHeap <K extends Comparable<? super K>, V> implements PriorityQueue <K, V> {
//
//    public Entry<K, V>[] getHeap() {
//        // Convenience method
//        return heap;
//    }
//
//
//    /*
//     * Constructor(int capacity)
//     */
//
//    /*
//     * heapifyUp(index)
//     */
//
//     /*
//      * heapifyDown(index)
//      */
//
//    /* merge two given heaps
//     * static <K extends Comparable<? super K>, V> ArrayHeap<K, V> merge(ArrayHeap<K, V> heap1, ArrayHeap<K, V> heap2)
//     */
//
//    private void swap(int index, int otherIndex) {
//        Entry<K, V> temp = heap[index];
//        heap[index] = heap[otherIndex];
//        heap[otherIndex] = temp;
//    }

    /*
    * prints the heap BFS
     * levelorder()
     */
//}

/*
 * Node-based Min-heapimplementation
 */

class ArrayHeap<K extends Comparable<? super K>, V> implements PriorityQueue<K, V> {
    private Entry<K, V>[] heap;
    private int size;

    public Entry<K, V>[] getHeap() {
        // Convenience method
        return heap;
    }


    /*
     * Constructor(int capacity)
     */
    @SuppressWarnings("unchecked")
    public ArrayHeap(int capacity) {
        heap = new Entry[capacity];
        size = 0;
    }

    /*
     * heapifyUp(index)
     */
    private void heapifyUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (heap[index].compareTo(heap[parent].getKey()) >= 0)
                break;

            swap(index, parent);
            index = parent;
        }
    }

    /*
     * heapifyDown(index)
     */
    private void heapifyDown(int index) {
        int left = 2 * index + 1;
        int rigth = 2 * index + 2;
        int smallest = index;

        if (left < size && heap[left].compareTo(heap[smallest].getKey()) < 0)
            smallest = left;

        if (rigth < size && heap[rigth].compareTo(heap[smallest].getKey()) < 0)
            smallest = rigth;

        if (smallest != index) {
            swap(index, smallest);
            heapifyDown(smallest);
        }
    }

    /*
     * merge two given heaps
     * static <K extends Comparable<? super K>, V> ArrayHeap<K, V>
     * merge(ArrayHeap<K, V> heap1, ArrayHeap<K, V> heap2)
     */
    public static <K extends Comparable<? super K>, V> ArrayHeap<K, V> merge(ArrayHeap<K, V> heap1,
                                                                             ArrayHeap<K, V> heap2) {
        ArrayHeap<K, V> mergedHeap = new ArrayHeap<>(heap1.size + heap2.size);
        for (int i = 0; i < heap1.size; i++)
            mergedHeap.insert(heap1.heap[i].getKey(), heap1.heap[i].getValue());

        for (int i = 0; i < heap2.size; i++)
            mergedHeap.insert(heap2.heap[i].getKey(), heap2.heap[i].getValue());

        return mergedHeap;
    }

    private void swap(int index, int otherIndex) {
        Entry<K, V> temp = heap[index];
        heap[index] = heap[otherIndex];
        heap[otherIndex] = temp;
    }


    public int size() {
        return size;
    }


    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public V remove() {
        Entry<K, V> root = heap[0];

        if (size > 0) {
            heap[0] = heap[size - 1];
            size--;
            heapifyDown(0);
        }

        return root.getValue();
    }

    @Override
    public V peek() {
        if (size == 0)
            return null;

        return heap[0].getValue();
    }

    @Override
    public void insert(K priority, V element) {
        if (size < heap.length) {
            heap[size] = new Entry<>(priority, element);
            heapifyUp(size);
            size++;
        }
    }

    /*
     * prints the heap BFS
     * levelorder()
     */
    public void levelorder() {
        for (int i = 0; i < size; i++)
            System.out.print(heap[i].getValue() + " ");


    }

}







class TreeNode <T> {
    T entry;
    TreeNode<T> left;
    TreeNode<T> right;
    TreeNode<T> parrent;

    public TreeNode(T entry) {
        this.entry = entry;
    }
    public void setEntry(T entry) {this.entry = entry;}
    public void setLeft(TreeNode<T> left) {this.left = left;}
    public void setRight(TreeNode<T> right) {this.right = right;}
    public void setParrent(TreeNode<T> parrent) {this.parrent = parrent;}
    public TreeNode<T> getLeft() {return left;}
    public TreeNode<T> getRight() {return right;}
    public TreeNode<T> getParrent() {return parrent;}
}



class NodeHeap <K extends Comparable<? super K>, V> implements PriorityQueue <K, V> {
    private TreeNode<Entry<K,V>> root;
    private int size;

    public NodeHeap() {
        root = null;
        size = 0;
    }
    public TreeNode<Entry<K, V>> getRoot() {
        // Convenience method
        return root;
    }

    /*
     * heapifyUp(node)
     */

    private void heapifyUp(TreeNode<Entry<K, V>> node) {
        while (node != null && node.getParrent() != null && node.entry.compareTo(node.getParrent().entry.getKey()) < 0) {
            swap(node, node.getParrent());
            node = node.getParrent();
        }
    }


    /*
     * heapifyDown(node)
     */
    private void heapifyDown(TreeNode<Entry<K, V>> node) {
        TreeNode<Entry<K, V>> smallChild = getLowChild(node);
        if (smallChild != null && node.entry.compareTo(smallChild.entry.getKey()) > 0) {
            swap(node, smallChild);
            heapifyDown(smallChild);
        }
    }




    

     /* finds the location of last node to remove
      * node findLastNode()
      */
    
      /* finds the first parrent with available location for insertion
       * node findParrent()
       */
      private TreeNode<Entry<K, V>> getLowChild(TreeNode<Entry<K, V>> node) {
          if (node.left == null && node.right == null)
              return null;

          if (node.right == null)
              return node.left;

          return (node.left.entry.compareTo(node.right.entry.getKey()) < 0) ? node.left : node.right;
      }

    /*
     * finds the location of last node to remove
     * node findLastNode()
     */
    private TreeNode<Entry<K, V>> findLastNode(TreeNode<Entry<K, V>> node) {
        if (node == null)
            return null;

        Queue<TreeNode<Entry<K, V>>> queue = new LinkedList<>();
        queue.add(node);

        TreeNode<Entry<K, V>> lastNode = null;
        while (!queue.isEmpty()) {
            lastNode = queue.poll();
            if (lastNode.left != null)
                queue.add(lastNode.left);

            if (lastNode.right != null)
                queue.add(lastNode.right);
        }

        return lastNode;
    }

    private void swap(TreeNode<Entry<K, V>> node, TreeNode<Entry<K, V>> otherNode) {
        Entry<K, V> tempEntry = node.entry;
        node.entry = otherNode.entry;
        otherNode.entry = tempEntry;
    }

    @Override
    public V remove() {
        if (root == null)
            return null;

        Entry<K, V> rootEntry = root.entry;
        if (root.left == null && root.right == null)
            root = null;
        else {
            TreeNode<Entry<K, V>> lastNode = findLastNode(root);
            swap(root, lastNode);
            removeLastNode(lastNode);
            heapifyDown(root);
        }

        size--;
        return rootEntry.getValue();
    }

    private void removeLastNode(TreeNode<Entry<K, V>> node) {
        if (node.parrent != null) {
            if (node.parrent.left == node)
                node.parrent.left = null;
            else
                node.parrent.right = null;
        }
    }

    @Override
    public V peek() {
        if (size == 0)
            return null;

        return root.entry.getValue();
    }

    @Override
    public void insert(K priority, V element) {
        TreeNode<Entry<K, V>> newNode = new TreeNode<>(new Entry<>(priority, element));
        if (root == null)
            root = newNode;
        else
            insertNode(root, newNode);

        size++;
        heapifyUp(newNode);
    }

    private void insertNode(TreeNode<Entry<K, V>> parent, TreeNode<Entry<K, V>> node) {
        if (parent.left == null) {
            parent.left = node;
            node.parrent = parent;
        } else if (parent.right == null) {
            parent.right = node;
            node.parrent = parent;
        } else
            insertNode(parent.left, node);
    }


    public int size() {
        return this.size;
    }


    public boolean isEmpty() {
        return size==0;
    }

    /*  merges 2 heaps with given new entry
     * static <K extends Comparable<? super K>, V> NodeHeap<K, V> merge(NodeHeap<K, V> heap1, NodeHeap<K, V> heap2, K priority, V value)
     */

    public static <K extends Comparable<? super K>, V> NodeHeap<K, V> merge(NodeHeap<K, V> heap1, NodeHeap<K, V> heap2,
                                                                            K priority, V value) {
        NodeHeap<K, V> mergedHeap = new NodeHeap<>();
        Queue<TreeNode<Entry<K, V>>> queue = new LinkedList<>();
        queue.add(heap1.root);
        while (!queue.isEmpty()) {
            TreeNode<Entry<K, V>> node = queue.poll();
            mergedHeap.insert(node.entry.getKey(), node.entry.getValue());
            if (node.left != null)
                queue.add(node.left);

            if (node.right != null)
                queue.add(node.right);
        }

        queue.add(heap2.root);
        while (!queue.isEmpty()) {
            TreeNode<Entry<K, V>> node = queue.poll();
            mergedHeap.insert(node.entry.getKey(), node.entry.getValue());
            if (node.left != null)
                queue.add(node.left);

            if (node.right != null)
                queue.add(node.right);
        }

        // Finally, insert the new entry to the merged heap
        mergedHeap.insert(priority, value);

        return mergedHeap;
    }

    /*
     * prints the heap BFS
     * levelorder()
     */
    public void levelorder() {
        if (root == null)
            return;

        Queue<TreeNode<Entry<K, V>>> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            TreeNode<Entry<K, V>> node = queue.poll();
            System.out.print(node.entry.getValue() + " ");
            if (node.left != null)
                queue.add(node.left);

            if (node.right != null)
                queue.add(node.right);
        }

        System.out.println();
    }

}

