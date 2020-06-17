package com.example.javabaisc.ioc;

/**
 * 容器类
 */
public class MContainer {
    //存储 依赖类对象
    private MDependency m;

    public void eat() {
        System.out.println("饿了，有什么吃的？");
        System.out.println(m.food());
    }

    //这是xml文件的javabean使用setter方式将依赖类对象注入进来
    public void setM(MDependency m) {
        this.m = m;
    }

}
