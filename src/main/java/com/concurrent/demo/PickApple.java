package com.concurrent.demo;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * ?? 线程互斥 实现方案
 *
 */
public class PickApple {

    public static void main(String[] args) throws InterruptedException {
        final Object minitor1 = new Object();
        final Object minitor2 = new Object();

        final Queue<String> basket = new ArrayBlockingQueue<String>(5);
        basket.add("apple");

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (basket.size()<5){
                    add(basket);
                    if(basket.size()>1){
                        minitor2.notify();
                    }
                    if(basket.size()==5){
                        try {
                            minitor1.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (basket.size()>0){
                    sub(basket);
                    if(basket.size()<3){
                        minitor1.notify();
                    }
                    if(basket.size()==0){
                        try {
                            minitor2.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }

    public static String uuid(){
        return UUID.randomUUID().toString();
    }

    public synchronized static void add(Queue<String> basket){
        String apple = "apple"+uuid();
        basket.offer( apple );
        System.out.println("添加:"+apple);
    }

    public synchronized static void sub(Queue<String> basket){
        String apple = basket.poll();
        System.out.println("删除:"+apple);
    }

}
