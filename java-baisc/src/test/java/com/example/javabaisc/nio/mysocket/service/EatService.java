package com.example.javabaisc.nio.mysocket.service;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ConcurrentMap;


public interface EatService {
    public void food(Selector mselector, SocketChannel sc, ByteBuffer buffer, String jsonstr,
                     ConcurrentMap<String, String> ipMap,
                     ConcurrentMap<String, String> usernameMap,ConcurrentMap<String,  SocketChannel> socketChannelMap) throws IOException;
}
