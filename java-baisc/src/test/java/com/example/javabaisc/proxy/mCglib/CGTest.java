package com.example.javabaisc.proxy.mCglib;

import com.example.javabaisc.proxy.mCglib.Util.Mail;
import com.example.javabaisc.proxy.mCglib.mproxy.CglibProxy;

import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest
@RunWith(SpringRunner.class)
public class CGTest {

    @Test
    public void t() {
        //实例目标类
        Mail mail = new Mail();
        //实例 Cglib 代理类 ，注入目标对象 ，创建代理对象，返回结果强转成目标对象一样的类型
        Mail mailProxy = (Mail) new CglibProxy(mail).getProxyInstance();
        //执行代理对象，方法则与目标类的相同
        mailProxy.send();

    }


    @Autowired
    private Mail mail;

    /**
     * 使用javabean注解，不使用new实例化目标类
     */
    @Test
    public void t2() {
        //实例 Cglib 代理类 ，注入目标对象 ，创建代理对象，返回结果强转成目标对象一样的类型
        Mail mailProxy = (Mail) new CglibProxy(mail).getProxyInstance();
        //执行代理对象，方法则与目标类的相同
        mailProxy.send();
    }


}
/*
总结：
（1）记得需要导入 cglib依赖包 ，这其实是个代码生成包，在内存中构建一个子类对象从而实现对目标对象功能的扩展。
（2）Cglib 代理 又称为 子类代理。
（3）代理类需要实现MethodInterceptor接口 ，用于重写拦截方法【intercept()】，内部的增强操作方式 与 动态代理十分相似。
（4）创建代理对象需要使用工具类Enhancer。
（4）允许代理那些那么没有实现任何接口的目标类，解决了动态代理的缺点。
（5）底层是ASM字节码框架，不建议直接操作ASM。

 */