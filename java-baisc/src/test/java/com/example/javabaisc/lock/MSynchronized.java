package com.example.javabaisc.lock;

public class MSynchronized {
    public synchronized void method1(String name) throws InterruptedException {
        int i = 0;
        while (true) {
            i++;
            System.out.println(name + "我是方法1，当前数字是" + i);
            if (i > 4) {
                System.out.println(name + "我是方法1，退出");
                break;
            }
            Thread.sleep(1000);
        }
    }

    public synchronized void method2(String name) throws InterruptedException {
        int i = 0;
        while (true) {
            i++;
            System.out.println(name + "我是方法2，当前数字是" + i);
            if (i > 4) {
                System.out.println(name + "我是方法2，退出");
                break;
            }
            Thread.sleep(1000);
        }


    }

    public static synchronized void method3(String name) throws InterruptedException {
        int i = 0;
        while (true) {
            i++;
            System.out.println(name + "我是方法3，当前数字是" + i);
            if (i > 4) {
                System.out.println(name + "我是方法3，退出");
                break;
            }
            Thread.sleep(1000);
        }


    }

    public static synchronized void method4(String name) throws InterruptedException {
        int i = 0;
        while (true) {
            i++;
            System.out.println(name + "我是方法4，当前数字是" + i);
            if (i > 4) {
                System.out.println(name + "我是方法4，退出");
                break;
            }
            Thread.sleep(1000);
        }

    }
}
