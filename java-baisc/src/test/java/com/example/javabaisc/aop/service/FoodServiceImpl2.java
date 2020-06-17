package com.example.javabaisc.aop.service;


import org.springframework.stereotype.Service;

@Service
public class FoodServiceImpl2 implements FoodService2 {
    @Override
    public String food() {

        System.out.println("吃什么");

        return "apple and tea";
    }
}
