import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Lab07 {
    /* In this lab section, I successfully implemented a BinaryTree structure, along with comprehensive traversal methods,
 including Breadth-First Search (BFS), Depth-First Search (DFS), Breadth-First Traversal (BFT), and Depth-First Traversal (DFT).
*/



    /*
     * contains method's main implementation of the method will be in
     * DFS and BFS classes. These classes just have 2 methods each, which will be the actual
     * Depth-first search and Breadth-first search implementations.
     * hint: you can use get methods in contains methods
     */

    public static void main(String[] args) {


    }


}

interface Search <T> {
    boolean contains(BinaryTreeNode<T> root, T value);
    BinaryTreeNode<T> get(BinaryTreeNode<T> root, T value);
}

/*
 * Binary Tree interface
 */
interface IBinaryTree <T> {
    void insert(T value);
    boolean contains(T value);
    boolean delete(T value);
    int height();
    boolean isEmpty();
    int size();
    void traverse(int order);
}


class BinaryTreeNode <T> {
    /*
     * no parent reference
     */

    private T data;
    private BinaryTreeNode<T> left;
    private BinaryTreeNode<T> right;


    public BinaryTreeNode(T value) {
        this.data = value;
        this.left = null;
        this.right = null;
    }

    @Override
    public String toString() {
        return "Node(" + data + ")";
    }
    public BinaryTreeNode<T> getLeft() {
        return left;
    }
    public T getData() {return data;}
    public BinaryTreeNode<T> getRight() {
        return right;
    }
    public void setLeft(BinaryTreeNode<T> left) {
        this.left = left;
    }
    public void setRight(BinaryTreeNode<T> right) {
        this.right = right;
    }
    public void setData(T data) {this.data = data;}
}

/*
 * This is not a BST! order in which elements are stored doesn't matter
 * !!Needs to satisfy complete binary tree properties at all times!!
 */

/*
 * Necessary data fields:
 *  Node root, int size, Search traversalStrategy (only going to use in contains)
 */

/*
 * Constructor(Search)
 */
class BinaryTree<T>  implements IBinaryTree<T>{
    private BinaryTreeNode<T> root;
    private  int size;
    private Search<T> travelStrategy;

    public BinaryTree() {
        this.root = null;
        int size=0;
        this.travelStrategy=null;
    }
    public void setTravelStrategy(Search<T> travelStrategy) {
        this.travelStrategy = travelStrategy;
    }
    public int getSize() {
        return size;
    }
    public BinaryTreeNode<T> getRoot() {
        return root;
    }


    public void insertBST(T value) {
        BinaryTreeNode<T> node = new BinaryTreeNode<>(value);
        if (root == null) {
            this.root=node;
        }
        else{
            BinaryTreeNode<T> current = root;
            while(current.getLeft() !=null || current.getRight() !=null){
                if((Integer)value>(Integer) current.getData()){
                    current = current.getRight();
                }else{
                    current = current.getLeft();
                }
            }
            if(current.getLeft() == null){
                current.setLeft(node);
            }
            else if(current.getRight() == null){
                current.setRight(node);
            }
        }
        size++;
    }
    public void insert(T value) {
        BinaryTreeNode<T> node = new BinaryTreeNode<>(value);
        if (root == null) { size++;
            root = node; return;
        }

        Queue<BinaryTreeNode<T>> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            BinaryTreeNode<T> current = queue.poll();

            if (current.getLeft() == null) {
                current.setLeft(node);
                break;
            } else {
                queue.add(current.getLeft());
            }

            if (current.getRight() == null) {
                current.setRight(node);
                break;
            } else {
                queue.add(current.getRight());
            }
        }
        size++;
    }

    public boolean containsBST(T value) {
        if (root == null) {
            return false;
        } else {
            T currentValue = root.getData();
            while (currentValue != null) {
                if (currentValue.equals(value)) {
                    return true;
                }
                if ((int) currentValue > (int) value) {
                    currentValue = root.getLeft().getData();
                    root = root.getLeft();
                } else {
                    currentValue = root.getRight().getData();
                    root = root.getRight();
                }
            }
            return false;
        }
    }

    public  boolean contains(T value) {
        this.travelStrategy = new BFS<>();
        return travelStrategy.contains(this.root, value);
    }




    /*
     * public boolean delete(T value)
     *      ^^ find taret node (node with value), find last node (target) in BFS manner
     *      ^^ (Optional) can use strategy.get(T value) for target,
     *      ^^ only if strategy is from BFS class, if DFS would be incorrect
     * private boolean deleteLastNode(Node<T> root, Node<T> target) // recursive DFS implementation
     * private int heightRec(Node<T> node) // Recursive helper implemenation
     */

    @Override
    public boolean delete(T value) {
        if (root == null || value == null) {
            return false;
        }


        this.travelStrategy = new BFS<>();
        BinaryTreeNode<T> target = travelStrategy.get(this.root, value);

        if (target == null) {
            return false;
        }


        BinaryTreeNode<T> lastNode = null;
        BinaryTreeNode<T> lastNodeParent = null;
        Queue<BinaryTreeNode<T>> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            BinaryTreeNode<T> current = queue.poll();

            if (current.getLeft() != null) {
                queue.add(current.getLeft());
                lastNodeParent = current;
            }
            if (current.getRight() != null) {
                queue.add(current.getRight());
                lastNodeParent = current;
            }
            lastNode = current;
        }


        if (lastNode == null) {
            return false;
        }


        target.setData(lastNode.getData());


        boolean isDeleted = deleteLastNode(lastNodeParent, lastNode);

        if (isDeleted) {
            size--;
        }
        return isDeleted;
    }

    private boolean deleteLastNode(BinaryTreeNode<T> parent, BinaryTreeNode<T> target) {
        if (parent == null) {
            return false;
        }

        // Eğer sağ alt çocuk son düğümse, onu sil
        if (parent.getRight() != null && parent.getRight().equals(target)) {
            parent.setRight(null);
            return true;
        }

        // Eğer sol alt çocuk son düğümse, onu sil
        if (parent.getLeft() != null && parent.getLeft().equals(target)) {
            parent.setLeft(null);
            return true;
        }

        return false;
    }

    public int height() {
        return heightRec(root);
    }

    private int heightRec(BinaryTreeNode<T> node) { // bir tabanlı yükseklik
        if (node == null) {
            return 0;
        }
        int leftHeight = heightRec(node.getLeft());
        int rightHeight = heightRec(node.getRight());
        return Math.max(leftHeight, rightHeight) + 1;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return this.size;
    }


    /*
     * All required methods, additionally below ones
     * private void inorder(Node<T> node) // recursive DFS inorder traversal
     * private void postorder(Node<T> node) // recursive DFS postorder traversal
     * private void preorder(Node<T> node) // recursive DFS preorder traversal
     * private void levelorder(Node<T> node) // iterative BFS levelorder traversal
     * print out elements, order matters
     */

    /*
     * public void traverse(int order)
     * Simply, use switch-case to determine traversal technique [1, 2, or 3], by default level-order
     * 1: preorder
     * 2: inorder
     * 3: postorder
     * default: level-order
     */

    /*
     * ===================== toString() ==========================
     * to see the tree, first implement height method
     */

    @Override
    public void traverse(int order) {
        switch (order) {
            case 1:
                // Preorder Traversal (Left, Root, Right)
                preorder(root);
                break;
            case 2:
                // Inorder Traversal (Root, Left, Right)
                inorder(root);
                break;
            case 3:
                // Postorder Traversal (Left, Right, Root)
                postorder(root);
                break;
            default:
                levelorder(root);
                break;
        }
    }

    // Recursive Inorder Traversal Left-rooT-right
    private void inorder(BinaryTreeNode<T> node) {
        if (node != null) {
            inorder(node.getLeft());
            System.out.print(node.getData() + " ");
            inorder(node.getRight());
        }
    }

    // Recursive Preorder Traversal Root-Left-Right
    private void preorder(BinaryTreeNode<T> node) {
        if (node != null) {
            System.out.print(node.getData() + " ");
            preorder(node.getLeft());
            preorder(node.getRight());
        }
    }

    // Recursive Postorder Traversal Left-Right-Root
    private void postorder(BinaryTreeNode<T> node) {
        if (node != null) {
            postorder(node.getLeft());
            postorder(node.getRight());
            System.out.print(node.getData() + " ");
        }
    }

    // Iterative Level Order Traversal
    private void levelorder(BinaryTreeNode<T> root) {
        if (root == null) return;

        Queue<BinaryTreeNode<T>> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            BinaryTreeNode<T> current = queue.poll();
            System.out.print(current.getData() + " ");

            if (current.getLeft() != null) queue.add(current.getLeft());
            if (current.getRight() != null) queue.add(current.getRight());
        }
    }







}
class BinarySearch<Integer>  {


    public boolean contains(BinaryTreeNode<Integer> root, Integer value) {
//        if (root == null) {
//            return false;
//        }else {
//            Integer currentValue = root.getData();
//            while (currentValue != null ) {
//                if (currentValue==value) {
//                    return true;
//                }
//                if ((int) currentValue>(int)value) {
//                    currentValue=root.getLeft().getData();
//                    root=root.getLeft();
//                }else{
//                    currentValue=root.getRight().getData();
//                    root=root.getRight();
//                }
//            }
//            return false;
        return get(root, value)!=null;
    }






    public BinaryTreeNode<Integer> get(BinaryTreeNode<Integer> root, Integer value) {
        if (root == null) {
            return null;
        }else {
            Integer currentValue = root.getData();
            while (currentValue != null ) {
                if (currentValue.equals(value)) {
                    return root;
                }
                if ((java.lang.Integer) currentValue>(java.lang.Integer) value) {
                    currentValue=root.getLeft().getData();
                    root=root.getLeft();
                }else{
                    currentValue=root.getRight().getData();
                    root=root.getRight();
                }
            }
            return null;
        }
    }



}



/*
 * concrete BFS generic class which implements Search interface
 */

/*
 * concrete DFS generic class which implements Search interface
 * ITERATIVE DFS implementation!
 */



class BFS<T> implements Search <T> {


    @Override
    public boolean contains(BinaryTreeNode<T> root, T value) {
        return get(root, value)!=null;
    }

    @Override
    public BinaryTreeNode<T> get(BinaryTreeNode<T> root, T value) {

        if (root == null) {
            return null;
        }

        Queue<BinaryTreeNode<T>> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            BinaryTreeNode<T> currentNode = queue.poll();

            if (currentNode.getData().equals(value)) {
                return currentNode;
            }


            if (currentNode.getLeft() != null) {
                queue.add(currentNode.getLeft());
            }
            if (currentNode.getRight() != null) {
                queue.add(currentNode.getRight());
            }
        }
        return null;
    }

}




class DFS<T> implements Search<T> {


    @Override
    public boolean contains(BinaryTreeNode<T> root, T value) {
        return get(root, value)!=null;
    }

    @Override
    public BinaryTreeNode<T> get(BinaryTreeNode<T> root, T value) {
        if (root == null) {
            return null;
        }


        Stack<BinaryTreeNode<T>> stack = new Stack<>();
        stack.add(root);

        while (!stack.isEmpty()) {
            BinaryTreeNode<T> currentNode = stack.pop();

            if (currentNode.getData().equals(value)) {
                return currentNode;
            }


            if (currentNode.getLeft() != null) {
                stack.add(currentNode.getLeft());
            }
            if (currentNode.getRight() != null) {
                stack.add(currentNode.getRight());
            }
        }
        return null;
    }

}













