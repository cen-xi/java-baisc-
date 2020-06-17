package com.example.javabaisc.myThread;

import org.junit.jupiter.api.Test;

/**
 * 多个线程之间不共享变量线程安全的情况
 */
public class T2 {

    @Test
    public void t2(){
        MyThread2 m1 = new MyThread2("1");
        MyThread2 m2 = new MyThread2("2");
        MyThread2 m3 = new MyThread2("3");
        m1.start();
        m2.start();
        m3.start();

    }

}

class MyThread2 extends Thread {
    private int count = 5;

    MyThread2(String name) {
//        super();
        //给父类的name属性赋值
        this.setName(name);
    }

    //每次new MyThread2只会执行一次run();
    @Override
    public void run() {
        super.run();
        while (count>0){
            System.out.println("由 线程 " + MyThread2.currentThread().getName() + " 计算，剩余count=" + count);
            count--;
        }
    }
}

/*
由 线程 1 计算，剩余count=5
由 线程 1 计算，剩余count=4
由 线程 1 计算，剩余count=3
由 线程 1 计算，剩余count=2
由 线程 1 计算，剩余count=1
由 线程 3 计算，剩余count=5
由 线程 2 计算，剩余count=5
由 线程 3 计算，剩余count=4
由 线程 3 计算，剩余count=3
由 线程 3 计算，剩余count=2
由 线程 3 计算，剩余count=1
由 线程 2 计算，剩余count=4
由 线程 2 计算，剩余count=3
由 线程 2 计算，剩余count=2
由 线程 2 计算，剩余count=1
 */

/*
每个线程都有自己的实例变量count ,互不影响
 */