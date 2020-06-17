package com.example.javabaisc.myThread;

import org.junit.jupiter.api.Test;

/**
 * 守护线程 ，当 用户线程 结束后，守护线程跟着结束
 *
 * 不可使用@Test测试 ，否则所有线程都会随着用户线程结束而结束，看不到非守护线程运行效果
 *
 *
 */
public class T6 {

public static void main(String[] args) throws InterruptedException {
        MyThread6 a = new MyThread6();
        //设置该线程为守护线程
        a.setDaemon(true);
        a.start();
        Thread.sleep(1000);
        System.out.println("用户线程结束，退出，守护线程也跟着退出");


    }
}


class MyThread6 extends Thread {
    private int i = 0;

    @Override
    public void run() {
        while (true) {
            System.out.println(i++);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }

    }
}
