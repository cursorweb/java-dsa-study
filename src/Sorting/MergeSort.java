package Sorting;

import java.util.ArrayList;
import java.util.List;

public class MergeSort implements Sort {
    private int splits = 0;

    /**
     * Divide array by 2, until it's array of size 1 (it's sorted). Then merge them
     */
    @Override
    public <T extends Comparable<T>> int sort(T[] array) {
        splits = 0;

        List<T> list = new ArrayList<>(List.of(array));
        list = mergeSort(list);

        for (int i = 0; i < array.length; i++) {
            array[i] = list.get(i);
        }

        return splits;
    }

    private <T extends Comparable<T>> List<T> mergeSort(List<T> array) {
        if (array.size() <= 1) {
            return array;
        }

        List<T> left = mergeSort(array.subList(0, array.size() / 2));
        List<T> right = mergeSort(array.subList(array.size() / 2, array.size()));

        splits++;

        List<T> out = new ArrayList<>();

        int l, r;
        for (l = 0, r = 0; l < left.size() && r < right.size(); ) {
            if (left.get(l).compareTo(right.get(r)) <= 0) {
                out.add(left.get(l));
                l++;
            } else {
                out.add(right.get(r));
                r++;
            }
        }

        while (l < left.size()) {
            out.add(left.get(l));
            l++;
        }

        while (r < right.size()) {
            out.add(right.get(r));
            r++;
        }

        return out;
    }
}
