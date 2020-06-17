package com.example.javabaisc.nio;


import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Scanner;

/**
 * noi操作
 */
public class Nio {
    /**
     * 把字符串写入文件[使用  buffer.put]
     */
    @Test
    public void w1() {
        try {
            //=================================
//            //new文件对象
//            File file = new File("C:/Users/cen/Desktop/ww/", "write.txt");
//            //字节输出流，意思是指JVM的内容输出到文件对象里面
//            //如果文件存在 覆盖
//            FileOutputStream fos = new FileOutputStream(file);
            //
            //上面几句等同于下面一句
            FileOutputStream fos = new FileOutputStream("C:/Users/cen/Desktop/ww/write.txt");
            //==============================================
            //true表示追加，如果文件存在 向里面继续添加内容，不加默认是覆盖
//            FileOutputStream fos=new FileOutputStream(file,true);
            //获取通道，该通道允许写操作【根据字节流是输入还是输出决定是读通道还是写通道】
            FileChannel fc = fos.getChannel();
            //
//            System.out.println("通道数量====" + fc.size());
            //打印的结果是0
            //
            //allocate是使用JVM的的内存的，适合小文件；allocateDirect则是使用系统的内存的，适合大文件
            //设置字节缓的大小
            ByteBuffer buffer = ByteBuffer.allocate(1024);
//            ByteBuffer.allocateDirect()
            //控制台写入字符串
//            Scanner sca = new Scanner(System.in);
//            String str = sca.next();
            // \n 转行符算是一个字符
            String str = "sa34523ui2345672\n" +
                    "kkkkuuuushfjsdhjkfhs222333444555\n" +
                    "撒大噶和\n" +
                    "稍等哈肯定会介绍的几款圣诞卡十大科技";
            //将字符串转成字节后，放入字节缓冲
            buffer.put(str.getBytes(StandardCharsets.UTF_8));
            // 此行语句一定要有,每次执行了这一句才可以做io操作
            buffer.flip();
            //通道对字节缓冲区里的内容写入文件对象里面
            fc.write(buffer);
            System.out.println("写入成功");
            //清空缓冲块
            buffer.clear();
            //关闭管道
            fc.close();
            //关闭字节输出流
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 把字符串写入文件[使用 ByteBuffer.wrap ,不设置缓冲区大小]
     *
     * @throws Exception
     */
    @Test
    public void w2() throws Exception {
//        构造一个传统的文件输出流
        FileOutputStream out = new FileOutputStream("C:/Users/cen/Desktop/ww/write.txt");
//        通过文件输出流获取到对应的FileChannel，以NIO的方式来写文件
        FileChannel channel = out.getChannel();
//        将数据写入到Buffer中
        ByteBuffer buffer = ByteBuffer.wrap("hello world".getBytes());
//        通过FileChannel管道将Buffer中的数据写到输出流中去，持久化到磁盘中去
        channel.write(buffer);
        channel.close();
        out.close();
    }

    /**
     * 从文件中一次性读取字符串
     */
    public static void main(String[] args) throws Exception {
        //==============================================
        //new文件对象
        File file = new File("C:/Users/cen/Desktop/ww/write2.txt");
        //字节输入流
        //意思是文件内容向JVM输入
        FileInputStream fis = new FileInputStream(file);
        //
        //上面几句等同于下面一句
//        FileInputStream fis = new FileInputStream("C:/Users/cen/Desktop/ww/write.txt");
        //==============================================
        //
        //开启管道
        FileChannel fc = fis.getChannel();
        //设置字节缓冲区的大小，这里设为文件对象的字节长度
//        ByteBuffer buffer = ByteBuffer.allocate((int) file.length());
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //管道将文件对象的内容读出并放入缓冲区里面，字符上限为上面缓冲区设置的最大值
        //read()方法返回的是个抽象的整数，意为字符的长度/个数，当为空时返回-1
        int nread = fc.read(buffer);
//        System.out.println(buffer);
        System.out.println("抽象整数==" + nread);
//        buffer.array()是缓冲区获取底层byte[]数组，
//        然后使用 new String 将字节转成字符串
        System.out.println(new String(buffer.array()));
        //清除缓冲区
        buffer.clear();
        //关闭管道
        fc.close();
        //关闭字节输入流
        fis.close();

    }

    /**
     * 多次读取文件数据--解决utf-8中文乱码问题
     */
    @Test
    public void readutf8Data() throws Exception {
        //==============================================
        //new文件对象
        File file = new File("C:/Users/cen/Desktop/ww/write.txt");
        //字节输入流
        //意思是文件内容向JVM输入
        FileInputStream fis = new FileInputStream(file);
        //==============================================
        //
        //开启管道
        FileChannel fc = fis.getChannel();
        //如果设置太小的，会乱码【解决方式不靠谱】
        //如果是utf-8，最大长度是4字节  ，GBK 为2 byte
        //因此这里设置最小是4
        ByteBuffer buffer = ByteBuffer.allocate(6);
        //管道将文件对象的内容读出并放入缓冲区里面，字符上限为上面缓冲区设置的最大值
        //read()方法返回的是个抽象的整数，意为字符的长度/个数，当为空时返回-1
        int mread;
        //此时管道读取数据放入缓冲，缓冲的定位position 不再是0 ，需要将limit设为position,而position需要恢复到0 ，
        //形成 【 limit == 当前的position值【也等于mread】，position == 0 , cap 不变】
        while ((mread = fc.read(buffer)) != -1) {

            byte b;
            //计算出此时中文utf-8的编码长度后放入这里
            int idx;

            //out可以bai改成任何不与保留关键字相同的字符，其中du标签out代表了这个for循环结构zhi体。理论上，标签可以标记任何结构体
            //break out ;代表语句执行跳出整个for循环结构
            out:
            for (idx = buffer.position() - 1; idx >= 0; idx--) {
                b = buffer.get(idx);
                if ((b & 0xff) >> 7 == 0) {  // 0xxxxxxx
                    break;
                }

                if ((b & 0xff & 0xc0) == 0xc0) {   // 11xxxxxx，110xxxxx、1110xxxx、11110xxx
                    idx -= 1;
                    break;
                }
                if ((b & 0xff & 0x80) == 0x80) {
                    for (int i = 1; i < 4; i++) {
                        b = buffer.get(idx - i);
                        if ((b & 0xff & 0xc0) == 0xc0) {
                            if ((b & 0xff) >> (5 + 1 - i) == 0xf >> (3 - i)) {
                                //break out ;代表语句执行跳出整个for循环结构
                                break out;
                            } else {
                                idx = idx - 1 - i;
                                //break out ;代表语句执行跳出整个for循环结构
                                break out;
                            }
                        }
                    }
                }
            }

            //缓冲区定位参数移位
            buffer.flip();
            //获取限制数，备份
            int limit = buffer.limit();
            //设限制数为上面计算出的utf-8字符的长度
            buffer.limit(idx + 1);  // 阻止读取跨界数据
            //
            System.out.println(Charset.forName("UTF-8").decode(buffer).toString());
            // 恢复limit
            buffer.limit(limit);
            //compact()方法将所有未读的数据拷贝到Buffer起始处。然后将position设到最后一个未读元素正后面。
            // limit属性依然像clear()方法一样，设置成capacity。现在Buffer准备好写数据了，但是不会覆盖未读的数据。
            //也就是说把已经写入缓冲区但是还没有读的数据左移到开头，然后将position设为该数据默认
            //然后继续做写操作，不会覆盖这些数据
            buffer.compact();
        }
        //清除缓冲区
        buffer.clear();
        //关闭管道
        fc.close();
        //关闭字节输入流
        fis.close();
    }


    /**
     * 文件复制 【如果已经存在则覆盖，因此需要提前判断是否存在】
     */
    @Test
    public void copyFile() throws Exception {
        //分别创建流
        //字节输入流，用于从文件读取数据，即原地址文件【被拷贝的文件】
        FileInputStream fis = new FileInputStream("C:/Users/cen/Desktop/ww/write.txt");
        //字节输出流，用于从将数据输出到文件里，即目标地址文件【拷贝文件】
        FileOutputStream fos = new FileOutputStream("C:/Users/cen/Desktop/ww/write_copy.txt");
        //
        //分别开启通道
        FileChannel sourceFC = fis.getChannel();
        FileChannel destFC = fos.getChannel();
        //
        //复制 【一个主动一个被动】
        //从哪里转移到这里 【参数 0 ，其实是管道的数量，等同于 destFC.size()】
        destFC.transferFrom(sourceFC, 0, sourceFC.size());
        //从这里转移到哪里
//        sourceFC.transferTo(destFC,0,sourceFC.size());
        //
        //关闭通道
        sourceFC.close();
        destFC.close();
        //
//        关闭字节输入流
        fis.close();
        fos.close();

    }


    //gatter聚集
    //奇怪，不显示文字
    @Test
    public void w3() throws Exception {
////        构造一个传统的文件输出流
//        FileOutputStream out = new FileOutputStream("C:/Users/cen/Desktop/ww/write2.txt");
////        通过文件输出流获取到对应的FileChannel，以NIO的方式来写文件
//        FileChannel channel = out.getChannel();
////        将数据写入到Buffer中
////        ByteBuffer buffer = ByteBuffer.wrap("hello world".getBytes());
//        ByteBuffer b1 = ByteBuffer.allocate(20);
//        ByteBuffer b2 = ByteBuffer.allocate(20);
//        byte [] y1 = {'0', '1'};
//        byte [] y2 = {'0', '1'};
//        b1.put(y1);
//        b2.put(y2);
//        ByteBuffer[] buffer = {b1,b2};
//
////        通过FileChannel管道将Buffer中的数据写到输出流中去，持久化到磁盘中去
//        channel.write(buffer);
//        channel.close();
//        out.close();

        ByteBuffer header = ByteBuffer.allocate(10);
        ByteBuffer body = ByteBuffer.allocate(10);
        byte [] b1 = {'0', '1'};
        byte [] b2 = {'2', '3'};
        header.put(b1);
        body.put(b2);
        ByteBuffer [] buffs = {header, body};
        try
        {
            FileOutputStream os = new FileOutputStream("C:/Users/cen/Desktop/ww/write2.txt");
            FileChannel channel = os.getChannel();
            channel.write(buffs);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


}

/*
总结：
1.io操作包括 socket io ,file io ;
2.在nio模型，file io使用fileChannel 管道 ，socket io 使用socketChannel管道，
3.在file io可以使用transferTo 或  transferFrom 实现管道向管道的的数据传输，但是别人说有可能传输数据不完成，不建议这样做；
4.GBK 为2 byte,如果是utf-8，最大长度是4字节  ,是可变的，需要循环判断获取字节长度后设置limit值才能获取完成的utf-8编码，否则会乱码，
5. buffer.compact();将所有未读的数据拷贝到Buffer起始处。然后将position设到最后一个未读元素正后面。limit属性依然像clear()方法一样
    ，设置成capacity。现在Buffer准备好写数据了，但是不会覆盖未读的数据。也就是说把已经写入缓冲区但是还没有读的数据左移到开头，
    然后将position设为该数据默认然后继续做写操作，不会覆盖这些数据
6. buffer.clear();调用的是clear()方法，position将被设回0，limit被设置成 capacity的值。换句话说，Buffer 被清空了。Buffer中的数据并未清除，
    只是这些标记告诉我们可以从哪里开始往Buffer里写数据。如果Buffer中有一些未读的数据，调用clear()方法，数据将“被遗忘”，
    意味着不再有任何标记会告诉你哪些数据被读过，哪些还没有。
7. buffer.flip();重置position，limit，capacity， 也就是limit= position，position=0，capacity=设好的最大值，
8. buffer.position();缓冲区的有定位位置 ，一般是position值小于limit值，否则无数据
9. buffer.limit(); 缓冲区的有限制位置 ，一般是position值小于limit值，否则无数据
10. 不论是读取还是写入，都是position 于limit之间的位置操作
11. buffer.capacity();缓冲区的最大容量 ，由ByteBuffer.allocate(【int】) 设置
12. buffer.hasRemaining();是否还有有效数据 ，即position值小于limit值
13.buffer缓冲区内部是字节流，使用buffer.array()从 缓冲区获取position 于limit之间的底层字节数组，然后使用 new String将字节数组转字符串
 */