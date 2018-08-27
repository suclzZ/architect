package com.concurrent.demo;

public class Volatile {

    private static volatile boolean is = false;

    public static void main(String[] args) throws InterruptedException {

        new Thread(()->{
            while (true){
                if(is == !is){
                    System.out.println("+++++++++++++++++ "+is);
                }else{
                    System.out.println("----------------- "+is);
                }
            }

        }).start();


        Thread.sleep(1000);

        new Thread(()->{
            while (true){
                is = !is;
            }
        }).start();
    }
}
