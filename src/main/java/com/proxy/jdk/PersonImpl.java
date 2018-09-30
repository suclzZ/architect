package com.proxy.jdk;

public class PersonImpl implements Person{

    @Override
    public void work() {
        System.out.println("wore");
    }

    @Override
    public void sleep() {
        System.out.println("sleep");
    }
}
