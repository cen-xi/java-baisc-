package com.example.javabaisc.anonymous;

//抽象类
abstract public class AbstractFood {
    //抽象方法
    abstract void getCount();

    //无参构造函数方法
    public AbstractFood() {
    }

    //有参构造函数方法
    public AbstractFood(String name) {
        this.name = name;
    }

    //变量属性
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
