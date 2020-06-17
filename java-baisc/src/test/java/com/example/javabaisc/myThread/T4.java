package com.example.javabaisc.myThread;

import org.junit.jupiter.api.Test;

/**
 * 使用 interrupt() 和 return 配合 停止线程
 */

public class T4 {


    @Test
    public void t() throws InterruptedException {
        MyThread4 m = new MyThread4();
        m.start();
        //主线程休眠0.01秒后执行
        Thread.sleep(10);
        m.interrupt();
        Thread.sleep(1000);
        System.out.println("停了么？");

    }

}

class MyThread4 extends Thread {

    //每次new MyThread4只会执行一次run();
    @Override
    public  void run() {
        super.run();
        int i = 0;
        while (true) {
            //判断该线程是否被执行了interrupt()
            if (this.isInterrupted()) {
                System.out.println("线程被停止");
                return;
            }
            System.out.println(i++);
        }

    }
}
