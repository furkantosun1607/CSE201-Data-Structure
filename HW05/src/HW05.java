/*
 * There might be some chanes required in the main method to test
 */

import java.util.ArrayList;
import java.util.LinkedList;

public class HW05 {
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
        ArrayList arrayList1 = new ArrayList();
        System.out.println("Heap after insertions (level-order):");
        heap.levelorder(arrayList1);
        arrayList1.forEach(System.out::println);

        //NodeHeap<Integer, String> heap2 = new NodeHeap<>();
        ArrayHeap<Integer, String> heap2 = new ArrayHeap<>(500);

        heap2.insert(8, "eight");
        heap2.insert(23, "Twenty three");
        heap2.insert(3, "three");
        heap2.insert(27, "Twenty seven");
        heap2.insert(16, "sixten");
        heap2.insert(22, "Twenty-two");
        heap2.insert(2, "two");

        // Display the heap (level-order)
        System.out.println("\nHeap after insertions (level-order):");
        ArrayList arrayList = new ArrayList();
        heap2.levelorder(arrayList);
        arrayList.forEach(System.out::println);

        System.out.println("\nHeap after merge (level-order):");
        //var heap3 = NodeHeap.merge(heap, heap2, 6, "Six");
        var heap3 = ArrayHeap.merge(heap, heap2);
        ArrayList arrayList2 = new ArrayList();
        heap3.levelorder(arrayList2);
        arrayList2.forEach(System.out::println);
        //System.out.println(heap3.getRoot().entry.getValue());

        /* int[] array = {1, 5, 3, 2, 6, 4};
        heapSort(array);
        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i]);
        } */
    }


    public static void heapSort(int[] array) {
        int n = array.length;

        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(array, n, i);
        }


        for (int i = n - 1; i > 0; i--) {

            int temp = array[0];
            array[0] = array[i];
            array[i] = temp;

            heapify(array, i, 0);
        }
    }

    public static void heapify(int[] array, int n, int i) {
        int biggest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;


        if (left < n && array[left] > array[biggest]) {
            biggest = left;
        }


        if (right < n && array[right] > array[biggest]) {
            biggest = right;
        }


        if (biggest != i) {

            int swap = array[i];
            array[i] = array[biggest];
            array[biggest] = swap;

            heapify(array, n, biggest);
        }
    }


    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

}

class Entry <K extends Comparable <? super K>, V> implements Comparable<K> {
/*
 * Constructor(K, V)
 * getValue()
 * getKey()
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

    @Override
    public int compareTo(K o) {
        return key.compareTo(o);
    }
} 

class TreeNode <T> {
    /*
     * Constructor(T entry)
     * entry, left, right
     */
    T entry;
    TreeNode<T> left, right;

    public TreeNode(T entry) {
        this.entry = entry;
    }
    public String toString() {return "Node(" + entry + ")";}
    public TreeNode<T> getLeft() {return left;}
    public T getData() {return entry;}
    public TreeNode<T> getRight() {return right;}
    public void setLeft(TreeNode<T> left) {this.left = left;}
    public void setRight(TreeNode<T> right) {this.right = right;}
    public void setData(T data) {this.entry = data;}

}

interface List <T> {
    int size();
    boolean isEmpty();
}

interface PriorityQueue <P, E> extends List <E> {
    E remove();
    E peek();
    void insert(P priority, E element);
}

/*
 * Array-based Min-heap implementation
 */
class ArrayHeap <K extends Comparable<? super K>, V> implements PriorityQueue <K, V> {

    private Entry<K, V>[] heap;
    private int size;




    public Entry<K, V>[] getHeap() {
        // Convenience method
        return heap;
    }

    /*
     * Constructor(int capacity)
     */
    public ArrayHeap(int capacity) {
        heap = new Entry[capacity];
        size = 0;
    }

    /*
     * heapifyUp(index)
     */
    private void heapifyUp(int index) {
        int parentIndex = (index - 1) / 2;

        while (index > 0 && heap[index].compareTo(heap[parentIndex].getKey()) < 0) {
            swap(index, parentIndex);
            index = parentIndex;
            parentIndex = (index - 1) / 2;
        }
    }

     /*
      * heapifyDown(index)
      */
     private void heapifyDown(int index) {
         int smallest = index;
         int left = 2 * index + 1;
         int right = 2 * index + 2;

         if (left < size && heap[left].compareTo(heap[smallest].getKey()) < 0) {
             smallest = left;
         }
         if (right < size && heap[right].compareTo(heap[smallest].getKey()) < 0) {
             smallest = right;
         }

         if (smallest != index) {
             swap(index, smallest);
             heapifyDown(smallest);
         }
     }


    public void insert(K priority, V element) {
        if (size == heap.length) {
            return;
        }
        heap[size] = new Entry<>(priority, element);
        heapifyUp(size);
        size++;
    }

    public V remove() {
        if (isEmpty()) {
            return null;
        }
        V removedValue = heap[0].getValue();
        heap[0] = heap[size - 1];
        size--;
        heapifyDown(0);
        return removedValue;
    }

    public V peek() {
        if (isEmpty()) {
            return null;
        }
        return heap[0].getValue();
    }


    /* merge two given heaps
     * static <K extends Comparable<? super K>, V> ArrayHeap<K, V> merge(ArrayHeap<K, V> heap1, ArrayHeap<K, V> heap2)
     */
    public static <K extends Comparable<? super K>, V> ArrayHeap<K, V> merge(ArrayHeap<K, V> heap1, ArrayHeap<K, V> heap2) {
        ArrayHeap<K, V> mergedHeap = new ArrayHeap<>(heap1.size + heap2.size);

        for (int i = 0; i < heap1.size; i++) {
            mergedHeap.insert(heap1.heap[i].getKey(), heap1.heap[i].getValue());
        }

        for (int i = 0; i < heap2.size; i++) {
            mergedHeap.insert(heap2.heap[i].getKey(), heap2.heap[i].getValue());
        }

        return mergedHeap;
    }

    private void swap(int index, int otherIndex) {
        Entry<K, V> temp = heap[index];
        heap[index] = heap[otherIndex];
        heap[otherIndex] = temp;
    }

    /*
    * adds elements to a list in BFS fashion
     * levelorder(List<V> list)
     */
    public void levelorder(java.util.List list) {
        for (int i = 0; i < size; i++) {
            list.add(heap[i].getValue());
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }
}