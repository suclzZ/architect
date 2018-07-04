package com.basic.map;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

/**
 *
 *
 */
public interface  _Map<K,V> {

    V put(K k,V v);

    V get(K k);

    boolean remove(K k);

    boolean containtKey(K k);

    Set<K> keySet();

    Collection<V> values();


    interface _Entry<K,V>{

        K getKey();

        V getValue();

        V setValue(V value);

        boolean equal(Object o);

        int hashCode();

    }
}
