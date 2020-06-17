package com.example.javabaisc.factory.methodfactory.factory;

import com.example.javabaisc.factory.methodfactory.origin.Apple;
import com.example.javabaisc.factory.methodfactory.origin.Food;
import com.example.javabaisc.factory.methodfactory.origin.Noodles;

public class FoodFactory {
    public static String FF_Apple = "apple";
    public static String FF_Noodles = "noodles";

    public static Food getFood(String key) {
        if (key.equals(FF_Apple)) {
            return new Apple();
        } else if (key.equals(FF_Noodles)) {
            return new Noodles();
        } else {
            System.out.println("没有这种食物");
            return null;
        }

    }

}
