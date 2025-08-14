import BinaryTree.BTree;
import BinaryTree.Node;
import BinaryTree.TreePrinter;
import Sorting.QuickSort;
import Sorting.Sort;

import java.util.Arrays;


public class Main {
    public static void main(String[] args) {
        tree();
    }

    public static void tree() {
        BTree treePrinter = new TreePrinter();

        Node node = new Node('5', new Node('6', new Node('8'), new Node('9')), new Node('7'));
        treePrinter.load(node);
        treePrinter.run();
    }

    public static void sort() {
        Sort sort = new QuickSort();

        Integer[] array = {5, 9, 3, 4, 6, 8, 2};
        int swaps = sort.sort(array);
        System.out.println(Arrays.toString(array));
        System.out.println(swaps);

        Double[] array2 = {0.99853107, 0.98862465, 0.28976821, 0.8093185, 0.68910705,
                0.89809701, 0.68403988, 0.44924104, 0.95817259, 0.01286121};
        swaps = sort.sort(array2);
        System.out.println(Arrays.toString(array2));
        System.out.println(swaps);
    }
}