package app;

import java.util.*;

public class IntegerComparer implements Comparator<Integer> {
    public int compare(Integer a, Integer b) {
        return b - a;
    }
}