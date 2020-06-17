package com.example.javabaisc.nio.socket;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * //https://blog.csdn.net/valada/article/details/96040288
 */

public class NIOClient {
    public static void main(String[] args) {

        //执行10个线程
        for (int i = 0; i < 1; i++) {
            new Worker().start();
        }
    }

    static class Worker extends Thread {
        @Override
        public void run() {
            //socket管道
            SocketChannel channel = null;
            //选择器，多路复用，其实就是一直循环，做轮询操作，
            Selector selector = null;
            try {
                //SocketChannel, 一看底层就是封装了一个Socket
                //开启socket 管道
                channel = SocketChannel.open();
                // SocketChannel是连接到底层的Socket网络
                //数据通道就是负责基于网络读写数据的
                //关闭自定义配置块
                channel.configureBlocking(false);
                //管道连接互联网socket地址，输入ip和端口号
                channel.connect(new InetSocketAddress("localhost", 8080));
                // 后台一定是tcp三次握手建立网络连接
                System.out.println("Selector.open(前==="+System.currentTimeMillis());
                //-----1
                Thread.sleep(1);
                //开启选择器
                selector = Selector.open();
                //监听Connect这个行为
                //SelectionKey.OP_CONNECT ——连接就绪事件，表示客户与服务器的连接已经建立成功，同通知服务端回应我
                //将管道注册到选择器上，设该管道为对连接操作感兴趣【是让服务端感兴趣】
                System.out.println("OP_CONNECT==前==="+System.currentTimeMillis());
                //----------3
                Thread.sleep(1);
                channel.register(selector, SelectionKey.OP_CONNECT);
                System.out.println("OP_CONNECT==后==="+System.currentTimeMillis());
                //-----------------4
                Thread.sleep(1);
                //一直循环，不退出。。。死循环
                while (true) {
                    // selector多路复用机制的实现 循环去遍历各个注册的Channel
                    //
                    //该方法会阻塞等待，直到有一个或更多的信道准备好了I/O操作或等待超时。select()方法将返回可进行I/O操作的信道数量。
                    // 现在，在一个单独的线程中，通过调用select()方法就能检查多个信道是否准备好进行I/O操作。如果经过一段时间后仍然没有信道准备好，
                    // select()方法就会返回0，并允许程序继续执行其他任务。
                    //阻塞到至少有一个通道在你注册的事件上就绪了
                    selector.select();
                    //迭代器，遍历所有选择器的key值
                    Iterator<SelectionKey> keysIterator = selector.selectedKeys().iterator();
                    System.out.println("遍历一次");
                    //判断是否存在下一个元素，有则执行代码块
                    while (keysIterator.hasNext()) {
                        System.out.println("这次遍历有东西");
                        //获取每一个key值 ，指针下移，返回该指针所指向的元素
                        SelectionKey key = (SelectionKey) keysIterator.next();
                        //删除当前next方法返回的值，也就是上一句指令的key，实际上是删除迭代器指向的选择器元素
                        keysIterator.remove();
                        /*
                         *也就是说，每次选择器使用迭代器遍历自身元素，将元素取出作为临时变量后，
                         * 就会立刻删除迭代器指向的元素，防止二次被遍历使用，
                         * 同时自身还可接收新元素，但是需要下一次遍历才会使用，
                         * 新建的元素都是接收服务端的，客户端管道注册的监听器是发送统给服务端的，不会混乱
                         *
                         */

                        /**
                         *   socket首次握手成功返回的消息，被服务端通知连接成功，在这里做操作
                         */
                        if (key.isConnectable()) {
                            //---------5
                            System.out.println("=========================================================");
                            System.out.println("socket首次握手成功返回的消息==="+System.currentTimeMillis());
                            Thread.sleep(1);
                            //key获取管道后强转成socket管道
                            channel = (SocketChannel) key.channel();
                            //如果管道是连接悬挂
                            if (channel.isConnectionPending()) {
                                //每次连接成功后都行执行这里
                                //
                                System.out.println("如果管道是连接悬挂==="+System.currentTimeMillis());
                                //管道结束连接
                                channel.finishConnect();
                                //   接下来对这个SocketChannel感兴趣的就是人家server给你发送过来的数据了
                                //  READ事件，就是可以读数据的事件
                                //  一旦建立连接成功了以后，此时就可以给server发送一个请求了
                                //   构建一个缓冲区
                                ByteBuffer buffer = ByteBuffer.allocate(1024);
                                //=============
                                Map<String,Object>map = new HashMap<>();
                                map.put("type",0);
                                map.put("date","socket首次握手成功，你好");
                                String jsonstr = JSON.toJSONString(map);
                                buffer.put(jsonstr.getBytes());
                                //=============
//                                buffer.put("你好".getBytes());
                                buffer.flip();
                                channel.write(buffer);
                            }
//                            SelectionKey.OP_READ  —— 读就绪事件，表示通道中已经有了可读的数据，可以执行读操作了（通道目前有数据，可以进行读操作了）
                            //将管道注册到选择器上，设该管道为对读操作感兴趣【是让服务端感兴趣】
                            channel.register(selector, SelectionKey.OP_READ);
                        }
                        /**
                         *   接收到服务端给我发的消息，在这里做操作
                         */
                        else if (key.isReadable()) {
                            //----------8
                            System.out.println("=========================================================");
                            System.out.println("接收到服务端给我发的消息==="+System.currentTimeMillis());
                            Thread.sleep(1);
                            channel = (SocketChannel) key.channel();
                            //   构建一个缓冲区
                            ByteBuffer buffer = ByteBuffer.allocate(1024);
                            // 把数据写入buffer，position推进到读取的字节数数字
                            int len = channel.read(buffer);
                            if (len > 0) {
                                System.out.println("[" + Thread.currentThread().getName()
                                        + "]收到响应：" + new String(buffer.array(), 0, len));
//                                Thread.sleep(5000);
                                //SelectionKey.OP_WRITE —— 写就绪事件，表示已经可以向通道写数据了（通道目前可以用于写操作）
                                //将管道注册到选择器上，设该管道为对写操作感兴趣【是让自己感兴趣】
                                channel.register(selector, SelectionKey.OP_WRITE);
                            }
                            /**
                             *   接收到服务端通知，告诉我可以给他写消息，在这里做操作
                             */
                        } else if (key.isWritable()) {
                            //-------------9
                            System.out.println("=========================================================");
                            System.out.println("接收到服务端通知，告诉我可以给他写消息==="+System.currentTimeMillis());
                            Thread.sleep(5000);
                            ByteBuffer buffer = ByteBuffer.allocate(1024);
                            //=============
                            Map<String,Object>map = new HashMap<>();
                            map.put("type",1);
                            map.put("date","love,kk");
                            String jsonstr = JSON.toJSONString(map);
                            buffer.put(jsonstr.getBytes());
                            //=============
//                            buffer.put("你好".getBytes());
                            buffer.flip();
                            channel = (SocketChannel) key.channel();
                            channel.write(buffer);
                            //将管道注册到选择器上，设该管道为对读操作感兴趣【是让服务端感兴趣】
                            channel.register(selector, SelectionKey.OP_READ);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                //管道如果不空，关闭管道
                if (channel != null) {
                    try {
                        channel.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                //选择器如果不空，关闭选择器
                if (selector != null) {
                    try {
                        selector.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


}
