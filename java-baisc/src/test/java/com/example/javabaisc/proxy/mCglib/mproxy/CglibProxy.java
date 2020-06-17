package com.example.javabaisc.proxy.mCglib.mproxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Date;

public class CglibProxy implements MethodInterceptor {
    //目标对象
    //final可有可不有 ，但是最后idea提示最好加上去
    private final Object target;

    //有参构造函数，用于注入业务层的实现类
    public CglibProxy(Object target) {
        this.target = target;
    }

    //给目标对象创建一个代理对象
    public Object getProxyInstance(){
        //工具类
        Enhancer en = new Enhancer();
        //设置父类
        en.setSuperclass(target.getClass());
        //设置回调函数
        en.setCallback(this);
        //创建子类(代理对象)
        return en.create();

    }

    //重写拦截方法
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("进入Cglib代理类，准备开始增强业务");
        System.out.println("准备发送邮件");
        System.out.println("目标类名字："+target.getClass().getName());
        System.out.println("方法名："+method.getName());
        //执行目标对象方法，参数分别是 目标类对象 、当前方法注入参数
        Object returnValue = method.invoke(target, objects);
        System.out.println("准备退出Cglib代理类，增强业务结束，" + new Date());
        return returnValue;
    }
}
