package com.proxy.cglib;

import net.sf.cglib.core.DebuggingClassWriter;

public class DogProxyTest {

    public static void main(String[] args) {

        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "resource/proxy/cglib");

        DogProxy dogProxy = new DogProxy();
        Dog dogPoxy = (Dog) dogProxy.create(Dog.class);
        dogPoxy.wang();


        Dog dog1 = new Dog();
        DogProxy dogProxy1 = new DogProxy(dog1);
        Dog dogPoxy1 = (Dog) dogProxy.create(Dog.class);
        dogPoxy1.wang();
    }
}
