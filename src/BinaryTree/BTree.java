package BinaryTree;

public abstract class BTree {
    protected Node root;

    public BTree() {
        this.root = null;
    }

    public void run(Node node) {
        root = node;
        run();
    }

    public abstract void run();
}
