package com.concurrent._synchronized;

/**
 * 当synchronized作用在静态方法上时，该类加锁
 *
 */
public class SycnB implements Runnable{
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
        SycnB sycnB = new SycnB();
        Thread t1 = new Thread(sycnB);
        Thread t2 = new Thread(sycnB);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println(sycnB.i);
    }

}
