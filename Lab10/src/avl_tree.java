class AVLTree {

    // Node class
    static class Node {
        int key, height;
        Node left, right;

        Node(int key) {
            this.key = key;
            height = 1; // New node is initially added at leaf
        }
    }

    private Node root;

    // Utility function to get the height of the tree
    int height(Node node) {
        return node == null ? 0 : node.height;
    }

    // Utility function to get the balance factor of a node
    int getBalance(Node node) {
        return node == null ? 0 : height(node.left) - height(node.right);
    }

    // Right rotate subtree rooted with y
    Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        // Perform rotation
        x.right = y;
        y.left = T2;

        // Update heights
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        // Return new root
        return x;
    }

    // Left rotate subtree rooted with x
    Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        // Perform rotation
        y.left = x;
        x.right = T2;

        // Update heights
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        // Return new root
        return y;
    }

    // Insert a key into the AVL tree
    Node insert(Node node, int key) {
        // Perform the normal BST insertion
        if (node == null) {
            return new Node(key);
        }

        if (key < node.key) {
            node.left = insert(node.left, key);
        } else if (key > node.key) {
            node.right = insert(node.right, key);
        } else {
            // Duplicate keys are not allowed in BST
            return node;
        }

        // Update height of this ancestor node
        node.height = Math.max(height(node.left), height(node.right)) + 1;

        // Get the balance factor
        int balance = getBalance(node);

        // If the node becomes unbalanced, then there are 4 cases

        // Left Left Case
        if (balance > 1 && key < node.left.key) {
            return rightRotate(node);
        }

        // Right Right Case
        if (balance < -1 && key > node.right.key) {
            return leftRotate(node);
        }

        // Left Right Case
        if (balance > 1 && key > node.left.key) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right Left Case
        if (balance < -1 && key < node.right.key) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        // Return the (unchanged) node pointer
        return node;
    }

    // Preorder traversal of the tree
    void preOrder(Node node) {
        if (node != null) {
            System.out.print(node.key + " ");
            preOrder(node.left);
            preOrder(node.right);
        }
    }

    // Public method to insert a key
    public void insert(int key) {
        root = insert(root, key);
    }

    // Public method for preorder traversal
    public void preOrder() {
        preOrder(root);
    }

    public static void main(String[] args) {
        AVLTree tree = new AVLTree();

        // Insert elements
        tree.insert(10);
        tree.insert(20);
        tree.insert(30);
        tree.insert(40);
        tree.insert(50);
        tree.insert(25);

        // Preorder traversal
        System.out.println("Preorder traversal of the AVL tree is:");
        tree.preOrder();

        // Test case: Check balance of nodes
        System.out.println("\n\nTesting AVL tree properties:");

        // Helper method to test balance factor
        testTreeBalance(tree.root);
    }

    // Test tree balance factor recursively
    private static void testTreeBalance(Node node) {
        if (node != null) {
            int balance = getBalanceStatic(node);
            System.out.println("Node: " + node.key + ", Balance: " + balance);

            if (Math.abs(balance) > 1) {
                System.out.println("AVL Tree property violated at node " + node.key);
            }

            testTreeBalance(node.left);
            testTreeBalance(node.right);
        }
    }

    // Static utility to get balance for testing
    private static int getBalanceStatic(Node node) {
        return node == null ? 0 : heightStatic(node.left) - heightStatic(node.right);
    }

    // Static utility to get height for testing
    private static int heightStatic(Node node) {
        return node == null ? 0 : node.height;
    }
}
