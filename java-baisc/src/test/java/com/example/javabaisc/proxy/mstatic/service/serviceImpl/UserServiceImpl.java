package com.example.javabaisc.proxy.mstatic.service.serviceImpl;

import com.example.javabaisc.proxy.mstatic.service.UserService;

public class UserServiceImpl implements UserService {
    @Override
    public void getname() {
        System.out.println("老王跑啦");
    }
}
