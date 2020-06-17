package com.example.javabaisc.factory.abstrctfactory.factory;

import com.example.javabaisc.factory.abstrctfactory.origin.Food;

/**
 * 工厂接口
 */
public interface FoodFactory {
    //获取苹果类食物
    public Food getApple();
    //获取面条类食物
    public Food getNoodles();

}
