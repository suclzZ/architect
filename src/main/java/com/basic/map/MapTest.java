package com.basic.map;

import java.util.Map;
import java.util.HashMap;

/**
 *
 *
 */
public class MapTest {

    public static void main(String[] args) {
        Map<String,String> map = new HashMap<String,String>();
        map.put("a","1");
        map.put("b","2");
        map.put("c","3");
        map.put("d","4");
        map.put("e","5");

        System.out.println(2^4);// 0010 0100 0110

        for(Map.Entry entry:map.entrySet()){
            entry.getKey();
            entry.getValue();
        }
    }
}
