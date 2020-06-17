package com.example.javabaisc.proxy.mdynamic.mproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Date;

/**
 * 代理类
 */

public class DynamicProxy {

    //目标对象
    //final可有可不有 ，但是最后idea提示最好加上去
    private final Object target;

    //有参构造函数，用于注入业务层的实现类
    public DynamicProxy(Object target) {
        this.target = target;
    }
    //方法名可随意定义，这个无关紧要，目的是调用newProxyInstance去内存动态创建代理对象
    public Object getProxyInstance() {
        //到内存动态创建代理对象，参数分别是：目标类的类加载器 、目标对象实现的接口的类型、事件处理器
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new InvocationHandler() {
                    //重写事件处理器的调用方法
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println("进入动态代理类，准备开始增强业务");
                        System.out.println("老王人哪去了？");
                        System.out.println("目标类名字："+target.getClass().getName());
                        System.out.println("方法名："+method.getName());
                        //执行目标对象方法，参数分别是 目标类对象 、当前方法注入参数
                        Object returnValue = method.invoke(target, args);
                        System.out.println("准备退出动态代理类，增强业务结束，" + new Date());
                        return returnValue;
                    }
                });
    }

}
