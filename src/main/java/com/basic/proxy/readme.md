#### 关于jdk和cglib动态代理
#### 从示例可以看出一些区别
+ jdk代理时，需要代理对象的实例，感觉上是一种继承关系，代理对象继承被代理对象
> PersonImpl -> Person 
> PersonProxy ->Person
> PersonProxy.invoke(... PersonImpl.invoke ...)将目标对象作为参数传入代理对象进行调用，和静态代理相似

+ cglib代理时，不用实例化对象，直接通过对象重新构建新的classs，并继承目标类，实现比较复杂
> 分别将目标对象和代理对象包装，并对所有方法进行包装，添加了拦截处理
> 通过方法proxy.invokeSuper(obj, args)可以看到，调用都是通过代理类完成，其实调用的都是包装后的类与方法