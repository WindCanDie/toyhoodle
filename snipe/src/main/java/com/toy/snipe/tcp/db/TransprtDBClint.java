package com.toy.snipe.tcp.db;

import com.toy.thread.ThreadUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by Administrator on
 * 2017/8/17.
 */
public class TransprtDBClint {
    private String serverIp;
    private int serverPost;
    private Map<String, Client> clients;
    private LinkedBlockingQueue<Runnable> takes;
    private LinkedBlockingQueue<Client> runClient;
    private boolean closed = false;


    private static class Client {
        ChannelHandlerContext ctx;
        String id;
        Socket socket;

        Client(ChannelHandlerContext ctx, String id, Socket socket) {
            this.ctx = ctx;
            this.id = id;
            this.socket = socket;
        }

        boolean isMassage() throws IOException {
            return socket.getInputStream().available() > 0;
        }

        void dataWriteback() throws IOException {
            ByteBuf backData = ctx.alloc().buffer();
            backData.writeBytes(socket.getInputStream(), socket.getInputStream().available());
            ctx.writeAndFlush(backData);
        }

        void close() throws IOException {
            socket.close();
        }

    }


    public TransprtDBClint(String serverIp, int serverPost) {
        this.serverIp = serverIp;
        this.serverPost = serverPost;
        this.clients = new HashMap<>();
        this.takes = new LinkedBlockingQueue<>();
        this.runClient = new LinkedBlockingQueue<>();
        init();
    }

    private void init() {
        //start event scan
        ThreadPoolExecutor executor = ThreadUtil.newDaemonFixedThreadPool(1, "event scan");
        executor.execute(() -> {
            for (; ; ) {
                for (Map.Entry<String, Client> entry : clients.entrySet()) {
                    if (closed) break;
                    try {
                        Client client = entry.getValue();
                        if (client.isMassage() && !runClient.contains(client)) {
                            runClient.put(client);
                            takes.put(() -> {
                                try {
                                    client.dataWriteback();
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                } finally {
                                    runClient.remove(client);
                                }
                            });
                        }
                    } catch (IOException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        //start take exector
        ThreadPoolExecutor executorTake = ThreadUtil.newDaemonFixedThreadPool(4, "socket executor take");
        executorTake.execute(() -> {
            assert !closed;
            for (; ; ) {
                try {
                    Runnable run = takes.take();
                    run.run();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }

    public void createClient(ChannelHandlerContext ctx) {
        Socket socket = null;
        try {
            socket = new Socket(serverIp, serverPost);
        } catch (IOException e) {
            e.printStackTrace();
            ctx.writeAndFlush(e.getMessage());
        }
        Client client = new Client(ctx, getClientId(ctx), socket);
        clients.put(client.id, client);
    }

    private String getClientId(ChannelHandlerContext ctx) {
        InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();
        return address.getHostName() + address.getPort();
    }

    private void close() throws IOException {
        this.closed = true;
        for (Map.Entry<String, Client> client : clients.entrySet()) {
            client.getValue().close();
        }
    }
}
