package com.example.javabaisc.encapsulation;

public class Encapsulation {
    //被保护的属性变量
    private int age;
    private String  name;
    //被保护的方法
    private void call(){
        System.out.println("java 封装");
    }


//==============================================
    //外部调用的放法
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Encapsulation{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }

    //调用被保护的方法
    public void useCall(){
        this.call();
    }

    //=========================================
}
