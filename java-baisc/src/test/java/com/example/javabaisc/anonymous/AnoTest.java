package com.example.javabaisc.anonymous;

import org.junit.jupiter.api.Test;

public class AnoTest {

    /**
     * 使用接口实现匿名内部类
     */
    @Test
    public void t1() {
        //jdk8以下的需要使用final
//       final int kk = 666;
        int kk = 666;

        //方法一：实例接口后直接配置内部方法来实现逻辑
        InterFood interFood = new InterFood() {
            @Override
            public void eat() {
                System.out.println("吃苹果");
            }

            @Override
            public void count() {
                System.out.println("100个");
            }
        };
        interFood.eat();
        interFood.count();
//=================================
        System.out.println("==============================================");
        //方法二：以接口实现的匿名内部类作为对象参数，直接传入方法内使用
        AnoTest anoTest = new AnoTest();
        anoTest.ano(new InterFood() {
            @Override
            public void eat() {
                System.out.println("这次要吃桃子。" + kk);
            }

            @Override
            public void count() {
                System.out.println("500个桃子够吗");
            }
        });


    }

    public void ano(InterFood interFood) {
        interFood.eat();
        interFood.count();
    }


//======================================================================================================

    /**
     * 使用抽象类实现匿名内部类
     */
    @Test
    public void t2() {
        //方法一：传入参数
        AbstractFood abstractFood = new AbstractFood("苹果") {
            // 初始化块
            {
                System.out.println("匿名内部类的初始化块...");
            }
            @Override
            void getCount() {
                System.out.println("200个");
            }
        };
        System.out.println(abstractFood.getName());
        abstractFood.getCount();
        System.out.println("==============================================");
        //
        //方法二： 引用外部参数变量
        String mname = "樱桃";
        AbstractFood abstractFood2 = new AbstractFood() {
            // 初始化块
            {
                System.out.println("匿名内部类的初始化块...");
            }
            @Override
            void getCount() {
                System.out.println("50个");
            }
            //即便不是抽象方法，也可以覆写类里面的普通方法
            @Override
            public String getName() {
                return mname;
            }
        };
        System.out.println(abstractFood2.getName());
        abstractFood2.getCount();
        System.out.println("==============================================");
        //
        //方法三：匿名内部类作为参数传入方法中
        AnoTest anoTest = new AnoTest();
        anoTest.abs(new AbstractFood("火龙果") {
            // 初始化块
            {
                System.out.println("匿名内部类的初始化块...");
            }
            @Override
            void getCount() {
                System.out.println("10个");
            }
        });


    }

    public void abs(AbstractFood abstractFood) {
        System.out.println(abstractFood.getName());
        abstractFood.getCount();
    }


}
