public class Lab05 {

    // In this lab section I implemented array based Queue and Stack data structures.
    // Then I implemented those data structures with using SinglyLinkedList which is I implemented before sections
    // Then using this data structure types I did some string manipulations

    public static void main(String[] args) {
        String s= "5+10-4/2";
        int a = evaluate(s);
        System.out.println(a);

        String abc= "hello";
        System.out.println(reverse(abc));


    }

    /*
     * You first need to implement bellow classes
     */


    public static boolean isMatching(String str) {
        /*
         * Given a string containing parentheses and other characters,
         *  write a function that determines whether the parentheses
         *  in the string are balanced. A string is said to have balanced
         *  parentheses if every opening parenthesis ((, {, [) has a
         *  corresponding cloisng parenthesis (), }, ]), and the pairs
         *  are properly nested.
         */
        // Your code here..
        LinkedStack<Character> deneme = new LinkedStack();
        for (char ch : str .toCharArray()) {
            if (ch == '(' || ch == '{' || ch == '[') {
                deneme.push(ch);
            } else if (ch == ')' || ch == '}' || ch == ']') {
                if (deneme.isEmpty()) {
                    return false;
                }
                char open = deneme.pop();
                if ((ch == ')' && open != '(') ||
                        (ch == '}' && open != '{') ||
                        (ch == ']' && open != '[')) {
                    return false;
                }
            }
        }
        return deneme.isEmpty();
    }

    public static String reverse(String str) {
        /*
         * Write a function that reverses a given string using a stack.
         *  In this problem, you will leverage the Last-In-First-Out (LIFO)
         *  property of a stack to reverse the order of characters in a string.
         */
        // Your code here..

        LinkedStack<Character> deneme = new LinkedStack();
        for (char ch : str.toCharArray()) {
            deneme.push(ch);
        }
        StringBuilder reversed = new StringBuilder();
        while (!deneme.isEmpty()) {
            reversed.append(deneme.pop());
        }
        return reversed.toString();

    }

    public static boolean isHTMLMatching(String html) {
        /*
         * In HTML, every opening tag (like <div>) must have a corresponding closing
         *  tag (like </div>). Write a function to check whether all the HTML tags in
         *  a given string are properly matched. A properly matched HTML string has balanced
         *  and correctly nested tags.
         */
        // Your code here..


        LinkedStack<String> myStack = new LinkedStack();
        int i = 0;
        while (i < html.length()) {
            if (html.charAt(i) == '<') {
                int j = html.indexOf('>', i);
                if (j == -1){
                    return false;
                }
                String tag = html.substring(i + 1, j);
                if (!tag.startsWith("/")) {
                    myStack.push(tag);
                } else {
                    if (myStack.isEmpty()){
                        return false;
                    }
                    String openTag = myStack.pop();
                    if (!openTag.equals(tag.substring(1))) {
                        return false;
                    }
                }
                i = j;
            }
            i++;
        }
        return myStack.isEmpty();
    }

    public static int evaluate(String expression) {
        LinkedStack<Integer> values = new LinkedStack<>(); // Stack to store numbers
        LinkedStack<Character> operators = new LinkedStack<>(); // Stack to store operators

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            // Skip whitespaces
            if (c == ' ') {
                continue;
            }

            // If the character is a digit, extract the full number
            if (Character.isDigit(c)) {
                int num = 0;
                while (i < expression.length() && Character.isDigit(expression.charAt(i))) {
                    num = num * 10 + (expression.charAt(i) - '0');
                    i++;
                }
                values.push(num);
                i--; // Adjust index as the loop advances one extra character
            }

            // If the character is an opening parenthesis
            else if (c == '(') {
                operators.push(c);
            }

            // If the character is a closing parenthesis
            else if (c == ')') {
                while (!operators.isEmpty() && operators.top() != '(') {
                    values.push(applyOp(operators.pop(), values.pop(), values.pop()));
                }
                operators.pop(); // Remove the opening parenthesis
            }

            // If the character is an operator
            else if (isOperator(c)) {
                while (!operators.isEmpty() && precedence(c) <= precedence(operators.top())) {
                    values.push(applyOp(operators.pop(), values.pop(), values.pop()));
                }
                operators.push(c);
            }
        }

        // Apply remaining operators
        while (!operators.isEmpty()) {
            values.push(applyOp(operators.pop(), values.pop(), values.pop()));
        }

        return values.pop();
        /*
         * Write a function to evaluate a given arithmetic expression containing integers,
         *  parentheses, and operators. The function should correctly follow the order of
         *  operations (precedence) and handle parentheses appropriately.
         *  Your task is to complete the evaluate(String expression) method
         *  which returns the result of the expression.
         *
         * You may also need following methods or any other:
         * boolean isOperator(char c): returns true if given character is an operator
         * int precedance(char op): returns the precedance of the character
         * int applyOp(char op, int b, int a): applys the operator on operands
         */
        // Your code here..
    }
    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    // Returns the precedence of the operator
    private static int precedence(char op) {
        if (op == '+' || op == '-') {
            return 1; // Lowest precedence
        } else if (op == '*' || op == '/') {
            return 2; // Higher precedence
        }
        return 0;
    }

    // Applies the operator to the operands
    private static int applyOp(char op, int b, int a) {
        switch (op) {
            case '+': return a + b;
            case '-': return a - b;
            case '*': return a * b;
            case '/':
                if (b == 0) throw new ArithmeticException("Division by zero");
                return a / b;
        }
        return 0;
    }
}

interface List <E> {
    int size();
    boolean isEmpty();
}

interface IStack <E> extends List <E> {
    void push(E e);
    E top();
    E pop();
}

interface IQueue <E> extends List<E> {
    void enqueue(E e);
    E dequeue();
    E first();
}

class ArrayStack <E> implements IStack <E> {
    /*
     * Data Fields: necessary data fields
     * Constuctors: ArrayStack(): default capacity = 1000, ArrayStack(int capacity)
     * Methods: Required methods, toString
     */

    // Your code here...
    public static final int CAPACITY = 1000;
    private E[] data;
    private int t = -1;

    public ArrayStack() { this(CAPACITY); }
    public ArrayStack(int capacity) {
        data = (E[]) new Object[capacity];
    }

    public int size() { return (t + 1); }

    public boolean isEmpty() { return (t == -1); }

    public void push(E e) throws IllegalStateException {
        if (size() == data.length) throw new IllegalStateException("Stack is full");
        data[++t] = e;
    }

    public E top() {
        if (isEmpty()) return null;
        return data[t];
    }

    public E pop() {
        if (isEmpty()) return null;
        E answer = data[t];
        data[t] = null;
        t--;
        return answer;
    }


}

class Node <E> {
    /*
     * Data Fields: data, next
     * Constuctor: Node(data, next)
     * Methods: getData, getNext, setNext, toString
     */

    // Your code here..
    private E element;
    private Node<E> next;

    public Node(E e, Node<E> n) {
        element = e;
        next = n;
    }

    public E getData() {
        return element;
    }

    public Node<E> getNext() {
        return next;
    }

    public void setNext(Node<E> n) {
        next = n;
    }

}







class LinkedStack<E> implements IStack<E> {
    /*
     * Data Fields: necessary data fields
     * Constuctor: LinkedStack()
     * Methods: Required methods, toString
     */

    // Your code here...

    private SinglyLinkedList<E> list;

    public LinkedStack() {
        list = new SinglyLinkedList<>();
    }

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public void push(E element) {
        list.addLast(element);
    }

    public E top() {

        return list.last();
    }

    public E pop() {

        return list.removeLast();
    }
}












class ArrayQueue <E> implements IQueue <E> {
    /*
     * Data Fields: necessary data fields
     * Constuctors: ArrayQueue(): default capacity = 1000, ArrayStack(int capacity)
     * Methods: Required methods, toString
     */

    // Your code here..
    private E[] data;
    private int f = 0;
    private int sz = 0;
    final int CAPACITY = 1000;

    public ArrayQueue() {
        data = (E[]) new Object[CAPACITY];
    }
    public ArrayQueue(int capacity) {
        data = (E[]) new Object[capacity];
    }

    public int size() { return sz; }

    public boolean isEmpty() { return (sz == 0); }

    public void enqueue(E e) throws IllegalStateException {
        if (sz == data.length)
            throw new IllegalStateException("Queue is full");
        int avail = (f + sz) % data.length;
        data[avail] = e;
        sz++;
    }

    public E first() {
        if (isEmpty()) return null;
        return data[f];
    }

    public E dequeue() {
        if (isEmpty()) return null;
        E answer = data[f];
        data[f] = null;
        f = (f + 1) % data.length;
        sz--;
        return answer;
    }

}


class LinkedQueue <E> implements IQueue <E> {
    /*
     * Data Fields: necessary data fields
     * Constuctors: LinkedQueue()
     * Methods: Required methods, toString
     */

    // Your code here..
    private SinglyLinkedList<E> list;
    public LinkedQueue(){
        this.list=new SinglyLinkedList<>();
    }

    @Override
    public int size() {
        return this.list.size();
    }

    @Override
    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    @Override
    public void enqueue(E e) {
        list.addLast(e);
    }

    @Override
    public E first() {
        return list.first();
    }

    @Override
    public E dequeue() {
        return list.removeFirst();
    }
}













class SinglyLinkedList<E> {

    private Node<E> head;
    private Node<E> tail;
    private int size;

    public SinglyLinkedList() {
        head = null;
        tail = null;
        this.size = 0;
    }


    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public E first() {
        if (isEmpty()) return null;
        return head.getData();
    }

    public E last() {
        if (isEmpty()) return null;
        return tail.getData();
    }
    public void addLast(E e) {
        Node<E> newNode = new Node<>(e, null);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
            size++;
        } else {
            tail.setNext(newNode);
            tail = newNode;
            size++;
        }

    }
    public void addFirst(E e){
        Node<E> newNode = new Node<>(e, null);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
            size++;
        }
        else {
            newNode.setNext(head);
            head = newNode;
            size++;
        }

    }


    public E removeFirst() {
        if (isEmpty()) return null;
        E answer = head.getData();
        head = head.getNext();
        size--;
        return answer;
    }

    public E removeLast() {
        if (isEmpty()) return null;

        E answer = tail.getData(); // Store the value to be returned

        if (size == 1) { // Special case: only one element
            head = null;
            tail = null;
        } else {
            Node<E> current = head;
            while (current.getNext() != tail) { // Find the second-to-last node
                current = current.getNext();
            }
            current.setNext(null);
            tail = current; // Update tail to the new last node
        }

        size--;
        return answer;
    }


}
