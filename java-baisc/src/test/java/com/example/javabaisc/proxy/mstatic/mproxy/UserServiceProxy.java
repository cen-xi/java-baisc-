package com.example.javabaisc.proxy.mstatic.mproxy;

import com.example.javabaisc.proxy.mstatic.service.UserService;
import com.example.javabaisc.proxy.mstatic.service.serviceImpl.UserServiceImpl;

import java.util.Date;

/**
 * 代理类
 */
public class UserServiceProxy implements UserService {
    //final可有可不有 ，但是最后idea提示最好加上去
    //类型定义必须与注入类型对应，否则需要强转
    private final UserServiceImpl target;

    //有参构造函数，用于注入业务层的实现类
    public UserServiceProxy(UserServiceImpl target){
            this.target = target;
    }

    @Override
    public void getname() {
        System.out.println("进入静态代理类，准备开始增强业务");
        System.out.println("老王人哪去了？");
        target.getname();
        System.out.println("准备退出静态代理类，增强业务结束，"+new Date());
    }
}
