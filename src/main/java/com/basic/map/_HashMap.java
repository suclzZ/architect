package com.basic.map;

import java.util.Collection;
import java.util.Set;

/**
 *
 *
 */
public class _HashMap<K,V> implements _Map<K,V>{
    private static final int DEFAULT_CAPACITY = 2<<3;

    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    public _HashMap(){

    }

    @Override
    public V put(K k, V v) {
        return null;
    }

    @Override
    public V get(K k) {
        return null;
    }

    @Override
    public boolean remove(K k) {
        return false;
    }

    @Override
    public boolean containtKey(K k) {
        return false;
    }

    @Override
    public Set<K> keySet() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }
}
