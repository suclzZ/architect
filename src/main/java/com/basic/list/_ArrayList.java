package com.basic.list;

import java.util.Arrays;

/**
 * ArrayList模型 [a,b,c,null,null]
 * 一般都有一个固定大小，只不过大于size的部分都是null
 *
 */
public class _ArrayList<E> implements _List<E>{
    private Object[] arr = {};
    private int capacity;
    private static int defaultCapacity = 10;
    private int size;

    public _ArrayList(){
        this.capacity = defaultCapacity;
    }

    public _ArrayList(int capacity){
        this.capacity = capacity;
    }

    public void dilatation(int newSize){//扩容规则
        if(newSize<capacity){
            arr = Arrays.copyOf(arr,capacity);
        }else if(newSize <Integer.MAX_VALUE){

        }
    }

    public E add(E e) {
        dilatation(size+1);
        arr[size++] = e;
        return e;
    }

    public boolean remove(E e) {
        boolean hasE = false;
        Object[] remanentEle ;
        for(int i=0; i<arr.length; i++){
            if((e!=null && e.equals(arr[i]))||(e==null && arr[i]==null)){
                remanentEle = new Object[size-i-1];
                System.arraycopy(arr,i+1,remanentEle,0,remanentEle.length);
                System.arraycopy(remanentEle,0,arr,i,remanentEle.length);
                arr = Arrays.copyOfRange(arr,0,size-1);
                hasE = true;
                break;
            }
        }
        if(hasE){
            size--;
        }
        return hasE;
    }

    public E get(int index) {
        boolean find = false;
        for(int i=0 ;i<arr.length;i++){
            if(i == index){
                find = true;
                return (E)arr[i];
            }
        }
//        if(!find){
//            throw new RuntimeException("没有找到元素");
//        }
        return null;
    }

    public int size() {
        return this.size;
    }

    public boolean containts(E e) {
        for(int i=0 ; i<arr.length;i++){
            if((e!=null && e.equals(arr[i]))||(e==null && arr[i]==null)){
                return true;
            }
        }
        return false;
    }

    private void change(){
    //    if(arr == null){
            arr = new Object[size];
    //    }
    //    System.arraycopy(arrTmp,0,arr,0,size);
 //       arrTmp = null;
    }

    @Override
    public String toString() {
        return Arrays.toString(arr);
    }
}
