package com.example.javabaisc.nio.mysocket;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ClientSocket {

    //用户名
    String username = "cen";


    @Test
    //单元测试方法启动
    public void clientSocket() throws IOException {
        //配置选择器
        selector();
        //监听
        listen();
    }

    //选择器作为全局属性
    private Selector selector = null;


    /**
     * 配置选择器
     * 如果使用 main 启动 ，那么 selector() 需要设为静态，因为main 函数是static的，都在报错
     */
    private void selector() throws IOException {
        //信道
        SocketChannel channel = null;
        //开启选择器
        selector = Selector.open();
        //开启信道
        channel = SocketChannel.open();
        //把该channel设置成非阻塞的，【需要手动设置为false】
        channel.configureBlocking(false);
        //管道连接互联网socket地址，输入ip和端口号
        channel.connect(new InetSocketAddress("localhost", 8080));
        //管道向选择器注册信息----连接就绪
        channel.register(selector, SelectionKey.OP_CONNECT);
    }

    /**
     * 写了监听事件的处理逻辑
     */
    private void listen() throws IOException {
        out:
        //进入无限循环遍历
        while (true) {
            //这个方法是阻塞的，是用来收集有io操作通道的注册事件【也就是选择键】，需要收到一个以上才会往下面执行，否则一直等待到超时，超时时间是可以设置的，
            //直接输入参数数字即可，单位毫秒 ，如果超时后仍然没有收到注册信息，那么将会返回0 ，然后往下面执行一次后又循环回来
            //不写事件将一直阻塞下去
//                selector.select();
            //这里设置超时时间为3000毫秒
            if (selector.select(3000) == 0) {
                //如果超时后返回结果0，则跳过这次循环
                continue;
            }
            //使用迭代器遍历选择器里的所有选择键
            Iterator<SelectionKey> ite = selector.selectedKeys().iterator();
            //当迭代器指针指向下一个有元素是才执行内部代码块
            while (ite.hasNext()) {
                //获取选择键
                SelectionKey key = ite.next();
                //选择键操作完成后，必须删除该元素【选择键】，否则仍然存在选择器里面，将会在下一轮遍历再执行一次，形成了脏数据，因此必须删除
                ite.remove();
                //当选择键是可连接的
                if (key.isConnectable()) {
                    if ( connectableHandler(key)) {
                        System.out.println("//远程主机未上线，退出循环，关闭客户端");
                        //退出循环，关闭客户端
                        break out;
                    }
                }
                //当选择键是可读的
                else if (key.isReadable()) {
                    if (readHandler(key)) {
                        System.out.println("//远程主机强迫关闭了连接。退出循环，关闭客户端");
                        //退出循环，关闭客户端
                        break out;
                    }
                }
                //当选择键是可写的且是有效的【其实是自己通知自己触发的，我不明白为什么要有一个分开的感兴趣事件，
                // 因为响应客户端直接在读操作后直接做写操作，然后注册读就绪事件就行了，没必要分开放在这里写啊】
                //为了演示我还是写了
                else if (key.isWritable() && key.isValid()) {
                    writeHandler(key);
                }
            }
        }
    }

    //当选择键是可连接的处理逻辑
    //static静态，可用可不用
    private boolean connectableHandler(SelectionKey key) throws IOException {
        System.out.println("当选择键是可连接的处理逻辑");
        SocketChannel sc =null;
        /*
        每当连接远程主机发现未上线，则会在这里报异常
        java.net.ConnectException: Connection refused: no further information
         */
        try {
             sc = (SocketChannel) key.channel();
            //如果管道是连接悬挂
            if (sc.isConnectionPending()) {
                //管道结束连接
                sc.finishConnect();
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                //=============
                Map<String, Object> map = new HashMap<>();
                map.put("type", 0);
                map.put("date", "socket首次握手成功，你好");
                map.put("username", username);
                String jsonstr = JSON.toJSONString(map);
                //清除索引信息【即position = 0 ；capacity = limit】
                buffer.clear();
                //指定编码将符串转字字节流
                buffer.put(jsonstr.getBytes(StandardCharsets.UTF_8));
                //定位到有效字符的索引【即limit = position ,position = 0 ,capacity 不变】
                buffer.flip();
                //如果 position < limit ，即仍有缓冲区的数据未写到信道中
                while (buffer.hasRemaining()) {
                    //写操作
                    sc.write(buffer);
                }
                sc.register(selector, SelectionKey.OP_READ);
            }
            return false;
        }catch (Exception e){
//            e.printStackTrace();
            //取消该选择键
            key.channel();
            //发生异常才关闭
            if (sc != null) {
                sc.close();
            }
            return true;
        }

    }

    //当选择键是可读的处理逻辑
    private boolean readHandler(SelectionKey key) throws IOException {
        SocketChannel sc = null;
        /*
        每当服务端强制关闭了连接，就会发送一条数据过来这里说
        java.io.IOException: 远程主机强迫关闭了一个现有的连接。
        因此需要这里销毁连接，即关闭该socket通道即可
         */
        try {
            System.out.println("当选择键是可读的处理逻辑");
            //获取信道,需要强转
            sc = (SocketChannel) key.channel();
            //key 获取 通道 关联的 缓冲区【这里使用是报错,read读操作报空指针异常，奇了怪了】
            // ByteBuffer buffer = (ByteBuffer) key.attachment();
            //只能自定义了
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            //获取选择器
            Selector mselector = key.selector();
            //信道做读操作 ,返回读取数据的字节长度
            long mreadSize;
            //存储从心得解析出来的字符串
            String jsonstr = "";
            //当字节长度大于零，说明还有信息没有读完
            while ((mreadSize = sc.read(buffer)) > 0) {
                System.out.println("=======");
                //定位到有效字符的索引【即limit = position ,position = 0 ,capacity 不变】
                buffer.flip();
                //获取该索引间的数据 ,buffer.get()返回的是节数
                byte[] b = buffer.array();
                //指定编码将字节流转字符串
                jsonstr = new String(b, StandardCharsets.UTF_8);
                //打印
                System.out.println(jsonstr);
            }
            //当字节长度为-1时，也就是没有数据可读取了，那么就关闭信道
            if (mreadSize == -1) {
                sc.close();
            }
            //检查字符串是否为空
            if (!jsonstr.isEmpty()) {
                //数据发送过来不为空
                //进入业务层 【与服务端的一样写法，我这里就演示服务层了】
//                eatService.food(mselector, sc, buffer, jsonstr);
                //为了演示响应，我直接用做写就绪事件响应
                //注册写就绪事件 ,这句话等同于 直接调用 writeHandler(SelectionKey key)
                sc.register(mselector, SelectionKey.OP_WRITE);

            }

            return false;
        } catch (Exception e) {
//            e.printStackTrace();
            //取消该选择键
            key.channel();
            //发生异常才关闭
            if (sc != null) {
                sc.close();
            }
            //关闭客户端
            return true;
        }
    }

    //当选择键是可写的且是有效的处理逻辑
    private void writeHandler(SelectionKey key) throws IOException {
        System.out.println("当选择键是可写的且是有效的处理逻辑 ，我被自己通知来写东西啦，虽然不知道为什么要分开读写");
        //获取信道,需要强转
        SocketChannel sc = (SocketChannel) key.channel();
        //设置字节缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //=============
        Map<String, Object> map = new HashMap<>();
        map.put("type", 3);
        map.put("date", "我要写东西，你看到了吗" + System.currentTimeMillis());
        String jsonstr = JSON.toJSONString(map);
        //清除索引信息【即position = 0 ；capacity = limit】
        buffer.clear();
        //将字符转成字节流放入缓冲中
        buffer.put(jsonstr.getBytes(StandardCharsets.UTF_8));
        //定位到有效字符的索引【即limit = position ,position = 0 ,capacity 不变】
        buffer.flip();
        //如果 position < limit ，即仍有缓冲区的数据未写到信道中
        while (buffer.hasRemaining()) {
            //信道做写操作
            sc.write(buffer);
        }
        //整理索引【即position定位到缓冲区未读的数据末尾 ，capacity = limit】
        buffer.compact();
        //获取选择器
        Selector mselector = key.selector();
        //注册读就绪事件
        sc.register(mselector, SelectionKey.OP_READ);

    }

}
