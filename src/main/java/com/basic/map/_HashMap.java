package com.basic.map;

import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;
import java.util.Set;

/**
 * HashMap
 *  DEFAULT_INITIAL_CAPACITY : table的长度 默认16
 *  DEFAULT_LOAD_FACTOR : 负载因子 空间换时间
 *  TREEIFY_THRESHOLD : table存储模式的临界值
 *  table : 默认长度为DEFAULT_INITIAL_CAPACITY,存储的是Entry链表（在Entry小于TREEIFY_THRESHOLD）时，根据key对应的hash计算均匀分布在0~15哪一位上
 *      table[0]  hash & （DEFAULT_INITIAL_CAPACITY-1） 相同
 *      table[1]
 *      table[...]
 *      table[15]
 *  entrySet : 所有元素的集合
 *
 *
 */
public class _HashMap<K,V> implements _Map<K,V>{
    private static final int DEFAULT_CAPACITY = 2<<3;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;//hash 存储
    private static final int TREEIFY_THRESHOLD = 8;
    _Node<K,V>[] table;
    Set<_Node> entrySet;
    int size;
    int capacity;

    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    public _HashMap(){

    }

    @Override
    public V put(K k, V v) {
        return putVal(hash(k),k,v);
    }

    private V putVal(int hash, K k, V v) {
        _Node[] tab;_Node<K,V> p;
        int n,i;
        if( (tab = table) == null || (n=tab.length)==0){//对n很玄妙的初始化
           n = (tab = resize()).length;
        }
        if((p = table[i=(n - 1) & hash]) == null){//n在初始化（resize）后为16 (n-1)&hash相当于 1111&.... 结果会在0~15间
            table[i] = newNode(hash,k,v,null);//设置node链表第一个值
        }else{//链表已有值 该值即为p
            _Node<K,V> e;
            if(p.hash==hash && Objects.equals(p.key,k)){//为什么还要判断hash，因为key现在值确定了落到0~15中一个，但要判断是否为同一个key
                //-比如key 0000 1111(15)、0001 1111(31) &15都是15 但两个key不同
                 e = p;
            }else{//为什么要遍历
                for(int x=0;;x++){//为什么不写判断条件
                    if ((e = p.next)==null){
                        p.next = newNode(hash,k,v,null);
                        if(x >= TREEIFY_THRESHOLD-1){//红黑树

                        }
                        break;
                    }
                    if(e.hash ==hash && Objects.equals(e.key,k)){//存在相同key 不做处理 e==null
                        break;
                    }
                    p = e;//第一个if不成立 e指向了下一个节点，继续处理
                }
            }
            if(e!=null){//第一个if 出现了同key、同hash的值,else 中e不可能为null
                V oldValue = e.value;
                e.value = v;
                return oldValue;
            }
        }
        if(++size> DEFAULT_INITIAL_CAPACITY*0.75){//扩容
            resize();
        }
        return null;
    }

    private _Node<K,V> newNode(int hash, K k, V v, _Node next) {
        return new _Node<K,V>(hash,k,v,next);
    }

    private _Node[] resize() {
        //初始化table
        if(table==null)
            table = new _Node[DEFAULT_INITIAL_CAPACITY];
        return table;
    }

    @Override
    public V get(K k) {
        _Node<K,V> e;
        return  (e = getNode(hash(k),k))==null?null:e.value;
    }

    private _Node<K,V> getNode(int hash, K k) {
        _Node node = table[(DEFAULT_INITIAL_CAPACITY-1) & hash];
        if(node!=null){
            if(node.hash==hash && Objects.equals(node.key,k)){
                return node;
            }
            _Node next;
            while ((next = node.next)!=null){
                 if(next.hash == hash && Objects.equals(next.key,k)){
                     return node;
                 }
             };
        }
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

    class _Node<K,V> implements  _Map._Entry<K,V>{
        K key;
        V value;
        int hash;
        _Node<K,V> next;

        _Node(int hashCode,K key,V value,_Node next){
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }

        @Override
        public boolean equal(Object o) {
            if(this == o){
                return true;
            }else if(o instanceof _Map._Entry){
                _Map._Entry entry = (_Entry) o;
                return Objects.equals(key,entry.getKey()) && Objects.equals(value,entry.getValue());
            }
            return false;
        }
    }
}
