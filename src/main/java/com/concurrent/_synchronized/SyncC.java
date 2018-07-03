package com.concurrent._synchronized;

import java.util.concurrent.TimeUnit;

/**
 *同步模块加锁，其他线程拿到指定对象/类锁才行
 * synchronized修饰的对象、列对应实例方法/静态方法
 *
 */
public class SyncC implements Runnable {
    Object monitor = new Object();
    static int i;

    public void run() {
        // this,or any Objecct实例级别，类似于实例方法
        synchronized (monitor){
            for(int x=0;x<1000000;x++){
                i++;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SyncC syncC = new SyncC();
        Thread t1 = new Thread(syncC);
        Thread t2 = new Thread(syncC);

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println(i);

    }
}
