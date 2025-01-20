import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;


public class HW05Test {
  private ArrayHeap<Integer, String> heap;

  @BeforeEach
  void setUp() {
    heap = new ArrayHeap<>(7);
  }

  @Test
  @DisplayName("Should insert an element to the heap")
  public void testInsert() {
    assertEquals(0, heap.size());
    assertTrue(heap.isEmpty());

    heap.insert(10, "Ten");
    assertEquals(1, heap.size());
    assertFalse(heap.isEmpty());

    heap.insert(20, "Twenty");
    assertEquals("Ten", heap.peek());

    heap.insert(5, "Five");
    assertEquals("Five", heap.peek());

    heap.insert(30, "Thirty");
    assertEquals("Five", heap.peek());

    heap.insert(15, "Fifteen");
    assertEquals("Five", heap.peek());

    heap.insert(25, "Twenty-Five");
    assertEquals("Five", heap.peek());

    heap.insert(1, "One");
    assertEquals("One", heap.peek());

    assertEquals(7, heap.size());
    assertFalse(heap.isEmpty());

    heap.insert(100, "One Hundred");
    assertEquals(7, heap.size());
  }

  @Test
  @DisplayName("Should get array-based heap.")
  void testGetHeap() {
    assertEquals(null, heap.getHeap()[0]);

    heap.insert(10, "Ten");
    heap.insert(1, "One");

    Entry<Integer, String>[] hp = heap.getHeap();
    assertEquals("One", hp[0].getValue());
    assertEquals("Ten", hp[1].getValue());
    assertEquals(null, hp[2]);
    assertEquals(null, hp[3]);
    assertEquals(null, hp[4]);
    assertEquals(null, hp[5]);
    assertEquals(null, hp[6]);
  }

  @Test
  void testLevelorder() {
    heap.insert(10, "Ten");
    heap.insert(20, "Twenty");
    heap.insert(5, "Five");
    heap.insert(30, "Thirty");
    heap.insert(15, "Fifteen");

    List<String> expected = List.of("Five", "Fifteen", "Ten", "Thirty", "Twenty");
    List<String> result = new ArrayList<>();
    heap.levelorder(result);

    assertEquals(expected, result);
  }

  @Test
  @DisplayName("Should remove an element from the heap.")
  void testRemove() {
    assertEquals(null, heap.remove());

    heap.insert(10, "Ten");
    heap.insert(20, "Twenty");
    heap.insert(5, "Five");
    heap.insert(30, "Thirty");
    heap.insert(15, "Fifteen");
    heap.insert(25, "Twenty-Five");
    heap.insert(1, "One");

    assertEquals(7, heap.size());
    assertFalse(heap.isEmpty());
    assertEquals("One", heap.peek());

    assertEquals("One", heap.remove());
    assertEquals(6, heap.size());
    assertFalse(heap.isEmpty());
    assertEquals("Five", heap.peek());

    assertEquals("Five", heap.remove());
    assertEquals(5, heap.size());
    assertFalse(heap.isEmpty());
    assertEquals("Ten", heap.peek());

    assertEquals("Ten", heap.remove());
    assertEquals(4, heap.size());
    assertFalse(heap.isEmpty());
    assertEquals("Fifteen", heap.peek());

    assertEquals("Fifteen", heap.remove());
    assertEquals(3, heap.size());
    assertFalse(heap.isEmpty());
    assertEquals("Twenty", heap.peek());

    assertEquals("Twenty", heap.remove());
    assertEquals(2, heap.size());
    assertFalse(heap.isEmpty());
    assertEquals("Twenty-Five", heap.peek());

    assertEquals("Twenty-Five", heap.remove());
    assertEquals(1, heap.size());
    assertFalse(heap.isEmpty());
    assertEquals("Thirty", heap.peek());

    assertEquals("Thirty", heap.remove());
    assertEquals(0, heap.size());
    assertTrue(heap.isEmpty());
    assertEquals(null, heap.peek());

    assertEquals(null, heap.remove());
  }

  @Test
  void testMerge() {
    heap = new ArrayHeap<>(2);
    heap.insert(4, "Four");
    heap.insert(2, "Two");

    ArrayHeap<Integer, String> heap2 = new ArrayHeap<>(2);
    heap2.insert(1, "One");
    heap2.insert(3, "Three");

    ArrayHeap<Integer, String> merged = ArrayHeap.merge(heap, heap2);
    assertEquals("One", merged.remove());
    assertEquals("Two", merged.remove());
    assertEquals("Three", merged.remove());
    assertEquals("Four", merged.remove());
  }

  @Test
  @DisplayName("Should heapify properly.")
  void testHeapify() {
    int[] array = { 4, 10, 3, 5, 1 };
    int n = array.length;
    int parent = 0;

    int[] expected = { 10, 5, 3, 4, 1 };

    HW05.heapify(array, n, parent);
    assertArrayEquals(expected, array);

    array = new int[] { 10, 5, 3, 4, 1 };
    parent = 1;

    expected = new int[] { 10, 5, 3, 4, 1 };

    HW05.heapify(array, n, parent);
    assertArrayEquals(expected, array);
  }

  @Test
  @DisplayName("Should do heap sort in a given array.")
  void testHeapSort() {
    int[] arr = new int[] { -5, 23, -6, 3 };
    HW05.heapSort(arr);
    assertEquals(-6, arr[0]);
    assertEquals(-5, arr[1]);
    assertEquals(3, arr[2]);
    assertEquals(23, arr[3]);

    arr = new int[] { 3, 1, 4, 3, 5, 2, 1 };
    int[] expected = { 1, 1, 2, 3, 3, 4, 5 };

    HW05.heapSort(arr);
    assertArrayEquals(expected, arr);
  }
}
