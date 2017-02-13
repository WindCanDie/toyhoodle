package com.toy.toyhoodle.core.rpc.impl;

import com.toy.toyhoodle.core.conf.HoodlCofig;
import com.toy.toyhoodle.core.rpc.Endpoint;
import com.toy.toyhoodle.core.rpc.Env;
import com.toy.toyhoodle.core.rpc.RefEndpoint;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by ljx on 2017/1/3.
 */
public class Envimpl implements Env {
    private ServerSocket serverSocket;
    private EnvConf envConf;
    private BlockingQueue messages;
    private String queueNumber = "toy.hoodle.message.queue.number";

    public Envimpl(String name, int port, String host, HoodlCofig hoodlCofig) throws IOException {
        envConf = new EnvConf(name, port, host, hoodlCofig);
    }

    private void init(EnvConf envConf) {
        messages = new ArrayBlockingQueue(Integer.parseInt(envConf.get(queueNumber)));

    }

    private void startServer() {
        try {
            serverSocket = new ServerSocket(Integer.parseInt(envConf.get(EnvConf.SERVE_PORT)));
            new ReceiveThread().start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public RefEndpoint setupEndpoint(String name, Endpoint endpoint) {
        return null;
    }

    @Override
    public RefEndpoint setupEndpointRefByURI(String uri) {
        return null;
    }

    @Override
    public RefEndpoint setupEndpointRef(String address, String endpointName) {
        return null;
    }

    @Override
    public void stop(Endpoint endpoint) {

    }

    class ReceiveThread extends Thread {
        @Override
        public void run() {
            while (true) {
                try (Socket socket = serverSocket.accept()) {
                    try {
                        Object object = new ObjectInputStream(socket.getInputStream()).readObject();
                        messages.put(object);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    class DealThread extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    Object object =  messages.take();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
