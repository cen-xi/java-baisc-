package com.example.javabaisc.myAbstract;

import org.junit.jupiter.api.Test;

public class AbstractTest {

    @Test
    public void t(){
        // 子类向父类转型
        Food food = new Apple();
        food.food();
        //父类向子类转型
        Apple apple = (Apple)food;
        apple.color();
        System.out.println("//==================================");
        // 子类向父类转型后作为参数输入
        this.eat(new Apple());
        this.eat(new Banana());

    }

    private void eat(Food food){
        //执行父类的方法，然后执行子类的重写方法
        food.food();
        //因为有一个父类被两个不同的子类继承，因此需要判断此时父类是与哪个一类为同一类的，然后才能强制转成子类，成为子类才可以调用子类方法
        if (food instanceof Apple){
            Apple apple = (Apple)food;
            apple.color();
        }else if(food instanceof Banana){
            Banana banana = (Banana)food;
            banana.color();
        }
    }

}

/*
总结：多态的抽象类与接口有点相似；
父类不需要具体实现方法，仅需要定义即可，需要在单独的类继承后成为子类，在子类实现具体方法，调用父类方法可直接执行子类的重写方法，可被多个不同的类继承，
子类可直接赋值给父类，但是父类需要强转子类 ，如果一个父类下面有多个子类，那么 父类强转子类之前需要确认此刻父类是由哪个系类赋值的后才可以转到对应的子类
否则会报错。
可见父类可用于多种状态，因此，这就是多态。

 */