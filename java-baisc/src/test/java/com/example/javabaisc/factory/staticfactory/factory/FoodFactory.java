package com.example.javabaisc.factory.staticfactory.factory;

import com.example.javabaisc.factory.staticfactory.origin.Apple;
import com.example.javabaisc.factory.staticfactory.origin.Food;
import com.example.javabaisc.factory.staticfactory.origin.Noodles;

public class FoodFactory {

    //获取苹果
    public static Food getApple() {
        return new Apple();
    }

    //获取面条
    public static Food getNoodles() {
        return new Noodles();
    }


}
