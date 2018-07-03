package com.concurrent._synchronized;

/**
 * 当synchronized作用在普通方法上时，当前实例加锁
 *
 */
public class SycnA implements Runnable{
    public int i;

    public synchronized void increase(){
        i++;
    }

    public void run() {
        for(int x=0;x<1000000;x++){
            increase();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SycnA sycnA = new SycnA();//一个实例
        Thread t1 = new Thread(sycnA);
        Thread t2 = new Thread(sycnA);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println(sycnA.i);
    }

}
