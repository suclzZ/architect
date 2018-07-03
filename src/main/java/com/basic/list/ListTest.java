package com.basic.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 *
 */
public class ListTest {

    public static void main(String[] args) {

        List<String> list = new ArrayList<String>();
        list.add("a");
        list.add("b");
        list.add(null);
        list.add("c");
        list.add("a");

        System.out.println(list );
        System.out.println(list.size() );

        list.remove("a");

        System.out.println(list );
        System.out.println(list.size() );
    }
}
