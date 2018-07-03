package com.concurrent._synchronized;

/**
 * 当synchronized作用在静态方法上时，对类加锁
 * 对货币SyncA1
 *
 */
public class SycnB1 implements Runnable{
    static int i;

    public static synchronized void increase(){
        i++;
    }


    public void run() {
        for(int x=0;x<1000000;x++){
            increase();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SycnB1 sycnB1 = new SycnB1();
        SycnB1 sycnB2 = new SycnB1();
        Thread t1 = new Thread(sycnB1);
        Thread t2 = new Thread(sycnB2);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println(sycnB1.i);
        System.out.println(sycnB2.i);
    }

}
