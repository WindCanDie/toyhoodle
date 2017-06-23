package com.toy.networktest;

import org.junit.Test;

import java.io.IOException;

/**
 * Created by Administrator on
 * 2017/6/6.
 */
public class TranspostServerTest {
    @Test
    public void TranspostTest() throws IOException, InterruptedException {

        // TransportClient client = new TransportClientFactory(new TransportContext(rpcHandler)).createClient("127.0.0.1", 8088);
    }

    @Test
    public void runSercer() {
        createServer();
    }

    private void createServer() {
        //   TransportServer server = new TransportServer(8088, null, new TransportContext(rpcHandler));
    }
}
