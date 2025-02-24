package org.example.chapter4;

import java.util.Comparator;

public class DefaultComparator<T> implements Comparator<T> {
    public static <T> DefaultComparator<T> getInstance() { return new DefaultComparator<>(); }
    @Override
    @SuppressWarnings("unchecked")
    public int compare(T first, T second) throws ClassCastException {
        return (( Comparable<T> )first).compareTo(second);
    }
}
