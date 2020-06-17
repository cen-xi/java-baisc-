package com.example.javabaisc.myThread;

import org.junit.jupiter.api.Test;

/**
 * 共享数据的情况 [使用 同步锁 可解决脏数据问题]
 */
public class T3 {
    @Test
    public void t3() {
        MyThread3 myThread3 = new MyThread3();
        //参数分别是 自定义线程 ，线程名字
        Thread m1 = new Thread(myThread3, "1");
        Thread m2 = new Thread(myThread3, "2");
        Thread m3 = new Thread(myThread3, "3");
        Thread m4 = new Thread(myThread3, "4");
        Thread m5 = new Thread(myThread3, "5");
        m1.start();
        m2.start();
        m3.start();
        m4.start();
        m5.start();

    }
}

//class  MyThread3 extends Thread {
//    //公共数据
//    private int count = 5;
//    //每次new MyThread3只会执行一次run();
//    @Override
//    public  void run() {
//        super.run();
//        int yu = count--;
//        System.out.println("由 线程 " + MyThread3.currentThread().getName() + " 计算，剩余count=" + yu);
//
//    }
//}

/*
由 线程 3 计算，剩余count=2
由 线程 5 计算，剩余count=3
由 线程 1 计算，剩余count=5
由 线程 4 计算，剩余count=1
由 线程 2 计算，剩余count=4
 */

/*
本应该剩余count是依次递减的 ，可是显示是乱数据，
因为在大多数jvm中，count–-的操作分为如下三步：
取得原有count值
计算 -1 的结果
进行赋值
并发线程获取count的值显然是无法避免脏数据

 */

//使用锁 synchronized，即可解决线程脏数据
class  MyThread3 extends Thread {
    //公共数据
    private int count = 5;
    //每次new MyThread3只会执行一次run();
    //添加了同步锁 synchronized
    @Override
    public synchronized void run() {
        super.run();
        int yu = count--;
        System.out.println("由 线程 " + MyThread3.currentThread().getName() + " 计算，剩余count=" + yu);

    }
}
/*
由 线程 1 计算，剩余count=5
由 线程 5 计算，剩余count=4
由 线程 4 计算，剩余count=3
由 线程 3 计算，剩余count=2
由 线程 2 计算，剩余count=1
 */