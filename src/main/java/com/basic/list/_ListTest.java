package com.basic.list;

import java.util.Arrays;

/**
 *
 *
 */
public class _ListTest {

    public static void main(String[] args) {

        String [] elementData = {};
        elementData = Arrays.copyOf(elementData, 10);


        _List<String> _list = new _ArrayList<String>();
        _list.add("a");
        _list.add("b");
        _list.add("c");
        _list.add("a");
        _list.add(null);
        _list.add("e");

        System.out.println("list: "+_list);

        System.out.println("index 1:  "+_list.get(1));

        System.out.println( "size: "+_list.size());

        _list.remove("b");

        System.out.println("list: "+_list);

        _list.remove("a");

        System.out.println("list: "+_list);

        _list.remove(null);

        System.out.println("list: "+_list);

        System.out.println("has c: "+_list.containts("c"));
    }
}
