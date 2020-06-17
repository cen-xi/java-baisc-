package com.example.javabaisc.service.serviceImpl;


import com.example.javabaisc.service.FoodService;
import org.springframework.stereotype.Service;

@Service
public class FoodServiceImpl implements FoodService {
    @Override
    public String food() {
        System.out.println("=yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy");
        System.out.println("吃什么");
        System.out.println("=yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy");
        return "apple and tea";
    }
}
