package com.basic.queue;

import java.util.*;

/**
 *
 * fifo
 * 感觉队列和数组很像，只是没有提供访问元素的方法，
 * 只能通过添加/删除操作
 *
 * 　add        增加一个元索                     如果队列已满，则抛出一个IllegalStateException异常
 　　remove   移除并返回队列头部的元素    如果队列为空，则抛出一个NoSuchElementException异常
 　　element  返回队列头部的元素             如果队列为空，则抛出一个NoSuchElementException异常
 　　offer       添加一个元素并返回true       如果队列已满，则返回false
 　　poll         移除并返问队列头部的元素    如果队列为空，则返回null
 　　peek       返回队列头部的元素             如果队列为空，则返回null
 　　put         添加一个元素                      如果队列满，则阻塞
 　　take        移除并返回队列头部的元素     如果队列为空，则阻塞
 *
 */
public class ArrayQueue<E> implements Queue<E>{
    private Object [] arr;
    private int size;
    private int position;

    public ArrayQueue(int size){
        this.size = size;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return arr==null||arr.length==0;
    }

    @Override
    public boolean contains(Object o) {//队列能否有null
        if(arr==null){
            return false;
        }else{
            for(Object obj : arr){
                if(Objects.equals(obj,o)){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return arr;
    }

    @Override
    public <T> T[] toArray(T[] a) {

        return null;
    }

    @Override
    public boolean add(E e) {
        if(position==size-1){
            throw new IllegalStateException("Queue full");
        }
        if(e!=null){
            arr[position++] = e;
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(Object o) {
        if(arr==null)
            throw new NoSuchElementException("Queue is null");
        System.arraycopy(arr,1,arr,0,arr.length-1);
        arr[position] = null;
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {
        arr = null;
    }

    @Override
    public boolean offer(E e) {
        if(position < size-1 && e!=null){
            arr[position++] = e;
            return true;
        }
        return false;
    }

    @Override
    public E remove() {
        if(arr==null || arr.length==0)
            throw new NoSuchElementException("Queue is null");
        Object firstEle = arr[0];
        System.arraycopy(arr,1,arr,0,arr.length-1);
        arr[position] = null;
        return (E)firstEle;
    }

    @Override
    public E poll() {
        if(arr==null || arr.length==0){
            return null;
        }
        Object firstEle = arr[0];
        System.arraycopy(arr,1,arr,0,arr.length-1);
        arr[position] = null;
        return (E)firstEle;
    }

    @Override
    public E element() {
        return null;
    }

    @Override
    public E peek() {
        if(arr!=null && arr.length>0){
            return (E)arr[0];
        }
        return null;
    }
}
