package com.basic.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class PersonProxyTest {

    public static void main(String[] args) {
        ClassLoader classLoader1 = PersonImpl.class.getClass().getClassLoader();//null
        ClassLoader classLoader2 =PersonImpl.class.getClassLoader();//app
        ClassLoader pClassLoader = classLoader2.getParent();//ext
        ClassLoader ppClassLoader = pClassLoader.getParent();//null

        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");

        Person person = new PersonImpl();
        Person personProxy = new PersonProxy<Person>(person).getProxy();
        personProxy.work();

        InvocationHandler personIH = Proxy.getInvocationHandler(personProxy);//通过代理对象获取InvocationHandler实现
        Class<?> proxyClass = Proxy.getProxyClass(Person.class.getClassLoader(), Person.class);//获取某个对象的代理对象
        boolean isProxy = Proxy.isProxyClass(personProxy.getClass());//判断是否为代理对象

    }
}
