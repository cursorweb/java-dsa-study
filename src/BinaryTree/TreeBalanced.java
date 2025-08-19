package BinaryTree;

public class TreeBalanced extends BTree {
    /*
    Tree is balanced if height of the two subtrees is never more than one.
    (Height of empty is 0)
    */
    @Override
    public void run() {
        System.out.println(balanced(root));
    }

    private boolean balanced(Node node) {
        if (node == null) {
            return true;
        }

        boolean leftBalanced = balanced(node.left);
        boolean rightBalanced = balanced(node.right);

        if (!leftBalanced || !rightBalanced) {
            return false;
        }

        int leftHeight = height(node.left);
        int rightHeight = height(node.right);

        return Math.abs(leftHeight - rightHeight) <= 1;
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
