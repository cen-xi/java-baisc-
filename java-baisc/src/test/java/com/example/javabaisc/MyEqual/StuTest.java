package com.example.javabaisc.MyEqual;

import org.junit.jupiter.api.Test;

public class StuTest {

    @Test
    public void t() {
        Student student = new Student();
        student.setAge(18);
        student.setName("cen");

        Student student2 = new Student();
        student2.setAge(18);
        student2.setName("cen");

        System.out.println("========equql 比较对象=============");
        if (student.equals(student2)) {
            System.out.println("一样");
        } else {
            System.out.println("不一样");
        }
        System.out.println("==========使用 == 比较地址空间====================");
        if (student == student2) {
            System.out.println("一样");
        } else {
            System.out.println("不一样");
        }


    }
//
//
//    public boolean equals(Object anObject) {
//        //比较地址值
//        if (this == anObject) {
//            return true;
//        }
//        //判断输入参数是否为String类型 ，不是则直接返回false
//        if (anObject instanceof String) {
//            //输入参数 强转成string
//            String anotherString = (String) anObject;
//            //记录 比较值 的字符长度
//            int n = count;
//            //判断 比较值 的字符长度和 输入参数 的字符长度是否相等
//            if (n == anotherString.count) {
//                //比较值 转字符数组
//                char v1[] = value;
//                //输入参数 转字符数组
//                char v2[] = anotherString.value;
//                //记录比较中 比较值 的当前比较位置
//                int i = offset;
//                //记录比较中 输入参数 的当前比较位置
//                int j = anotherString.offset;
//                //当比较值 的字符长度 每次减1 不等于 0时执行内部代码块
//                while (n-- != 0) {
//                    //判断这两个 字符的 ASCLL 码是否相等，只要有一个不相等的则直接返回 false ,结束循环
//                    if (v1[i++] != v2[j++])
//                        return false;
//                }
//                //执行的这里则表示 全部相等
//                return true;
//            }
//        }
//        //执行到这里表示输入参数不是String 类型
//        return false;
//    }


}
