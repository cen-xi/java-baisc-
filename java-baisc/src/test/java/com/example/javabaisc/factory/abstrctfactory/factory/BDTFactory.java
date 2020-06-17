package com.example.javabaisc.factory.abstrctfactory.factory;

import com.example.javabaisc.factory.abstrctfactory.origin.Food;
import com.example.javabaisc.factory.abstrctfactory.origin.abstractFood.apple.SDApple;
import com.example.javabaisc.factory.abstrctfactory.origin.abstractFood.apple.YNApple;
import com.example.javabaisc.factory.abstrctfactory.origin.abstractFood.noodles.LZNoodele;
import com.example.javabaisc.factory.abstrctfactory.origin.abstractFood.noodles.YLNoodles;

/**
 * 摆地摊-工厂
 */
public class BDTFactory implements FoodFactory{

    @Override
    public Food getApple() {
        //云南苹果
        return new YNApple();
    }

    @Override
    public Food getNoodles() {
        //一乐拉面
        return new YLNoodles();
    }
}
