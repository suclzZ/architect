package com.concurrent.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 不可重入锁
 * 导致死锁出现
 * @author sucl
 * @since 2018/12/12
 */
public class UnReentrantLock {

    private boolean lock = false;

    public synchronized void lock(){
        if(lock){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }else{
            lock = true;
        }
    }

    public synchronized  void unlock(){
        if(lock){
            lock = false;
            notifyAll();
        }
    }

}
