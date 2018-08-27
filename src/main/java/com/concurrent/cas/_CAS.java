package com.concurrent.cas;

import sun.misc.Unsafe;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class _CAS {

    private Unsafe unsafe;

    public Unsafe getUnsafe() {
        unsafeField();
        return unsafe;
    }

    /**
     * 通过属性获取
     */
    private void unsafeField(){
        if(unsafe==null){
            try {
                Field f = Unsafe.class.getDeclaredField("theUnsafe");
                f.setAccessible(true);
                unsafe = (Unsafe) f.get(null);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private void unsafeConstruct(){
        Class<Unsafe> clazz = Unsafe.class;
        try {
            Constructor<Unsafe> construct = clazz.getDeclaredConstructor();
            construct.setAccessible(true);
            unsafe =  construct.newInstance();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过构造器获取
     */
    public _CAS(){

    }

    public static void main(String[] args) {
        _CAS cas = new _CAS();
        Unsafe unsafe = cas.getUnsafe();
        System.out.println(unsafe);
    }
}
