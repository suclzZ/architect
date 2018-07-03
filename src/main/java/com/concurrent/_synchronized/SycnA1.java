package com.concurrent._synchronized;

/**
 * 当synchronized作用在普通方法上时，当前实例加锁
 *
 */
public class SycnA1 implements Runnable{
    static int i;

    public synchronized void increase(){
        i++;
    }

    public void run() {
        for(int x=0;x<1000000;x++){
            increase();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SycnA1 sycnA1 = new SycnA1();//多个实例
        SycnA1 sycnA2 = new SycnA1();
        Thread t1 = new Thread(sycnA1);
        Thread t2 = new Thread(sycnA2);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println(sycnA1.i);
        System.out.println(sycnA2.i);
    }

}
