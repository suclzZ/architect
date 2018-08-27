package com.concurrent.demo;

public class ConsumerAndProvider {

    public static void main(String[] args) {
        Buffer buffer = new Buffer();
        Provider provider = new Provider(buffer);
        Consumer consumer = new Consumer(buffer);
        provider.start();
        consumer.start();
    }

}

class Buffer {
    private  int data;
    boolean isEmpty = false;

    public synchronized void provide(int i){
        if(isEmpty == false){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.printf("生产 "+i);
        data = i;
        isEmpty = false;
        notify();
    }

    public synchronized void consume(){
        if(isEmpty == true){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("消费 "+data);
        isEmpty = true;
        notify();
    }

}
class Provider extends Thread{
    private Buffer buffer;

    Provider(Buffer buffer){
        this.buffer = buffer;
    }
    @Override
    public void run() {
        for(int i=0 ;i<5;i++){
            buffer.provide(i);
        }
    }
}

class Consumer extends Thread{
    private Buffer buffer;

    Consumer(Buffer buffer){
        this.buffer = buffer;
    }

    @Override
    public void run() {
        for(int i=0;i<5;i++){
            buffer.consume();
        }
    }
}
