package BinaryTree;

public abstract class BTree {
    protected Node root;

    public BTree() {
        this.root = null;
    }

    public void load(Node node) {
        root = node;
    }

    public abstract void run();
}
