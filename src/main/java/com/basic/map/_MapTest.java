package com.basic.map;

/**
 *
 *
 */
public class _MapTest {

    public static void main(String[] args) {
        int hash = _HashMap.hash("abc");

        System.out.println( hash );

        System.out.println( 1000 & hash );

        //1111 1111
    }
}
