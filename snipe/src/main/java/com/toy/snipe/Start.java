package com.toy.snipe;

import com.toy.common.network.*;

import java.nio.ByteBuffer;

/**
 * Created by ljx on
 * 2017/8/3.
 */
public class Start {
    public static void main(String[] args) {
        TransportContext context = new TransportContext(new RpcHandler() {
            @Override
            public void receive(TransportClient client, ByteBuffer message, RpcResponseCallback callback) {
                System.out.println("aaaaaaaaa");
            }

            @Override
            public void receive(TransportClient client, ByteBuffer message) {
                System.out.println("bbbbbbbbb");
            }

            @Override
            public void channelActive(TransportClient client) {
                System.out.println("cccccccccc");
            }

            @Override
            public void channelInactive(TransportClient client) {
                System.out.println("eeeeeeeeeee");
            }

            @Override
            public void exceptionCaught(Throwable cause, TransportClient client) {
                System.out.println("ddddddddd");
            }
        });

        TransportServer server = new TransportServer(10001, null, context);

    }
}
