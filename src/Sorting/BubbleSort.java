package Sorting;

public class BubbleSort implements Sort {
    /**
     * Go through the array, and if an element and its subsequent element is out of order, swap them.
     * Repeat until no more swaps are made.
     * Also, optimize it so that the search space decreases: the last element is known to be sorted.
     */
    @Override
    public <T extends Comparable<T>> int sort(T[] array) {
        int totalSwaps = 0;

        for (int i = 0; i < array.length; i++) {
            boolean swapped = false;

            for (int j = 0; j < array.length - i - 1; j++) {
                if (array[j].compareTo(array[j + 1]) > 0) {
                    swapped = true;
                    totalSwaps += 1;

                    T temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }

            if (!swapped) {
                break;
            }
        }

        return totalSwaps;
    }
}
