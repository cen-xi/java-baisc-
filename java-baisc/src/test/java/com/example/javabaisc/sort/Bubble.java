package com.example.javabaisc.sort;

import org.junit.jupiter.api.Test;

import java.util.Arrays;


public class Bubble {

    @Test
    public void t() {

        int[] numbers = new int[]{1, 6, 4, 5, 2, 12, 33, 24};
//        /**
//         * 升序
//         */
//        Arrays.sort(numbers);
//        System.out.println(Arrays.toString(numbers));
//        /**
//         * 降序
//         */
//        //克隆，这样就不需要考虑数组长度了
//        int[] n2 = numbers.clone();
//        //首末数据颠倒
//        for (int i = 0; i < numbers.length; i++) {
//            n2[i] = numbers[numbers.length - 1 - i];
//        }
//        System.out.println(Arrays.toString(n2));


//        冒泡排序算法
        int i, j;
        /**
         * 升序
         */
        //第一层，只需要对比（元素总数-1）次即可
        for (i = 0; i < numbers.length - 1; i++) {
            //第二层，只需要对比（元素总数-1-已经对比过的次数）次即可
            for (j = 0; j < numbers.length - 1 - i; j++) {
                //升序：相邻的两个元素对比，当左边的比右边大时 ，需要互换位置
                if (numbers[j] > numbers[j + 1]) {
                    //使用临时变量辅助 两个元素互换位置
                    int temp = numbers[j];
                    numbers[j] = numbers[j + 1];
                    numbers[j + 1] = temp;
                }
            }
        }
        System.out.println(Arrays.toString(numbers));
        /**
         * 降序
         */
        //第一层，只需要对比（元素总数-1）次即可
        for (i = 0; i < numbers.length - 1; i++) {
            //第二层，只需要对比（元素总数-1-已经对比过的次数）次即可
            for (j = 0; j < numbers.length - 1 - i; j++) {
                //降序：相邻的两个元素对比，当左边的比右边小时 ，需要互换位置
                if (numbers[j] < numbers[j + 1]) {
                    //使用临时变量辅助 两个元素互换位置
                    int temp = numbers[j];
                    numbers[j] = numbers[j + 1];
                    numbers[j + 1] = temp;
                }
            }
        }
        System.out.println(Arrays.toString(numbers));

    }


}
