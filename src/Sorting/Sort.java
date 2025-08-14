package Sorting;

public interface Sort {
    /**
     * Return the number of swaps
     */
    <T extends Comparable<T>> int sort(T[] array);
}
