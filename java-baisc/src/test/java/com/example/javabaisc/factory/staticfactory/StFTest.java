package com.example.javabaisc.factory.staticfactory;

import com.example.javabaisc.factory.staticfactory.factory.FoodFactory;
import com.example.javabaisc.factory.staticfactory.origin.Food;
import org.junit.jupiter.api.Test;

public class StFTest {
    @Test
    public void t() {

        //从工厂获取苹果
        Food food = FoodFactory.getApple();
        food.getFood();
        //
        //从工厂获取面条
        Food food2 = FoodFactory.getNoodles();
        food2.getFood();

    }

}
