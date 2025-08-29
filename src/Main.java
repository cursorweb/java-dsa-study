import BinaryTree.*;
import Parse.*;
import Sorting.*;

import java.util.Arrays;


public class Main {
    public static void main(String[] args) {
        parser();
    }

    private static void parser() {
        BaseParser parser = new PrattParser();
        parser.run();
    }

    private static void tree() {
        TreePrinter printer = new TreePrinter();
        BTree bTree = new TreeBalanced();

        Node node = new Node('5',
                new Node('6',
                        new Node('8',
                                new Node('9')
                        )
                ),
                new Node('7',
                        new Node('4'),
                        new Node('3')
                )
        );
        printer.run(node);
        bTree.run(node);

        node = new Node('9',
                new Node('3',
                        new Node('7'),
                        new Node('8')
                ),
                new Node('5',
                        new Node('6'),
                        new Node('4')
                )
        );
        printer.run(node);
        bTree.run(node);

        node = new Node('2',
                new Node('3',
                        new Node('4',
                                new Node('6')
                        ),
                        new Node('9')
                ),
                new Node('5')
        );
        printer.run(node);
        bTree.run(node);
    }

    private static void sort() {
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