package com.toy.networktest;

import com.toy.common.message.RequestMessage;
import com.toy.common.network.TransportClient;
import com.toy.common.network.TransportClientFactory;
import com.toy.common.network.TransportContext;
import com.toy.common.network.TransportServer;
import org.junit.Test;

import java.io.IOException;
import java.lang.annotation.Annotation;

/**
 * Created by Administrator on
 * 2017/6/6.
 */
public class TranspostServerTest {
    @Test
    public void TranspostTest() throws IOException, InterruptedException {

        TransportClient client = new TransportClientFactory(new TransportContext()).createClient("127.0.0.1", 8088);
    }

    @Test
    public void runSercer() {
        createServer();
    }

    private void createServer() {
        TransportServer server = new TransportServer(8088, null, new TransportContext());
    }
}
