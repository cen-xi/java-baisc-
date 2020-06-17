package com.example.javabaisc.myThread;

import org.junit.jupiter.api.Test;

public class T1 {

    @Test
    public void t1(){
        //调用方法一
//        Mythread m = new Mythread();
//        m.start();


        //调用方法二
        MyRunnable mr = new MyRunnable();
        Thread thread=new Thread(mr);
        thread.start();
        System.out.println("结束");


    }


}

//方法一：
class Mythread extends Thread {
    @Override
    public void run(){
        super.run();
        System.out.println("我的线程");

    }

}

//方法二：
class MyRunnable implements Runnable{

    @Override
    public void run() {
        System.out.println("使用接口实现我的线程");
    }
}