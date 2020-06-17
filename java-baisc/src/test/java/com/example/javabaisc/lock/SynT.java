package com.example.javabaisc.lock;

public class SynT {

    public static void main(String[] args){

        Thread s1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
//                    System.out.println("=========我是线程1==========");
                    MSynchronized mSynchronized = new MSynchronized();
                    mSynchronized.method3("我是线程1");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        Thread s2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
//                    System.out.println("==========我是线程2========");
                    MSynchronized mSynchronized2 = new MSynchronized();
                    mSynchronized2.method4("我是线程2");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

        //启动
        s1.start();
        s2.start();
    }
}
