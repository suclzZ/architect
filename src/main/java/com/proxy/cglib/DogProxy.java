package com.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class DogProxy<T> implements MethodInterceptor {
    private T target;

    public DogProxy(T target){
        this.target = target;
    }



    public DogProxy(){}

    public Object create(Class<T> clazz){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        return enhancer.create();
    }

    /**
     *
     * @param obj 对象的代理
     * @param method 原方法
     * @param args 参数
     * @param proxy 方法的代理
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println("before");
        Object object = null;
        if(target!=null){
            object = method.invoke(target,args);
        }else{
            object = proxy.invokeSuper(obj, args);
//            proxy.invoke(obj,args); //调用代理对象方法，死循环
        }
        System.out.println("after");
        return object;
    }


}
