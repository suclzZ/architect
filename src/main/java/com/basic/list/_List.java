package com.basic.list;

/**
 *
 *
 */
public interface _List<E> {

    E add(E e);

    boolean remove(E e);

    E get(int index);

    int size();

    boolean containts(E e);

    String toString();

}
