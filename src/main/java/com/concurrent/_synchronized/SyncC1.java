package com.concurrent._synchronized;

/**
 *同步模块加锁，其他线程拿到指定对象锁才行
 * synchronized修饰的对象、列对应实例方法/静态方法
 *
 */
public class SyncC1 implements Runnable {
    static int i;

    public void run() {
        // class加锁 类似于静态方法
        synchronized (SyncC1.class){
            for(int x=0;x<1000000;x++){
                i++;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SyncC1 syncC = new SyncC1();
        SyncC1 syncC1 = new SyncC1();
        Thread t1 = new Thread(syncC);
        Thread t2 = new Thread(syncC1);

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println(i);

    }
}
