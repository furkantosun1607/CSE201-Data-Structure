public class Lab02 {

    // in this lab section I implemented CircularLinkedList,DoublyLinkedList,SingularLinkedList Data Structures and Node class

    public static void main(String[] args) {

    }
}



interface Circlular {
    void rotate(); // tail becomes next of tail if not empty
}

interface Doubly <E> extends List <E> {
    E removeLast(); //removes the last element from the list
}

interface INode <E> { // storage unit
    E getElement(); // data/element
    Node<E> getPrev(); // returns the previous unit of this unit
    void setPrev(Node<E> prev); // sets the previous of this unit
    Node<E> getNext(); // returns the next unit of this unit
    void setNext(Node<E> next); // sets the next of this unit
}




interface List <E> {
    int size(); // Returns the size of the list
    boolean isEmpty(); // Checks whether the list is empty or not
    E first(); // returns the element/data of first entry in the list
    E last(); // returns te element/data of last entry in the list
    void addFirst(E e); // adds the given element at the start of the list
    void addLast(E e); // adds the given element at the end of the list
    E removeFirst(); // removes the first element in the list
}

/*
 * According to given above given interfaces, implement:
 *       1. A concrete Node class that implements INode generic interface
 *       2. A concrete SinglyLinkedList class that implements List generic interface
 *       3. A concrete CircularLinkedlist class that implements List generic interface and Circular interface
 *       4. A concrete DoublyLinkedList class that implements Doubly generic interface
 * For each of the concrete classes, use Node class as the storage unit
 * !! SinglyLinkedList class must have head and tail data fields
 * !! CircularLİnkedList class must have tail data field
 * !! Every class except Node must only have a single constructor without any parameters
 * You can implement any additional method you would like
 */







class Node<E> implements INode<E> {
    private E  e;
    private Node<E> preNode;
    private Node<E> nextNode;
    
    
    public Node (E e){
        this.e=e;

    }
    public void setElement(E e){
        this.e=e;
    }
    
    public E getElement(){
        return this.e;
    }
    
    
    public Node<E> getPrev(){
        return this.preNode;
    }
    
    public Node<E> getNext(){
        return this.nextNode;
    }
    public void setNext(Node<E> nextnode){
        this.nextNode=nextnode;
        
    }
    public void setPrev(Node<E> prevnode){
        this.preNode= prevnode;
    }
    
}


class SinglyLinkedList<E> implements List<E> {
    private Node<E> head;
    private Node<E> tail;
    private int size;
    
    public SinglyLinkedList(){
        this.size=0;
        this.head=null;
        this.tail=null;
    }
    public int size(){
        return this.size;
    }
    public boolean isEmpty(){
        return size==0;
    }
    public E first(){
        if (head !=null){
            return head.getElement();
        }
        return null;
    }
    public E last(){
        if(tail != null){
            return tail.getElement();
        }
        return null;
    }
    
     public void addFirst(E e) {
        Node<E> newNode = new Node<>(e);
        newNode.setElement(e);
        newNode.setNext(head);
        head = newNode;
        if (size == 0) tail = newNode;
        size++;
    }
 
    public void addLast(E e) {
        Node<E> newNode = new Node<>(e);
        newNode.setElement(e);
        newNode.setNext(null);
        if (size == 0) head = newNode;
        else tail.setNext(newNode);
        tail = newNode;
        size++;
    }
 
    public E removeFirst() {
        if (isEmpty()) return null;
        E temp = head.getElement();
        head = head.getNext();
        size--;
        if (size == 0) tail = null;
        return temp;
    }
    
}

class CircularLinkedList<E> implements List<E>,Circlular {
    private Node<E> head;
    private Node<E> tail;
    private int size;
    public CircularLinkedList(){
        this.size=0;
        this.head=null;
        this.tail=null;
    }
    public int size(){
        return this.size;
    }
    public boolean isEmpty(){
        return size==0;
    }

    @Override
    public E first() {
        return head.getElement();
    }

    @Override
    public E last() {
        return tail.getElement();
    }

    @Override
    public void addFirst(E data) {
        Node<E> node = new Node<>(data);
    if (size == 0){
        head = node;
        tail = node;
        tail.setNext(head);
        size++;}
    else {
    node.setNext(head);
    tail.setNext(node);
    this.head=node;
    size++;}
    }


    @Override
    public void addLast(E e) {
        Node<E> node = new Node<>(e);
        if (size == 0){
            head = node;
            tail = node;
            tail.setNext(head);
            head.setNext(tail);
            size++;}
         else{
        tail.setNext(node);
        tail=node;
        node.setNext(head);
         size++;}

    }

    @Override
    public E removeFirst() {
        if (isEmpty()) return null;
        Node<E> temp = head;
        tail.setNext(temp.getNext());
        head = temp.getNext();
        size--;
        return temp.getElement();
    }


    @Override
    public void rotate() {
    if (size != 0){

        head = head.getNext();
        tail= tail.getNext();
    }
    }
}
class DoublyLinkedList<E> implements Doubly<E> {
    private Node<E> head;
    private Node<E> tail;
    private int size;
    public DoublyLinkedList(){
        this.size=0;
        this.head=null;
        this.tail=null;
    }


    @Override
    public E removeLast() {
        if (isEmpty()) return null;
        else{
            Node<E> temp = tail.getPrev();
            E removed = tail.getElement();
            temp.setNext(null);
            tail = temp;
            size--;
            return removed;

        }

    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size==0;
    }

    @Override
    public E first() {
        return head.getElement();
    }

    @Override
    public E last() {
        return tail.getElement();
    }

    @Override
    public void addFirst(E e) {
        Node<E> newNode = new Node<>(e);
    if (size == 0){
        head = newNode;
        tail = newNode;
        head.setNext(tail);
        tail.setPrev(head);
        size++;
    }
    else {
        head.setPrev(newNode);
        newNode.setNext(head);
        head=newNode;
        size--;
    }
    }

    @Override
    public void addLast(E e) {
        Node<E> newNode = new Node<>(e);
        if (size == 0){
            head = newNode;
            tail = newNode;
            head.setNext(tail);
            tail.setPrev(head);
            size++;}
        else{
            tail.setNext(newNode);
            newNode.setPrev(tail);
            tail=newNode;
            size++;
        }
    }

    @Override
    public E removeFirst() {
        if (isEmpty()) return null;
        else {
            Node<E> temp = head;
            head = head.getNext();
            head.setPrev(null);
            size--;
            return temp.getElement();
        }
    }
}







 

 








