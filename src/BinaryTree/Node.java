package BinaryTree;

public final class Node {
    public final Node left;
    public final Node right;
    public final char val;


    public Node(char val, Node left, Node right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    public Node(char val, Node left) {
        this.val = val;
        this.left = left;
        this.right = null;
    }

    public Node(char val) {
        this.val = val;
        this.left = null;
        this.right = null;
    }

    @Override
    public String toString() {
        return "Node(" + this.val + ")";
    }
}
