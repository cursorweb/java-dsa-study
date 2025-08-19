package BinaryTree;

public class TreeHeight extends BTree {
    @Override
    public void run() {
        System.out.println(height(root));
    }

    private int height(Node node) {
        if (node == null) {
            return -1;
        }

        int leftHeight = 1 + height(node.left);
        int rightHeight = 1 + height(node.right);
        return Math.max(leftHeight, rightHeight);
    }
}
