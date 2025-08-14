package Sorting;

public class QuickSort implements Sort {
    private int swaps = 0;

    /**
     * Choose the last element of the list as the pivot. Then, split the array into two sides:
     * one that is less than pivot, and one that is greater than pivot.
     * So you have (`|` is the pivot pos):
     * ```
     * | 5 2 9 3 4
     * 2 | 5 9 3 4
     * 2 3 | 5 9 4
     * (swap the pivot)
     * 2 3 [4] 9 5
     * ```
     * With the two partitions, quick sort those.
     */
    @Override
    public <T extends Comparable<T>> int sort(T[] array) {
        swaps = 0;

        qs(array, 0, array.length - 1);

        return swaps;
    }

    private <T extends Comparable<T>> void qs(T[] array, int lo, int hi) {
        if (lo >= hi) {
            return;
        }

        int idx = partition(array, lo, hi);
        qs(array, lo, idx - 1);
        qs(array, idx + 1, hi);
    }

    private <T extends Comparable<T>> int partition(T[] array, int lo, int hi) {
        T pivot = array[hi];
        int pivotPos = lo;

        for (int i = lo; i < hi; i++) {
            if (array[i].compareTo(pivot) < 0) {
                T temp = array[i];
                array[i] = array[pivotPos];
                array[pivotPos] = temp;
                pivotPos++;
                swaps++;
            }
        }

        if (hi != pivotPos) {
            T temp = array[pivotPos];
            array[pivotPos] = array[hi];
            array[hi] = temp;
            swaps++;
        }

        return pivotPos;
    }
}
