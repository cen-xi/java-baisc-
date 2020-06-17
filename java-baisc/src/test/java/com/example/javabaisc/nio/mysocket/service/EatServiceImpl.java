package com.example.javabaisc.nio.mysocket.service;


import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

/**
 * 业务层
 */
@Service
public class EatServiceImpl implements EatService {
    @Override
    public void food(Selector mselector, SocketChannel sc, ByteBuffer buffer, String jsonstr,
                     ConcurrentMap<String, String> ipMap,
                     ConcurrentMap<String, String> usernameMap, ConcurrentMap<String, SocketChannel> socketChannelMap) throws IOException {

        //解析json串成map
        Map<String, Object> map = JSON.parseObject(jsonstr);
        System.out.println(map);
        int type = (Integer) map.get("type");
        if (type == 1) {
            //返回结果
            String res = "apple,好好吃，我好饿";
            Map<String, Object> map2 = new HashMap<>();
            map2.put("r-type", 1);
            map2.put("data", res);
            String jsonStr = JSON.toJSONString(map2);
            //
            System.out.println(jsonStr);
            //
            //清除索引信息【即position = 0 ；capacity = limit】
            buffer.clear();
            //指定编码将符串转字字节流
            buffer.put(jsonStr.getBytes(StandardCharsets.UTF_8));
            //定位到有效字符的索引【即limit = position ,position = 0 ,capacity 不变】
            buffer.flip();
            //如果 position < limit ，即仍有缓冲区的数据未写到信道中
            while (buffer.hasRemaining()) {
                //写操作
                sc.write(buffer);
            }
            //整理索引【即position定位到缓冲区未读的数据末尾 ，capacity = limit】
            //这里用不到
            //buffer.compact();
            //注册读就绪事件，【让客户端读】
            sc.register(mselector, SelectionKey.OP_READ);
        } else if (type == 3) {
            try {
                //客户端回应
                System.out.println(" //客户端回应 业务类型3，//到了这里不再传输数据，懒得写，以免无限循环");
                //获取远程ip地址与端口号
                SocketAddress ra = sc.getRemoteAddress();
                //获取该客户端的用户名
                String username = usernameMap.get(ra.toString());
                //懒得写新接口，直接判断如果该客户端如果是cen，则向yue发送信息
                if (username.equals("cen")) {
                    //判断guo是否在线
                    ////获取guo的ip
                    String ip = ipMap.get("guo");
                    System.out.println("guo 的ip:" + ip);
                    if (ip == null || ip.isEmpty()) {
                        System.out.println("guo 不存在，未上线");
                        return;
                    }
                    System.out.println("向 guo 发送信息");
                    //存在
                    //
                    SocketChannel mchannel = socketChannelMap.get(ip);
                    String res = "我是cen,我向guo发送消息，看到了吗" + new Date();
                    //
                    System.out.println(res);
                    //
                    Map<String, Object> map3 = new HashMap<>();
                    map3.put("r-type", 6);
                    map3.put("data", res);
                    String jsonStr = JSON.toJSONString(map3);
                    //清除索引信息【即position = 0 ；capacity = limit】
                    ByteBuffer buffer2 = ByteBuffer.allocate(1024);
                    buffer2.clear();
                    //指定编码将符串转字字节流
                    buffer2.put(jsonStr.getBytes(StandardCharsets.UTF_8));
                    //定位到有效字符的索引【即limit = position ,position = 0 ,capacity 不变】
                    buffer2.flip();
                    System.out.println(new String(buffer2.array()));
                    //如果 position < limit ，即仍有缓冲区的数据未写到信道中
                    while (buffer2.hasRemaining()) {
                        //写操作
                        mchannel.write(buffer2);
                    }
                    //整理索引【即position定位到缓冲区未读的数据末尾 ，capacity = limit】
                    //这里用不到
                    // buffer.compact();
                    //注册读就绪事件，【让客户端读】
                    sc.register(mselector, SelectionKey.OP_READ);
                    System.out.println("发送成功");

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (type == 0) {
            System.out.println("type是0");
            String username = (String) map.get("username");
            System.out.println("用户名叫：" + username + " 的客户端上线");
            //注册用户信息[根据用户名获取ip]
            ipMap.put(username, sc.getRemoteAddress().toString());
            //注册用户信息[根据ip获取用户名]
            usernameMap.put(sc.getRemoteAddress().toString(), username);
            System.out.println("打印当前用户信息");
            System.out.println(ipMap);
            System.out.println(usernameMap);
            //向选择器注册写就绪事件，是通知自己写东西【让自己写】,一般不会注册OP_WRITE，为了展示用法我才这样写
            sc.register(mselector, SelectionKey.OP_WRITE);
        }
    }
}
