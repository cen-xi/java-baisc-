package com.example.javabaisc.nio.mysocket;

import com.example.javabaisc.nio.mysocket.service.EatService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ServerSocket {


    //main方法启动
//    public static void main(String[] args) throws IOException {
//        //配置选择器
//        selector();
//        //监听
//        listen();
//    }
    @Test
    //单元测试方法启动
    public void serverSocket() throws IOException {
        //配置选择器
        selector();
        //监听
        listen();
    }

    //服务层接口
    @Autowired
    private EatService eatService;


    //选择器作为全局属性
    private Selector selector = null;

    //存储信道对象 ,静态公用的全局变量
    //key是ip和端口，如 /127.0.0.1:64578
    //value 是 储信道对象
    private static final ConcurrentMap<String, SocketChannel> socketChannelMap = new ConcurrentHashMap<>();
    //存储ip和端口
    // key 是用户名
    // value 是ip和端口，如 /127.0.0.1:64578
    private static final ConcurrentMap<String, String> ipMap = new ConcurrentHashMap<>();
    //存储用户名
    // key 是ip和端口
    // value 是用户名
    private static final ConcurrentMap<String, String> usernameMap = new ConcurrentHashMap<>();

    /**
     * 配置选择器
     * 如果使用 main 启动 ，那么 selector() 需要设为静态，因为main 函数是static的，都在报错
     */
    private void selector() throws IOException {
        //服务信道
        ServerSocketChannel channel = null;
        //开启选择器
        selector = Selector.open();
        //开启服务信道
        channel = ServerSocketChannel.open();
        //把该channel设置成非阻塞的，【需要手动设置为false】
        channel.configureBlocking(false);
        //开启socket 服务,由信道开启，绑定端口 8080
        channel.socket().bind(new InetSocketAddress(8080));
        //管道向选择器注册信息----接收连接就绪
        channel.register(selector, SelectionKey.OP_ACCEPT);

    }

    /**
     * 写了监听事件的处理逻辑
     */
    private void listen() throws IOException {
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
                //当选择键是可接受的
                if (key.isAcceptable()) {
                    acceptableHandler(key);
                }
                //当选择键是可读的
                else if (key.isReadable()) {
                    readHandler(key);
                }
                //当选择键是可写的且是有效的【其实是自己通知自己触发的，我不明白为什么要有一个分开的感兴趣事件，
                // 因为响应客户端直接在读操作后直接做写操作，然后注册读就绪事件就行了，没必要分开放在这里写啊】
                //为了演示我还是写了
                else if (key.isWritable() && key.isValid()) {
                    writeHandler(key);
                }
                //当选择键是可连接的【其实这个是在客户端才会被触发，为了演示这里也可以写，我才写的】
                else if (key.isConnectable()) {
                    System.out.println("选择键是可连接的,key.isConnectable() 是 true");
                }

            }
        }
    }


    //当选择键是可接受的处理逻辑
    //static静态，可用可不用
    private void acceptableHandler(SelectionKey key) throws IOException {
        System.out.println("当选择键是可接受的处理逻辑");
        //从选择键获取服务信道,需要强转
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        //服务信道监听新进来的连接,返回一个信道
        SocketChannel sc = serverSocketChannel.accept();

        //信道不为空才执行
        if (sc != null) {
            //到了这里说明连接成功
            //
            //获取本地ip地址与端口号
//        SocketAddress socketAddress = sc.getLocalAddress();
//        System.out.println(socketAddress.toString());
//        /127.0.0.1:8080
            //获取远程ip地址与端口号
            SocketAddress ra = sc.getRemoteAddress();
            System.out.println(ra.toString());
//        /127.0.0.1:64513
            //存储信道对象
            socketChannelMap.put(ra.toString(), sc);
            System.out.println("当前在线人数：" + socketChannelMap.size());
            //将该信道设置为非阻塞
            sc.configureBlocking(false);
            //获取选择器
            Selector mselector = key.selector();
            //信道注册到选择器 ---- 读操作就绪
            sc.register(mselector, SelectionKey.OP_READ);
            //在这里设置字节缓冲区的 关联关系，但是我设置会在读操作报空指针异常，原因未知
            // sc.register(mselector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
        }
    }

    //当选择键是可读的处理逻辑
    private void readHandler(SelectionKey key) throws IOException {
        SocketChannel sc = null;
        /*
        每当客户端强制关闭了连接，就会发送一条数据过来这里说
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
                //进入业务层
                eatService.food(mselector, sc, buffer, jsonstr, ipMap, usernameMap, socketChannelMap);
            }

        } catch (Exception e) {
//            e.printStackTrace();
            System.out.println("//远程客户端强迫关闭了连接。关闭客户端已经关闭，服务端继续运行");

            //发生异常才关闭
            if (sc != null) {
                //获取ip地址与端口号
//                SocketAddress socketAddress = sc.getLocalAddress();
//                System.out.println(socketAddress.toString());
                //  /127.0.0.1:8080
                //
                //获取远程ip地址与端口号
                SocketAddress ra = sc.getRemoteAddress();
                System.out.println(ra.toString());
                //移除
                socketChannelMap.remove(ra.toString());
                System.out.println(socketChannelMap);
                //
                String username = usernameMap.get(ra.toString());
                System.out.println("用户名叫：" + username + " 的客户端下线");
                usernameMap.remove(ra.toString());
                ipMap.remove(username);
                //
                System.out.println("当前在线人数：" + socketChannelMap.size());
                //
                System.out.println("打印当前用户信息");
                System.out.println(ipMap);
                System.out.println(usernameMap);
                //
                sc.close();
            }
            //取消该选择键
            key.channel();
        }
    }

    //当选择键是可写的且是有效的处理逻辑
    private void writeHandler(SelectionKey key) throws IOException {
        System.out.println("当选择键是可写的且是有效的处理逻辑 ，我被自己通知来写东西啦，虽然不知道为什么要分开读写");
        //获取信道,需要强转
        SocketChannel sc = (SocketChannel) key.channel();
        //设置字节缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //
        String str = "我要写东西，你看到了吗" + System.currentTimeMillis();
        //清除索引信息【即position = 0 ；capacity = limit】
        buffer.clear();
        //将字符转成字节流放入缓冲中
        buffer.put(str.getBytes(StandardCharsets.UTF_8));
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
