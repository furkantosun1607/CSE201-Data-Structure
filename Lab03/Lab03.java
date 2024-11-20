import java.util.Random;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Arrays;

public class Lab03 {

    // in this lab section we compared time complexity of some searching and sorting algorithm

    public static void main(String[] args) {
        List<Integer> array_list = new ArrayList<>();
        List<Integer> linked_list = new LinkedList<>();
        int[] n = {1000, 10000, 100000};  // Farklı boyutlardaki diziler

        for (int i = 0; i < n.length; i++) {
            int[] randomArray1 = generateRandomArray(n[i]);
            int[] randomArray2 = randomArray1.clone();  // MergeSort ve BubbleSort için aynı diziyi kullan

            System.out.println("Dizi boyutu: " + n[i]);

            // BubbleSort'u test et
            Runnable bubbleSortAlgorithm = () -> bubbleSort(randomArray1);
            double bubbleSortTime = measureExecutionTime(bubbleSortAlgorithm);
            System.out.printf("BubbleSort took: %.4f seconds\n", bubbleSortTime);

            // MergeSort'u test et
            Runnable mergeSortAlgorithm = () -> mergeSort(randomArray2, 0, randomArray2.length - 1);
            double mergeSortTime = measureExecutionTime(mergeSortAlgorithm);
            System.out.printf("MergeSort took: %.4f seconds\n", mergeSortTime);

            System.out.println("---------------------------------");
            int arraySize = 10;
            int[] randomArray = new int[arraySize];
            Random random = new Random();


        }


        int arraySize = 10;
        int[] randomArray = new int[arraySize];
        Random random = new Random();

        for (int i = 0; i < arraySize; i++) {
            randomArray[i] = random.nextInt(100) + 1;
        }

        System.out.println("Rastgele Dizi: " + Arrays.toString(randomArray));


        int[] bubbleSortedArray = randomArray.clone();
        bubbleSort(bubbleSortedArray);
        System.out.println("Bubble Sort Çıktısı: " + Arrays.toString(bubbleSortedArray));


        int[] mergeSortedArray = randomArray.clone();
        mergeSort(mergeSortedArray, 0, mergeSortedArray.length - 1);
        System.out.println("Merge Sort Çıktısı: " + Arrays.toString(mergeSortedArray));





    }

    public static double measureExecutionTime(Runnable algorithm) {
        long startTime = System.currentTimeMillis();
        algorithm.run();
        long endTime = System.currentTimeMillis();
        return (endTime - startTime) / 1000.0;
    }

    public static int[] generateRandomArray(int size) {
        Random random = new Random();
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(10000);  // Random integers between 0 and 9999
        }
        return array;
    }

    public static int[] generateSortedArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = i;  // Sorted array from 0 to size-1
        }
        return array;
    }

    public static void listInsertion(int n, List<Integer> list) {
        // O(n)
        System.out.println("Time complexity: O(n)");
        for (int i = 0; i < n; i++) {
            list.add(0, i);  // Insert at the front of the list
        }
    }

    public static void accessElement(int[] array, int index) {
        // O(1)
        System.out.println("O(1)");
        int value = array[index];
    }

    public static int findMax(int[] array) {
        // O(n)
        System.out.println("Time complexity: O(n)");
        int max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        return max;
    }

    public static void bubbleSort(int[] array) {
        System.out.println("O(n**2)");
        int n = array.length;
        boolean swapped;
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    // Swap array[j] and array[j + 1]
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    swapped = true;
                }
            }

            if (!swapped) break;
        }
    }


    public static void mergeSort(int[] array, int left, int right) {
        System.out.println("O(nlogn)");
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSort(array, left, mid);
            mergeSort(array, mid + 1, right);
            merge(array, left, mid, right);
        }
    }

    public static void merge(int[] array, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        int[] L = new int[n1];
        int[] R = new int[n2];

        // Copy data to temp arrays
        for (int i = 0; i < n1; i++) {
            L[i] = array[left + i];
        }
        for (int i = 0; i < n2; i++) {
            R[i] = array[mid + 1 + i];
        }

        // Merge the temp arrays
        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                array[k] = L[i];
                i++;
            } else {
                array[k] = R[j];
                j++;
            }
            k++;
        }

        // Copy remaining elements of L[] and R[] if any
        while (i < n1) {
            array[k] = L[i];
            i++;
            k++;
        }
        while (j < n2) {
            array[k] = R[j];
            j++;
            k++;
        }
    }


    public static int binarySearch(int[] array, int target) {
        System.out.println("O(logn)");
        int low = 0;
        int high = array.length - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (array[mid] == target) {
                return mid;
            }

            if (array[mid] < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;  // Target not found
    }
}
