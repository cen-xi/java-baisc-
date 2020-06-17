package com.example.javabaisc.proxy.mstatic;


import com.example.javabaisc.proxy.mstatic.mproxy.UserServiceProxy;
import com.example.javabaisc.proxy.mstatic.service.UserService;
import com.example.javabaisc.proxy.mstatic.service.serviceImpl.UserServiceImpl;
import org.junit.jupiter.api.Test;

/**
 * 静态代理
 */

public class MSTest {

    //无静态代理
    @Test
    public void t() {
        //实例业务层对象【类型也可以使用接口的】
        UserServiceImpl userService = new UserServiceImpl();
        //执行业务层
        userService.getname();
    }

    //使用静态代理
    @Test
    public void t2() {
        //实例业务层对象【类型也可以使用接口的】
        UserServiceImpl userService = new UserServiceImpl();
        //实例代理类，并注入业务层对象
        UserServiceProxy userServiceProxy = new UserServiceProxy(userService);
        //执行代理业务
        userServiceProxy.getname();
    }

}

/*
总结：
（1）其实，所谓的静态代理，其实就是创建一个类【名为代理类】，然后该类实现了与目标类【业务层对象】一样的接口后，
重写接口内容，里面做增强业务，也就是自定义操作，然后需要执行目标类的方法时，调用注入的目标类[即业务层对象]的方法即可，
说白了就是目标类外再套一层方法而已。
（2）重写目的是保证与目标类的方法一样，不仅可以确保不写错，还可以不用手写，这就很舒服了，
但是并不是说目标类必须要有接口才可以使用静态代理，因此，目标类不论是否有接口都可以使用静态代理。
(3)缺点：因为代理对象需要与目标对象实现一样的接口,所以会有很多代理类,类太多。同时,一旦接口增加方法,目标对象与代理对象都要维护。


 */