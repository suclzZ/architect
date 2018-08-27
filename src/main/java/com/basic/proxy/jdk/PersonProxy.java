package com.basic.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;

public class PersonProxy<T> implements InvocationHandler {

    private T target;

    public PersonProxy(T target){
        assert target==null:"target can't be null";
        this.target = target;
    }

    /**
     *
     * @param proxy 代理对象
     * @param method 被代理对象方法
     * @param args 被代理对象参数
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before proxy");
        method.invoke(target,args);
        System.out.println("after proxy");
        return null;
    }

    public T getProxy(){
        if(target.getClass().isInterface()){
            throw new RuntimeException("target Object can't be interface");
        }
        Class<?>[] interfaces = target.getClass().getInterfaces();
        if(interfaces ==null || interfaces.length == 0){
            throw new RuntimeException("target object must implements at least one interface");
        }
        Object proxy = Proxy.newProxyInstance(target.getClass().getClassLoader(), interfaces, this);
        return (T)proxy;
    }

}
