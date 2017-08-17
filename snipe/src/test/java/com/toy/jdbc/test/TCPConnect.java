package com.toy.jdbc.test;

import com.toy.common.network.*;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;

/**
 * Created by Administrator on
 * 2017/8/9.
 */
public class TCPConnect {
    @Test
    public void tcpClient() throws IOException, InterruptedException {
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
                client.getChannel().writeAndFlush(ByteBuffer.wrap("show Tabel".getBytes()));
                client.getChannel().flush();
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

        TransportClientFactory clientFactory = new TransportClientFactory(context);
        TransportClient client = clientFactory.createClient("SFHN08", 10000);
        Thread.sleep(500000);
    }

    @Test
    public void tcpClient2() throws IOException {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("SFHN08", 10000));
        InputStream out  =  socket.getInputStream();
        byte[] v = new byte[1024];
        while (out.read(v)!=-1){
            System.out.println(new String(v));
        }


    }

}
