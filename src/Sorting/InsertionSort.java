package Sorting;

public class InsertionSort implements Sort {
    /**
     * Have the sorted part start at the beginning of the array and grow to the right.
     * Each step take the left-most value from the unsorted part of the array and move it leftward,
     * swapping elements until it is in the right place
     */
    @Override
    public <T extends Comparable<T>> int sort(T[] array) {
        int swaps = 0;

        for (int i = 1; i < array.length; i++) {
            for (int j = i; j > 0; j--) {
                if (array[j].compareTo(array[j - 1]) < 0) {
                    T temp = array[j];
                    array[j] = array[j - 1];
                    array[j - 1] = temp;
                    swaps++;
                } else {
                    break;
                }
            }
        }

        return swaps;
    }
}
