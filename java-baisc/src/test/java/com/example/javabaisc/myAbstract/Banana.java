package com.example.javabaisc.myAbstract;

public class Banana extends Food{
    //重写父类
    @Override
    void food() {
        System.out.println("香蕉");
    }
    void color(){
        System.out.println("yellow");
    }
}
