import java.util.NoSuchElementException;

public class HW02_20220808025 {
    public static void main(String[] args) {


        MusicPlayer mp = new MusicPlayer("./Musics");
        mp.addSongToBeginning("Hotel California");
        mp.addSongToBeginning("Im Still Standing");
        mp.addSongToBeginning("Judas");
        mp.addSongToBeginning("Bohemian Rhapsody");
    }
}

interface INode <T> { // storage unit
    // Constructor (T data, Node<T> prev, Node<T> next)
    T getData(); // returns the data
    Node<T> getNext(); // returns the next of this storage unit
    Node<T> getPrev(); // returns the previous storage unit of this unit
    void setNext(Node<T> next); // sets next pointer of this node
    void setPrev(Node<T> prev); // sets the prev pointer of this node
    String toString(); // string representation
}

interface IDoublyCircularLinkedList <T> {
    // must have the data field current
    // Constructor ()
    void addFirst(T data); // adds an element to the head of the list. If first element in list, must also be last element
    // if only element in the list its next and prev should point to itself
    void addLast(T data); // adds an element to the tail of the list. If first element in list, must also be last element
    // if only element in the list its next and prev should point to itself
    T removeFirst() throws NoSuchElementException; // removes the first element in the list,
    // throw exception if list is empty, if only element remaining it should be first and last and its next and prev,
    // should be itself
    T removeLast() throws NoSuchElementException; // removes the last element in the list,
    // throw exception if list is empty, if only element remaining it should be first and last and its next and prev,
    // should be itself
    T get(int index) throws IndexOutOfBoundsException; // gets the ith element in the list,
    // should throw exception if out of bounds
    T first() throws NoSuchElementException; // should set current, returns the first data
    T last() throws NoSuchElementException; // should set current, returns the last data
    boolean remove(T data); // should return false if data doesnt exists, returns true and removes if exists
    boolean isEmpty();
    int size();
    T next() throws NoSuchElementException; // if empty, throws exception, should change current correctly
    // if current is null should return head and set it to head
    T previous() throws NoSuchElementException; // if empty, throws exception, should change current correctly
    // if current is null should return tail data and set it
    T getCurrent() throws NoSuchElementException; // Retruns the current pointer, if no element exits throws exception
    // if current is null returns heads data
    Node<T> getHead(); // returns the head of the list, if is empty returns null
    // any other method needed

}

class Node<T> implements INode<T> {
    private T data;
    private Node<T> prev;
    private Node<T> next;


   public Node(T data, Node<T> prev, Node<T> next){
       this.data = data;
       this.prev = prev;
       this.next = next;
    }
    public T getData() {
       return data;
    }

    @Override
    public Node<T> getNext() {
        return next;
    }

    @Override
    public Node<T> getPrev() {
        return prev;
    }

    @Override
    public void setNext(Node<T> next) {
        this.next = next;
    }

    @Override
    public void setPrev(Node<T> prev) {
        this.prev = prev;
    }

    @Override
    public String toString() {
        return data.toString();
    }

}



class DoublyCircularLinkedList<T> implements IDoublyCircularLinkedList<T> {
    private Node<T> head;
    private Node<T> current;
    private int size;

    public DoublyCircularLinkedList() {
        head = null;
        current = null;
        size = 0;
    }

    @Override
    public void addFirst(T data) {
        Node<T> newNode = new Node<>(data, null, null);
        if (isEmpty()) {
            head = newNode;
            head.setNext(head);
            head.setPrev(head);
        } else {
            Node<T> tail = head.getPrev();
            newNode.setNext(head);
            newNode.setPrev(tail);
            head.setPrev(newNode);
            tail.setNext(newNode);
            head = newNode;
        }
        size++;
    }

    @Override
    public void addLast(T data) {
        Node<T> newNode = new Node<>(data, null, null);
        if (isEmpty()) {
            head = newNode;
            head.setNext(head);
            head.setPrev(head);
        } else {
            Node<T> tail = head.getPrev();
            newNode.setNext(head);
            newNode.setPrev(tail);
            tail.setNext(newNode);
            head.setPrev(newNode);
        }
        size++;
    }

    @Override
    public T removeFirst() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty.");
        }
        Node<T> removedNode = head;
        if (size == 1) {
            head = null;
        } else {
            Node<T> tail = head.getPrev();
            head = head.getNext();
            head.setPrev(tail);
            tail.setNext(head);
        }
        size--;
        return removedNode.getData();
    }

    @Override
    public T removeLast() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty.");
        }
        Node<T> tail = head.getPrev();
        if (size == 1) {
            head = null;
        } else {
            Node<T> newTail = tail.getPrev();
            head.setPrev(newTail);
            newTail.setNext(head);
        }
        size--;
        return tail.getData();
    }

    @Override
    public T get(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds.");
        }
        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.getNext();
        }
        return currentNode.getData();
    }

    @Override
    public T first() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty.");
        }
        current = head;
        return head.getData();
    }

    @Override
    public T last() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty.");
        }
        current = head.getPrev();
        return current.getData();
    }

    @Override
    public boolean remove(T data) {
        if (isEmpty()) {
            return false;
        }
        Node<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            if (currentNode.getData().equals(data)) {
                if (size == 1) {
                    head = null;
                } else {
                    currentNode.getPrev().setNext(currentNode.getNext());
                    currentNode.getNext().setPrev(currentNode.getPrev());
                    if (currentNode == head) {
                        head = currentNode.getNext();
                    }
                }
                size--;
                return true;
            }
            currentNode = currentNode.getNext();
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T next() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty.");
        }
        if (current == null) {
            current = head;
        } else {
            current = current.getNext();
        }
        return current.getData();
    }

    @Override
    public T previous() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty.");
        }
        if (current == null) {
            current = head.getPrev();
        } else {
            current = current.getPrev();
        }
        return current.getData();
    }

    @Override
    public T getCurrent() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty.");
        }
        if (current == null) {
            return head.getData();
        }
        return current.getData();
    }

    @Override
    public Node<T> getHead() {
        return head;
    }
}
