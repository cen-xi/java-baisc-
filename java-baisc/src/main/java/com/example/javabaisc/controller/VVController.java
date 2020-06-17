package com.example.javabaisc.controller;


import com.example.javabaisc.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VVController {

    @Autowired
    private FoodService foodService;

    @RequestMapping("/")
    public String gg(int id) {

        if (id == 1) {
            // 强制抛出异常
            throw new RuntimeException();
        } else {
            return foodService.food();
        }

    }

}
