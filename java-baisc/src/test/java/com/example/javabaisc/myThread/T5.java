package com.example.javabaisc.myThread;

import org.junit.jupiter.api.Test;

/**
 * 线程优先级对比
 * setPriority不一定起作用的，在不同的操作系统不同的jvm上，效果也可能不同
 * 数字越大优先级越高，但不是优先级低就一定在优先级高的线程后面运行，而是运行的机率会低一些，，反之亦然。
 *
 */
public class T5 {
    @Test
    public void t() {
        MyThread5 a = new MyThread5("A");
        MyThread5 b = new MyThread5("B");
//        a.setPriority(10);
//        b.setPriority(1);
        a.setPriority(1);
        b.setPriority(10);
        a.start();
        b.start();

    }

}

class MyThread5 extends Thread {

    MyThread5(String name) {
        super();
        this.setName(name);
    }

    @Override
    public  void run() {
        super.run();
        System.out.println("我是线程:" + Thread.currentThread().getName());
    }
}
