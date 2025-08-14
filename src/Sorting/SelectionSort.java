package Sorting;

public class SelectionSort implements Sort {
    /**
     * Start at the first element. Go through the rest of the array, and find the smallest element.
     * Then, swap the value with the first element. Then go to second element ...
     */
    @Override
    public <T extends Comparable<T>> int sort(T[] array) {
        int swaps = 0;
        for (int i = 0; i < array.length; i++) {
            T smallestVal = array[i];
            int smallestIdx = i;
            for (int j = i + 1; j < array.length; j++) {
                if (array[j].compareTo(smallestVal) < 0) {
                    smallestVal = array[j];
                    smallestIdx = j;
                }
            }

            if (i != smallestIdx) {
                T temp = array[i];
                array[i] = array[smallestIdx];
                array[smallestIdx] = temp;
                swaps++;
            }
        }

        return swaps;
    }
}
