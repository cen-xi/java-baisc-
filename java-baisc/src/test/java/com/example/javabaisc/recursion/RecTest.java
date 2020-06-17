package com.example.javabaisc.recursion;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class RecTest {

    /**
     * N个台阶的走法递归
     * <p>
     * 有个楼梯，台阶有10个，每次可以跳上1阶 或者 2阶 ，那么台阶的走法一共有多少种
     */
    @Test
    public void t() {
        System.out.println(f(10));
    }

    //斐波那契数列变形，求N个台阶的走法，递归方法
    public int f(int n) {
        if (n <= 2) {
            return n;
        }
        return f(n - 1) + f(n - 2);
    }

//=========================================================================

    /**
     * 文件删除递归
     * <p>
     * 1:检测文件夹是否存在，不存在则退出
     * 2:获取该文件夹目录【获取文件数组】，遍历文件数组
     * 3:判断是文件则删除，不是则回调 2
     * 4.文件删除完后，将该文件夹删除
     */
    @Test
    public void t2() {
        this.delete(file);
    }

    //封装根目录
    static File file = new File("C:/Users/cen/Desktop/ww");

    public void delete(File f) {
        if (!f.exists()) {
            System.out.println("该文件夹不存在");
            return;
        }
        //获取该文件夹的目录文件数组
        File[] files = f.listFiles();
        //遍历
        for (File mf : files) {
            //如果是文件夹
            if (mf.isDirectory()) {
                //递归
                delete(mf);
            }
            //如果是文件
            else if (mf.isFile()) {
                //删除文件,方法.delete() 删除成功返回true ,失败为false
                System.out.println("文件" + mf.getName() + "被删除:" + mf.delete());
            }
        }
        System.out.println("最后操作完毕，已经是个空文件夹=" + f.getName() + "=啦，现在删除该空文件夹：" + f.delete());
    }
//=========================================================================

    /**
     * 文件查询递归
     * <p>
     * 1:检测文件夹是否存在，不存在则退出
     * 2:获取该文件夹目录【获取文件数组】，遍历文件数组
     * 3:判断是文件夹则回调 2  ，如果是是文件且文件名后缀是 .java 则打印绝对路径
     */
    @Test
    public void t3() {
        this.find(file2);
    }

    //封装根目录
    static File file2 = new File("C:/Users/cen/Desktop/ww");

    public void find(File f) {
        if (!f.exists()) {
            System.out.println("该文件夹不存在");
            return;
        }
        //获取该文件夹的目录文件数组
        File[] files = f.listFiles();
        //遍历
        if (files != null) {
            for (File mf : files) {
                //如果是文件夹
                if (mf.isDirectory()) {
                    //递归
                    find(mf);
                }
                //如果是文件 且文件名后缀是 .java 则打印觉得路径
                else if (mf.isFile() && mf.getName().endsWith(".java")) {
                    //删除文件,方法.delete() 删除成功返回true ,失败为false
                    System.out.println("文件" + mf.getName() + "绝对路径：" + mf.getAbsolutePath());
                }
            }
        }
    }
//=========================================================================

    /**
     * 使用递归方式计算 随机数 [1,10]的乘阶结果并打印过程
     */
    @Test
    public void t4() {
//        (数据类型)(最小值+Math.random()*(最大值-最小值+1))
        int i = (int) (1 + Math.random() * (10 - 1 + 1));
        System.out.println(i + "的乘阶是：" + this.mo(i));
        StringBuilder s = new StringBuilder();
        for (int n : list) {
            s.append(n).append("*");
        }
        int len = s.length();
        //去掉末尾的 *号
        //substrings是要包含末尾索引的字符的
        String str = s.substring(0, len - 1);
        System.out.println("乘阶过程是" + str);
    }

    //记录数字
    List<Integer> list = new ArrayList<>();

    public int mo(int n) {
        //记录数字
        list.add(n);
        if (n < 2) {
            //n为1
            return n;
        }
        //递归
        return n * mo(n - 1);
    }
//=========================================================================

    /**
     * 题目：有一对兔子，从出生后第三个月起每个月都生一对兔子 ，小兔子长到第三个月后每个月又生一对兔子，假如兔子都不死 ，问每个月的兔子总数为多少？
     * <p>
     * 解析：使用斐波拉契数列计算 ： 每一项都是前两项之和，一般使用递归方法计算
     * <p>
     * 兔子数列应该是 1，1，2，3，5，8，13，21，34 【注意：这里的单位是对 ，那么兔子数需要 * 2】
     */
    @Test
    public void t5() {
//    public static void main(String[] args) {
        //获取月份数
        int n = getMonth();
        System.out.println("当前输入月份是：" + n);
        //兔子对数
        int xx = tutu(n);
        System.out.println("当前兔子对数是:" + xx);
        System.out.println("当前共有" + 2 * xx + "只兔子");
    }

    //获取键盘数字递归
    public static int getMonth() {
        System.out.println("请输入月份【正整数】：");
        // 从键盘接收数据
        Scanner scanner = new Scanner(System.in);
        //判断是否有输入值
        if (scanner.hasNext()) {
            //接收数值
            String m = scanner.next();
            try {
                //字符串转整数
//                Integer x = Integer.valueOf(m);
                int x = Integer.parseInt(m);
                if (x > 0) {
                    return x;
                }
                return getMonth();
            } catch (Exception e) {
                return getMonth();
            }
        }
        return getMonth();
    }


    //斐波拉契数列递归
    public static int tutu(int n) {
        //前两个月不生兔子，还是一对
        if (n < 3) {
            return 1;
        } else {
            return tutu(n - 1) + tutu(n - 2);
        }
    }

    //=========================================================================

    /**
     * 判断101-200之间有多少个素数，并输出所有素数
     * 【1不是素数】因此最少2开始
     */
    //非递归方式求解
    @Test
    public void t6() {
        Map<Integer, Object> map = new HashMap<>();
        int n = 101;
        int j = 200;
        for (int i = n; i <= j; i++) {
            List list = new ArrayList();
            int k = 1;
            while (k < i) {
                //整除，余数为0
                if (i % k == 0) {
                    list.add(k);
                }
                k++;
            }
            if (list.size() == 1 && (Integer) list.get(0) == 1) {
                //只有一个因数 1 才可以
                map.put(i, list);
            }
        }
        //
        System.out.println("共有" + map.size() + "个素数");
        System.out.println("直接map打印，无序的");
        System.out.println(map.keySet());
        List<Integer> list2 = new ArrayList<>();
        //升序
        for (Integer x : map.keySet()) {
            list2.add(x);
        }
        System.out.println("lambda表达式排序[升序]");
        //lambda表达式排序
        list2.sort((n1, n2) -> n1 - n2);
        System.out.println(list2);
    }
    //=========================================================================

    /**
     * 打印出所有的 "水仙花数 "，所谓 "水仙花数 "是指一个三位数，其各位数字立方和等于该数本身。例如：153是一个 "水仙花数 "，因为153=1的三次方＋5的三次方＋3的三次方
     */
    //非递归方法求解
    @Test
    public void t7() {
        int n = 100;
        int j = 999;
        for (int i = n; i <= j; i++) {
            this.sxh(i);
        }
    }

    //整数
    int aaa, aa, a;

    public void sxh(int num) {
        //因为是整数型类，只会保留整数部分
        aaa = num / 100;
        //num%100取余数部分，然后除以10，再次取整数部分
        aa = (num % 100) / 10;
        a = num % 10;
        if (aaa * aaa * aaa + aa * aa * aa + a * a * a == num) {
            System.out.println(num);
        }

    }
    //=========================================================================

    /**
     * 将一个正整数分解 质因数 。例如：输入90,打印出90=2*3*3*5
     */
    @Test
    public void t8() {
        int n = 90;
        s.append(n).append("=");
        this.fjys(n);
        System.out.println(s);

    }

    StringBuilder s = new StringBuilder();

    int k = 2;

    public void fjys(int n) {
        if (k <= n) {
            if (k < n && n % k == 0) {
                //说明可以整除
                s.append(k).append("*");
                //剩下部分
                int y = n / k;
                //递归
                fjys(y);
            } else if (k < n && n % k != 0) {
                //说明不可以整除，不是因数
                //需要加一
                k++;
                //递归
                fjys(n);
            } else if (k == n) {
                //说明已经遍历到剩余数字本身，结束啦
                s.append(n);
            }
        }

    }
    //=========================================================================

    /**
     * 利用条件运算符的嵌套来完成此题：学习成绩> =90分的同学用A表示，60-89分之间的用B表示，60分以下的用C表示
     */
    @Test
    public void t9() {
        System.out.println("成绩93-》" + ys(93));
        System.out.println("成绩89-》" + ys(89));
        System.out.println("成绩35-》" + ys(35));
    }

    public char ys(int n) {
        //注意啦。必须使用单引号
        return n >= 90 ? 'A' : (n < 60 ? 'C' : 'B');
    }

    //=========================================================================

    /**
     * 输入两个正整数m和n，求其最大公约数和最小公倍数
     * <p>
     * 整数乘积 除以 最大公因数 则等于 最小公倍数
     */

    @Test
    public void t10() {
//    public static void main(String[] args) {
        Scanner s1 = new Scanner(System.in);
        System.out.println("请输入第一个正整数");
        int m = s1.nextInt();
        Scanner s2 = new Scanner(System.in);
        System.out.println("请输入第二个正整数");
        int n = s2.nextInt();
        if (n > m) {
            int t = m;
            m = n;
            n = t;
        }
        int bb = yb(n, m);
        System.out.println("最大公约数是：" + bb);
        System.out.println("最小公倍数是：" + m * n / bb);
    }

    //递归，计算最大共因素
    //参数 n 必须比m 小
    public static int yb(int n, int m) {
        int temp;
        if (m % n == 0) {
            temp = n;
        } else {
            //递归
            temp = yb(m % n, n);
        }
        return temp;
    }

    //=========================================================================

    /**
     * 输入一行字符，分别统计出其中 英文字母 、 空格 、 数字 和 其它字符 的个数
     */
    @Test
    public void t11() {
//    public static void main(String[] args) {
        Scanner s1 = new Scanner(System.in);
        System.out.println("请输入字符串");
        //.nextLine()表示输入一行字符，允许接收空格
        String m = s1.nextLine();
        System.out.println("字符总数：" + m.length());
        char[] ch = m.toCharArray();
        //英文字母
        int a = 0;
        //空格
        int b = 0;
        //数字
        int c = 0;
        //其它字符
        int d = 0;
        for (char n : ch) {
            //对比 ASCLL 码
            if (('a' <= n && n <= 'z') || ('A' <= n && n <= 'Z')) {
                a++;
            } else if (n == ' ') {
                b++;
            } else if ('0' <= n && n <= '9') {
                c++;
            } else {
                d++;
            }
        }
        System.out.println("英文字母" + a);
        System.out.println("空格" + b);
        System.out.println("数字" + c);
        System.out.println("其它字符" + d);


    }

    //=========================================================================

    /**
     * 求s=a+aa+aaa+aaaa+aa...a的值，其中a是一个数字。例如2+22+222+2222+22222(此时共有5个数相加)，几个数相加有键盘控制
     */
    @Test
    public void t12() {
//    public static void main(String[] args) {
        Scanner s1 = new Scanner(System.in);
        System.out.println("请输入一个数字");
        int a = s1.nextInt();
        if (a < 0) {
            System.out.println("不允许为负数");
            return;
        } else if (a == 0) {
            System.out.println("结果为0");
            return;
        }
        Scanner s2 = new Scanner(System.in);
        System.out.println("请输入相加的数字个数");
        int n = s2.nextInt();
        if (n < 0) {
            System.out.println("不允许为负数");
            return;
        } else if (n == 0) {
            System.out.println("结果为0");
            return;
        }
        //
        StringBuilder stringBuilder = new StringBuilder();
        int res = 0;
        //循环 n 加法运算
        for (int i = 1; i <= n; i++) {
            //存储字符串
            StringBuilder resstr = new StringBuilder();
            //拼接字符串
            for (int k = 0; k < i; k++) {
                resstr.append(a);
            }
            //记录
            stringBuilder.append(resstr).append("+");
            //字符串转整数后做加法运算
            res += Integer.parseInt(resstr + "");
        }
        //去掉末尾 + 号
        String f = stringBuilder.substring(0, stringBuilder.length() - 1);
        //打印
        System.out.println(f + "=" + res);
    }

    //=========================================================================

    /**
     * 一个数如果恰好等于它的因子之和，这个数就称为 "完数 "。例如6=1＋2＋3.编程找出1000以内的所有完数
     * <p>
     * 注意，这是因子，也叫因数 ，是指 -》整数a除以整数b(b≠0) 的商bai正好是整数而du没有余数，我们就说b是a的因数
     * 【90 = 10 * 9】
     * <p>
     * 与质因数【质因子】不同，【90=2*3*3*5】
     */
    @Test
    public void t13() {

        for (int i = 1; i < 1000; i++) {
            int count = 0;
            for (int j = 1; j <= i / 2; j++) {
                if (i % j == 0) {
                    count += j;
                }
            }
            if (count == i) {
                System.out.println(i);
            }
        }
    }

//=========================================================================

    /**
     * 一球从100米高度自由落下，每次落地后反跳回原高度的一半；再落下，求它在 第10次落地时，共经过多少米？第10次反弹多高？
     */
    @Test
    public void t14() {
        double h = 100.0;
        double s = 0;
        for (int i = 0; i < 10; i++) {
            System.out.print("第" + (i + 1) + "次落下时候高度:" + h + "米 ");
            s += h + h / 2;
            h = h / 2;
            System.out.print("反弹高度为" + h + "米 ");
            System.out.println("抵达反弹高度时球共经过" + s + "米");
        }
        s -= h;
        System.out.println("第10次落地时，共经过" + s + "米");
        System.out.println("第10次反弹" + h + "米");

    }
    //=========================================================================

    /**
     * 有1、2、3、4四个数字，能组成多少个互不相同且一个数字中无重复数字的三位数？并把他们都输入
     */
    @Test
    public void t15() {
        int[] arr = {1, 2, 3, 4};
        int count = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                for (int k = 0; k < arr.length; k++) {
                    if (arr[i] != arr[j] && arr[i] != arr[k] && arr[j] != arr[k]) {
                        int n = arr[i] * 100 + arr[j] * 10 + arr[k];
                        System.out.print(n+"  ");
                        count++;
                    }
                }
            }
        }
        System.out.println();
        System.out.println("总共个数：" + count);
    }
    //=========================================================================

    /**
     * 企业发放的奖金根据利润提成。利润(I)低于或等于10万元时，奖金可提10%；
     * 利润高于10万元，低于20万元时，低于10万元的部分按10%提成，高于10万元的部分，可可提成7.5%；
     * 20万到40万之间时，高于20万元的部分，可提成5%；
     * 40万到60万之间时高于40万元的部分，可提成3%；
     * 60万到100万之间时，高于60万元的部分，可提成1.5%，
     * 高于100万元时，超过100万元的部分按1%提成，
     * 从键盘输入当月利润，求应发放奖金总数？
     */
    @Test
    public void t16() {
//    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("请输入利润【正数】");
        double li = scanner.nextDouble();
        if (li < 0) {
            System.out.println("不可输入负数");
            return;
        }

        double m1, m2, m3, m4, m5;
        //10万
        m1 = 10 * 10000 * 0.1;
        //20万
        m2 = m1 + 10 * 10000 * 0.075;
        //40万
        m3 = m2 + 20 * 10000 * 0.05;
        //60万
        m4 = m3 + 20 * 10000 * 0.03;
        //100万
        m5 = m4 + 40 * 10000 * 0.015;

        double count = 0;
        if (li <= 10 * 10000) {
            count = 0.1 * li;
        } else if (li <= 20 * 10000) {
            count = m1 + (li - 10 * 10000) * 0.075;
        } else if (li <= 40 * 10000) {
            count = m2 + (li - 20 * 10000) * 0.05;
        } else if (li <= 60 * 10000) {
            count = m3 + (li - 40 * 10000) * 0.03;
        } else if (li <= 100 * 10000) {
            count = m4 + (li - 60 * 10000) * 0.015;
        } else {
            count = m5 + (li - 100 * 10000) * 0.01;
        }
        System.out.println("应发放奖金总数:" + count + "元");
    }

    //=========================================================================

    /**
     * 一个整数，它加上100后是一个完全平方数，再加上168又是一个完全平方数，请问该数是多少？
     */
    @Test
    public void t17() {
        boolean k = true;
        int i = 0;
        while (k == true) {
            int t = i + 100;
            //开平方根
            double c1 = Math.sqrt(t);
            //余数为0，说明平方根是整数，即该数为完全平方数
            if (c1 % 1 == 0) {
                int t2 = i + 168;
                //开平方根
                double c2 = Math.sqrt(t2);
                //余数为0，说明平方根是整数，即该数为完全平方数
                if (c2 % 1 == 0) {
                    System.out.println(i);
                    k = false;
                }
            }
            i++;
        }
    }

    //=========================================================================

    /**
     * 输入某年某月某日，判断这一天是这一年的第几天？
     */
    @Test
    public void t18() {
//    public static void main(String[] args) {
        //闰年
        int[] leapYear = {0, 31, 60, 91, 121, 152, 182, 213, 244, 274, 305, 335, 366};
        //平年
        int[] commonYear = {0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334, 365};
        Scanner input = new Scanner(System.in);
        System.out.print("请输入您要查询的年份:");
        int year = input.nextInt();
        System.out.print("请输入您要查询的月份:");
        int month = input.nextInt();
        System.out.print("请输入您要查询的天数：");
        int day = input.nextInt();
        int sumDays = 0;
        String type = "";
        if (month > 12 || month < 1) {
            System.out.println("请输入正确的月份!");
        } else {
            //判断是否为闰年
            //普通年能被4整除且不能被100整除的为闰年。（如2004年就是闰年,1900年不是闰年）
            //世纪年能被400整除的是闰年。(如2000年是闰年，1900年不是闰年)
//            闰年的二月为29天，平年的为28天
            if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                //闰年
                type = "闰年";
                //天数为31天的月份
                if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
                    //判断天数是否在31天之内
                    if (day < 1 || day > 31) {
                        System.out.println("请输入正确的天数!");
                    } else {
                        sumDays = leapYear[month - 1] + day;
                    }
                } else if (month == 2) {
                    //判断天数是否在29天之内
                    if (day < 1 || day > 29) {
                        System.out.println("请输入正确的天数!");
                    } else {
                        sumDays = leapYear[month - 1] + day;
                    }
                } else {//为4、6、9、11月中的一月
                    //判断天数是否在30天之内
                    if (day < 1 || day > 30) {
                        System.out.println("请输入正确的天数!");
                    } else {
                        sumDays = leapYear[month - 1] + day;
                    }
                }
            } else {
                //为平年
                type = "平年";
                //天数为31天的月份
                if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
                    //判断天数是否在31天之内
                    if (day < 1 || day > 31) {
                        System.out.println("请输入正确的天数!");
                    } else {
                        sumDays = commonYear[month - 1] + day;
                    }
                } else if (month == 2) {
                    //判断天数是否在28天之内
                    if (day < 1 || day > 28) {
                        System.out.println("请输入正确的天数!");
                    } else {
                        sumDays = commonYear[month - 1] + day;
                    }
                } else {//为4、6、9、11月中的一月
                    //判断天数是否在30天之内
                    if (day < 1 || day > 30) {
                        System.out.println("请输入正确的天数!");
                    } else {
                        sumDays = commonYear[month - 1] + day;
                    }
                }
            }
        }
        System.out.println("这一年是" + type + ",这一天为一年中的第" + sumDays + "天");
    }

    //=========================================================================

    /**
     * 输入三个整数x,y,z，请把这三个数由小到大输出
     */
    @Test
    public void t19() {
//    public static void main(String[] args) {
        System.out.println("输入三个整数x,y,z");
        Scanner s1 = new Scanner(System.in);
        System.out.println("x=");
        int x = s1.nextInt();
        System.out.println("y=");
        int y = s1.nextInt();
        System.out.println("z=");
        int z = s1.nextInt();
        List<Integer> list = new ArrayList<>();
        list.add(x);
        list.add(y);
        list.add(z);
        //lamda排序，升序
        list.sort((a, b) -> a - b);
        System.out.println(list);
    }

    //=========================================================================

    /**
     * 输出9*9口诀
     */
    @Test
    public void t20() {
        for (int i = 1; i <= 9; i++) {
            for (int j = 1; j <= i; j++) {
                System.out.print(i + "*" + j + "=" + i * j + "  ");
            }
            System.out.println();
        }
    }
    //=========================================================================

    /**
     * 猴子吃桃问题：猴子第一天摘下若干个桃子，当即吃了一半，还不瘾，又多吃了一个 第二天早上又将剩下的桃子吃掉一半，又多吃了一个
     * 。以后每天早上都吃了前一天剩下 的一半零一个。到第10天早上想再吃时，见只剩下一个桃子了。求第一天共摘了多少
     * <p>
     * [也就是说，一共吃了9天]
     */

    @Test
    public void t21() {
        System.out.println(tao(1));
    }

    //吃的总次数
    int times = 9;

    //递归
    public int tao(int n) {
        if (times > 0) {
            times--;
            n += 1;
            n = n * 2;
            n = tao(n);
        }
        return n;
    }

    //=========================================================================

    /**
     * 两个乒乓球队进行比赛，各出三人。甲队为a,b,c三人，乙队为x,y,z三人。已抽签决定比赛名单。
     * 有人向队员打听比赛的名单。a说他不和x比，c说他不和x,z比，请编程序找出三队赛手的名单
     * <p>
     * //注意，是三队
     */

    @Test
    public void t22() {
        char[] n = {'x', 'y', 'z'};
        for (char a : n) {
            for (char b : n) {
                for (char c : n) {
                    //取出乙队三人不重复
                    if (a != b && a != c && b != c) {
                        //当 a不是x ,c 不是 x、z 时
                        if (a != 'x' && c != 'x' && c != 'z') {
                            System.out.println("a<-->" + a);
                            System.out.println("b<-->" + b);
                            System.out.println("c<-->" + c);
                        }
                    }
                }
            }
        }
    }
    //=========================================================================

    /**
     * 打印出如下图案（菱形）
     * <p>
     * 好难
     */
    @Test
    public void t23() {
        //边长
        int s = 5;
        //
        int a = s + 1;//6
        int b = s - 1;//4
        int c = 2 * b + a;//14
        //行数/列数
        int d = s * 2 - 1;//9
        //每行
        for (int i = 1; i <= d; i++) {
            //上半部分
            if (i <= b) {
                //每列
                for (int j = 1; j <= d; j++) {
                    if (j == a - i || j == b + i) {
                        System.out.print(" * ");
                    } else {
                        System.out.print("   ");
                    }
                }
            }
            //下半部分
            else {
                //每列
                for (int k = 1; k <= d; k++) {
                    if (k == i - b || k == c - i) {
                        System.out.print(" * ");
                    } else {
                        System.out.print("   ");
                    }
                }
            }
            System.out.println();
        }
    }

    //=========================================================================

    /**
     * 有一分数序列：2/1，3/2，5/3，8/5，13/8，21/13...求出这个数列的前20项之和
     */
    @Test
    public void t24() {

        int x = 2;
        int y = 1;
        int z ;
        double sum = 0;
        for (int i = 0; i < 20; i++) {
            sum += (double) x / y;
            //备份分母
            z = y;
            //新的分母是前一个的分子
            y = x;
            //新的分子是前一个分子加分母，与 x = x + z; 一样
            x = y + z;
        }
        System.out.println(sum);


    }



}