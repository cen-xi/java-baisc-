package com.example.javabaisc.factory.methodfactory;

import com.example.javabaisc.factory.methodfactory.factory.FoodFactory;
import com.example.javabaisc.factory.methodfactory.origin.Food;
import org.junit.jupiter.api.Test;

public class StFTest {
    @Test
    public void t(){

        //从工厂获取苹果
        Food food = FoodFactory.getFood(FoodFactory.FF_Apple);
        if (food != null){
            food.getFood();
        }
        //从工厂获取面条
        Food food2 = FoodFactory.getFood(FoodFactory.FF_Noodles);
        if (food2 != null){
            food2.getFood();
        }

    }

}
