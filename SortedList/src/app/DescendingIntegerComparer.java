package app;

import java.util.Comparator;

public class DescendingIntegerComparer implements Comparator<Integer> {
    public int compare(Integer a, Integer b) {
        return a - b;
    }
}