package com.basic.queue;

import java.util.Queue;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *
 *
 */
public class QueueTest {

    Object obj = new Object();

    static BlockingQueue<String> market = new ArrayBlockingQueue<String>(20);

    ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    Lock read = lock.readLock();

    Lock write = lock.writeLock();

    public static void main(String[] args) {
        new QueueTest().test();
    }

    public void test() {

        ExecutorService customer = Executors.newFixedThreadPool(10);
        ExecutorService producer = Executors.newFixedThreadPool(10);


        for(;;){
            customer.submit(()->{
                try {
                    String commodity = market.take();
                    print("  购买商品 ："+commodity+"库存 : "+market.size());
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                }
            });

            producer.submit(()->{
                try {
                    String commodity = (int)(Math.random()*1000)+"";
                    market.put(commodity);
                    print("生产商品 ："+commodity+"库存 : "+market.size());
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                }
            });
        }

    }

    public  void print(String msg){
        synchronized(obj){
            System.out.println(msg);
        }
    }
}
