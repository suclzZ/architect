package com.concurrent.lock;

/**
 * @author sucl
 * @since 2018/12/12
 */
public class UnReentrantLockTest {

    private UnReentrantLock lock = new UnReentrantLock();

    public void do1(){

        lock.lock();
        System.out.println("do 1");
        do2();
        lock.unlock();
//        do2();
    }

    private void do2() {
        lock.lock();
        System.out.println("do 2");
        lock.unlock();
    }

    public static void main(String[] args) {
        new UnReentrantLockTest().do1();
    }
}
