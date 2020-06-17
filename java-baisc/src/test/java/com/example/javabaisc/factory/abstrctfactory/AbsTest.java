package com.example.javabaisc.factory.abstrctfactory;

import com.example.javabaisc.factory.abstrctfactory.factory.BDTFactory;
import com.example.javabaisc.factory.abstrctfactory.factory.DCSFactory;
import com.example.javabaisc.factory.abstrctfactory.factory.FoodFactory;
import com.example.javabaisc.factory.abstrctfactory.origin.Food;
import org.junit.jupiter.api.Test;

public class AbsTest {
    @Test
    public void t() {

        System.out.println("去大超市买吃的");
        //去大超市买吃的
        FoodFactory dcs = new DCSFactory();
        //吃苹果
        Food food1 = dcs.getApple();
        food1.getFood();
        //吃面条
        Food food2 = dcs.getNoodles();
        food2.getFood();
        System.out.println("=================");
        //
        System.out.println("去摆地摊买吃的");
        //去摆地摊买吃的
        FoodFactory bdt = new BDTFactory();
        //吃苹果
        Food food3 = bdt.getApple();
        food3.getFood();
        //吃面条
        Food food4 = bdt.getNoodles();
        food4.getFood();
    }
}

/*
总结：
（1）感觉好鸡肋啊，写的还这么多，无语，就像个漏斗，当个分类器用。
（2）分两部分，工厂和 产品源 ，工厂负责打包分配的， 产品源 负责提供材料的.
（3）在产品源，
    所有的材料本质上都是食物【Food接口】，往下分，可以分出苹果和面条两种【抽象类】，
    抽象类实现接口，统让不同的抽象类统一了使用方法名称，同时使用java 多态 ，可以让不同的抽象类可以向上转到相同的食物类型。
    再往下面分，会有各种苹果和面条【实现类】，
    让实现类继承抽象类，统一了食物的使用方法名称，使用Java多态 ，可让实现类向上转类型，最终所有的食物都是归Food类型，
    也就是说让不同的食物统一了类型，因此，消费者不再需要考虑这个食物需要用什么东西装，拿来能吃就行。
（4）在工厂，
    工厂规定了可以吃的食物【工厂接口】，运给不同的销售地【工厂实现类】，
    工厂实现类实现工厂接口，统一了对食物的操作方法，也能让不同的实现类向上转到相同的类型【工厂类型】。
    我设立了摆地摊和大超市两个卖东西的地方，虽然地方不同，但是本质上都是卖食物的，根据不同的操作，
    获取相应的食物【在方法里new食物对象】，不论是什么食物，本质都是食物，因此返回数据类型是Food【java多态，向最高类型转型】。
（5）消费者选择一个地点买东西，返回的都是食物工厂类型【java多态，向最高类型转型】，然后付钱【使用统一规定的操作方法获取食物】，
    得到食物对象，类型都是食物，然后可以吃食物啦【对食物做统一规定的操作，由食物接口规定，如果不规定，食物多了，岂不会乱套？】
（6）因此，对消费者来说，只需要选择买东西的地方，选择要吃的食物类型，至于食物的产地，不需要在乎，能吃就行，而食物的选择是由
    店主/地摊主【工厂实现类】决定，消费者给了钱，拿到了食物，就可以吃了，不需要考虑这个食物需要用什么东西装，
    都统一用饭盒装【Food类型】，总不能拿个马桶盖来装吧？



 */