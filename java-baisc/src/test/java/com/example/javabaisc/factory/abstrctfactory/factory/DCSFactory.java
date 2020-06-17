package com.example.javabaisc.factory.abstrctfactory.factory;

import com.example.javabaisc.factory.abstrctfactory.origin.Food;
import com.example.javabaisc.factory.abstrctfactory.origin.abstractFood.apple.SDApple;
import com.example.javabaisc.factory.abstrctfactory.origin.abstractFood.noodles.LZNoodele;
import com.example.javabaisc.factory.abstrctfactory.origin.abstractFood.noodles.YLNoodles;

/**
 * 大超市-工厂
 */
public class DCSFactory implements FoodFactory{

    @Override
    public Food getApple() {
        //山东苹果
        return new SDApple();
    }

    @Override
    public Food getNoodles() {
        //兰州拉面
        return new LZNoodele();
    }
}
