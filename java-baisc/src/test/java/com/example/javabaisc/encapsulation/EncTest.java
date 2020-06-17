package com.example.javabaisc.encapsulation;

import org.junit.jupiter.api.Test;

import java.util.*;

public class EncTest {

    @Test
    public void t() {
//        Encapsulation e = new Encapsulation();
//        e.setAge(18);
//        e.setName("你大爷");
//        //打印属性变量
//        System.out.println(e);
//        System.out.println("//=======================");
//        //调用被保护的方法
//        e.useCall();

    }


    /**
     * 数字排序
     */
    @Test
    public void t2() {
        List<Integer> list = new ArrayList<>();
        list.add(2);
        list.add(4);
        list.add(8);
        list.add(5);
        list.add(6);
//        list.sort(new Comparator<Integer>() {
//            @Override
//            public int compare(Integer o1, Integer o2) {
//                //升序
////                return o1-o2;
//                //降序
//                return o2 - o1;
//            }
//        });
        //使用 Lambda 表达式
        //升序
//        list.sort((o1, o2) -> o1 - o2);
        //降序
        list.sort((o1, o2) -> o2 - o1);
        System.out.println(list);
    }

    /**
     * 字符串排序
     */
    @Test
    public void t3() {
        List<String> list = new ArrayList<>();
        list.add("2");
        list.add("4");
        list.add("du");
        list.add("f");
        list.add("7");
//        list.sort(new Comparator<String>() {
//            @Override
//            public int compare(String o1, String o2) {
//                //升序
////                return o1.compareTo(o2);
//                //降序
//                return o2.compareTo(o1);
//            }
//        });
        //使用 Lambda 表达式
        //升序
        list.sort(String::compareTo);
        //或
//        list.sort((o1, o2) ->o1.compareTo(o2));
        //
        //降序
//        list.sort(Comparator.reverseOrder());
        //或
//        list.sort((o1, o2) ->o2.compareTo(o1));
        System.out.println(list);
    }


}
