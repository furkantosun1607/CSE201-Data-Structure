import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import java.util.NoSuchElementException;

public class DoublyCircularLinkedListTest {
  private DoublyCircularLinkedList<Integer> linkedList;

  @BeforeEach
  public void setUp() {
    linkedList = new DoublyCircularLinkedList<>();
  }

  @Test
  @DisplayName("Should add the data as the first element in the linked list")
  void testAddFirst() {
    assertEquals(null, linkedList.getHead());
    assertEquals(0, linkedList.size());

    // 3
    linkedList.addFirst(3);
    assertEquals(1, linkedList.size());
    assertEquals(3, linkedList.getHead().getData()); // head
    assertEquals(3, linkedList.getHead().getNext().getData()); // tail
    assertEquals(3, linkedList.getHead().getPrev().getData()); // tail

    // 2 <--> 3
    linkedList.addFirst(2);
    assertEquals(2, linkedList.size());
    assertEquals(2, linkedList.getHead().getData()); // head
    assertEquals(3, linkedList.getHead().getNext().getData()); // tail
    assertEquals(3, linkedList.getHead().getPrev().getData()); // tail
    assertEquals(2, linkedList.getHead().getNext().getNext().getData()); // head

    // 1 <--> 2 <--> 3
    linkedList.addFirst(1);
    assertEquals(3, linkedList.size());
    assertEquals(1, linkedList.getHead().getData()); // head
    assertEquals(2, linkedList.getHead().getNext().getData()); // middle
    assertEquals(3, linkedList.getHead().getNext().getNext().getData()); // tail
    assertEquals(1, linkedList.getHead().getNext().getNext().getNext().getData()); // head
    assertEquals(3, linkedList.getHead().getPrev().getData()); // tail
    assertEquals(2, linkedList.getHead().getPrev().getPrev().getData()); // middle
    assertEquals(1, linkedList.getHead().getPrev().getPrev().getPrev().getData()); // head
    assertEquals(3, linkedList.getHead().getPrev().getPrev().getPrev().getPrev().getData()); // tail
  }

  @Test
  @DisplayName("Should add the data as the last element in the linked list")
  void testAddLast() {
    assertEquals(null, linkedList.getHead());
    assertEquals(0, linkedList.size());

    // 1
    linkedList.addLast(1);
    assertEquals(1, linkedList.size());
    assertEquals(1, linkedList.getHead().getData()); // head
    assertEquals(1, linkedList.getHead().getNext().getData()); // tail
    assertEquals(1, linkedList.getHead().getPrev().getData()); // tail
    assertEquals(1, linkedList.getHead().getPrev().getPrev().getData()); // head

    // 1 <--> 2
    linkedList.addLast(2);
    assertEquals(2, linkedList.size());
    assertEquals(1, linkedList.getHead().getData()); // head
    assertEquals(2, linkedList.getHead().getNext().getData()); // tail
    assertEquals(2, linkedList.getHead().getPrev().getData()); // tail
    assertEquals(1, linkedList.getHead().getPrev().getPrev().getData()); // head

    // 1 <--> 2 <--> 3
    linkedList.addLast(3);
    assertEquals(3, linkedList.size());
    assertEquals(1, linkedList.getHead().getData()); // head
    assertEquals(2, linkedList.getHead().getNext().getData()); // middle
    assertEquals(3, linkedList.getHead().getNext().getNext().getData()); // tail
    assertEquals(1, linkedList.getHead().getNext().getNext().getNext().getData()); // head
    assertEquals(3, linkedList.getHead().getPrev().getData()); // tail
    assertEquals(2, linkedList.getHead().getPrev().getPrev().getData()); // middle
    assertEquals(1, linkedList.getHead().getPrev().getPrev().getPrev().getData()); // head
    assertEquals(3, linkedList.getHead().getPrev().getPrev().getPrev().getPrev().getData()); // tail
  }

  @Test
  @DisplayName("Should set the current pointer to the head of the linked list and return it")
  void testFirst() {
    assertThrows(NoSuchElementException.class, () -> {
      linkedList.first();
    });

    linkedList.addFirst(3);
    assertEquals(3, linkedList.first());

    linkedList.addLast(4);
    assertEquals(3, linkedList.first());

    linkedList.addFirst(2);
    assertEquals(2, linkedList.first());
  }

  @Test
  @DisplayName("Should return the element of the given index in the linked list.")
  void testGet() {
    assertThrows(IndexOutOfBoundsException.class, () -> {
      linkedList.get(0);
    });

    linkedList.addLast(1);
    linkedList.addLast(2);
    linkedList.addLast(3);

    assertEquals(1, linkedList.get(0));
    assertEquals(2, linkedList.get(1));
    assertEquals(3, linkedList.get(2));

    assertThrows(IndexOutOfBoundsException.class, () -> {
      linkedList.get(4);
    });

    assertThrows(IndexOutOfBoundsException.class, () -> {
      linkedList.get(-1);
    });

    linkedList.removeLast();
    assertEquals(2, linkedList.get(1));

    assertThrows(IndexOutOfBoundsException.class, () -> {
      linkedList.get(2);
    });
  }

  @Test
  @DisplayName("Should return the current pointer of the linked list")
  void testGetCurrent() {
    assertThrows(NoSuchElementException.class, () -> {
      linkedList.getCurrent();
    });

    linkedList.addLast(1);
    linkedList.addLast(2);
    linkedList.addLast(3);

    assertEquals(1, linkedList.getCurrent());

    linkedList.last();
    assertEquals(3, linkedList.getCurrent());

    linkedList.next();
    assertEquals(1, linkedList.getCurrent());

    linkedList.previous();
    assertEquals(3, linkedList.getCurrent());

    linkedList.first();
    assertEquals(1, linkedList.getCurrent());
  }

  @Test
  @DisplayName("Should return the head")
  void testGetHead() {
    assertEquals(null, linkedList.getHead());

    linkedList.addFirst(2);
    assertEquals(2, linkedList.getHead().getData());

    linkedList.addFirst(1);
    assertEquals(1, linkedList.getHead().getData());

    linkedList.addLast(4);
    assertEquals(1, linkedList.getHead().getData());

    linkedList.removeLast();
    linkedList.removeLast();
    assertEquals(1, linkedList.getHead().getData());

    linkedList.removeLast();
    assertEquals(null, linkedList.getHead());
  }

  @Test
  @DisplayName("Should check if the linked list empty")
  void testIsEmpty() {
    assertEquals(true, linkedList.isEmpty());

    linkedList.addFirst(1);
    assertEquals(false, linkedList.isEmpty());

    linkedList.removeFirst();
    assertEquals(true, linkedList.isEmpty());
  }

  @Test
  @DisplayName("Should set the current pointer to the last element and return it")
  void testLast() {
    assertThrows(NoSuchElementException.class, () -> {
      linkedList.last();
    });

    linkedList.addFirst(3);
    assertEquals(3, linkedList.last());

    linkedList.addLast(4);
    assertEquals(4, linkedList.last());
  }

  @Test
  @DisplayName("Should set current pointer to the next")
  void testNext() {
    assertThrows(NoSuchElementException.class, () -> {
      linkedList.next();
    });

    linkedList.addLast(1);
    linkedList.addLast(2);
    linkedList.addLast(3);

    linkedList.next();
    assertEquals(1, linkedList.getCurrent());

    linkedList.next();
    assertEquals(2, linkedList.getCurrent());

    linkedList.next();
    assertEquals(3, linkedList.getCurrent());

    linkedList.next();
    assertEquals(1, linkedList.getCurrent());
  }

  @Test
  @DisplayName("Should set current pointer to the previous")
  void testPrevious() {
    assertThrows(NoSuchElementException.class, () -> {
      linkedList.previous();
    });

    linkedList.addLast(1);
    linkedList.addLast(2);
    linkedList.addLast(3);

    linkedList.previous();
    assertEquals(3, linkedList.getCurrent());

    linkedList.previous();
    assertEquals(2, linkedList.getCurrent());

    linkedList.previous();
    assertEquals(1, linkedList.getCurrent());

    linkedList.previous();
    assertEquals(3, linkedList.getCurrent());
  }

  @Test
  @DisplayName("Should remove the given element from the linked list")
  void testRemove() {
    assertEquals(false, linkedList.remove(0));

    linkedList.addLast(1);
    linkedList.addLast(2);
    linkedList.addLast(3);

    assertEquals(true, linkedList.remove(2));
    assertEquals(2, linkedList.size());
    assertEquals(1, linkedList.getHead().getData());
    assertEquals(3, linkedList.getHead().getNext().getData());
    assertEquals(1, linkedList.getHead().getNext().getNext().getData());
    assertEquals(3, linkedList.getHead().getPrev().getData());
    assertEquals(1, linkedList.getHead().getPrev().getPrev().getData());

    assertEquals(true, linkedList.remove(3));
    assertEquals(1, linkedList.size());
    assertEquals(1, linkedList.getHead().getData());
    assertEquals(1, linkedList.getHead().getNext().getData());
    assertEquals(1, linkedList.getHead().getPrev().getData());

    assertEquals(false, linkedList.remove(3));
    assertEquals(true, linkedList.remove(1));
    assertEquals(0, linkedList.size());

    DoublyCircularLinkedList<String> ll = new DoublyCircularLinkedList<>();
    ll.addLast("one");
    ll.addLast("two");
    ll.addLast("three");
    ll.addLast("four");
    ll.addLast("five");
    ll.addLast("six");
    ll.addLast("seven");

    assertEquals(false, ll.remove("eight"));
    assertEquals(7, ll.size());

    assertEquals(true, ll.remove("one"));
    assertEquals(6, ll.size());
    assertEquals("two", ll.getHead().getData());
    assertEquals("seven", ll.getHead().getPrev().getData());
    assertEquals(true, ll.remove("seven"));
    assertEquals(5, ll.size());
    assertEquals("two", ll.getHead().getData());
    assertEquals("six", ll.getHead().getPrev().getData());

    assertEquals("three", ll.getHead().getNext().getData());
    assertEquals("four", ll.getHead().getNext().getNext().getData());
    assertEquals("five", ll.getHead().getNext().getNext().getNext().getData());
    assertEquals("six", ll.getHead().getNext().getNext().getNext().getNext().getData());
    assertEquals("two", ll.getHead().getNext().getNext().getNext().getNext().getNext().getData());

    assertEquals(true, ll.remove("three"));
    assertEquals(4, ll.size());
    assertEquals("two", ll.getHead().getData());
    assertEquals("four", ll.getHead().getNext().getData());
    assertEquals("six", ll.getHead().getPrev().getData());
    assertEquals("five", ll.getHead().getPrev().getPrev().getData());
    assertEquals("four", ll.getHead().getPrev().getPrev().getPrev().getData());
    assertEquals("two", ll.getHead().getPrev().getPrev().getPrev().getPrev().getData());
  }

  @Test
  @DisplayName("Should remove the first element of the linked list")
  void testRemoveFirst() {
    linkedList.addLast(1);
    linkedList.addLast(2);
    linkedList.addLast(3);

    assertEquals(3, linkedList.size());

    assertEquals(1, linkedList.removeFirst());
    assertEquals(2, linkedList.size());
    assertEquals(2, linkedList.getHead().getData());
    assertEquals(3, linkedList.getHead().getNext().getData());
    assertEquals(2, linkedList.getHead().getNext().getNext().getData());
    assertEquals(3, linkedList.getHead().getPrev().getData());
    assertEquals(2, linkedList.getHead().getPrev().getPrev().getData());

    assertEquals(2, linkedList.removeFirst());
    assertEquals(1, linkedList.size());
    assertEquals(3, linkedList.getHead().getData());
    assertEquals(3, linkedList.getHead().getNext().getData());
    assertEquals(3, linkedList.getHead().getPrev().getData());

    assertEquals(3, linkedList.removeFirst());
    assertEquals(0, linkedList.size());
    assertEquals(null, linkedList.getHead());

    assertThrows(NoSuchElementException.class, () -> {
      linkedList.removeFirst();
    });
  }

  @Test
  @DisplayName("Should remove the last element of the linked list")
  void testRemoveLast() {
    linkedList.addLast(1);
    linkedList.addLast(2);
    linkedList.addLast(3);

    assertEquals(3, linkedList.size());

    assertEquals(3, linkedList.removeLast());
    assertEquals(2, linkedList.size());
    assertEquals(1, linkedList.getHead().getData());
    assertEquals(2, linkedList.getHead().getNext().getData());
    assertEquals(1, linkedList.getHead().getNext().getNext().getData());
    assertEquals(2, linkedList.getHead().getPrev().getData());
    assertEquals(1, linkedList.getHead().getPrev().getPrev().getData());

    assertEquals(2, linkedList.removeLast());
    assertEquals(1, linkedList.size());
    assertEquals(1, linkedList.getHead().getData());
    assertEquals(1, linkedList.getHead().getNext().getData());
    assertEquals(1, linkedList.getHead().getPrev().getData());

    assertEquals(1, linkedList.removeLast());
    assertEquals(0, linkedList.size());
    assertEquals(null, linkedList.getHead());

    assertThrows(NoSuchElementException.class, () -> {
      linkedList.removeLast();
    });
  }

  @Test
  @DisplayName("Should return the size of the linked list.")
  void testSize() {
    assertEquals(0, linkedList.size());

    linkedList.addLast(2);
    assertEquals(1, linkedList.size());

    linkedList.addLast(3);
    assertEquals(2, linkedList.size());

    linkedList.addFirst(1);
    assertEquals(3, linkedList.size());

    linkedList.removeLast();
    linkedList.removeFirst();
    assertEquals(1, linkedList.size());

    linkedList.removeLast();
    assertEquals(0, linkedList.size());
  }
}
