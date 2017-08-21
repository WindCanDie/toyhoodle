package com.toy.snipe.tcp.db;

import com.toy.snipe.tcp.Scheduler;
import com.toy.thread.ThreadUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by Administrator on
 * 2017/8/17.
 */
public class TransprtDBClint {
    private Scheduler scheduler;
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

        void sendData(ByteBuf byteBuf) throws IOException {
            byte[] b = new byte[byteBuf.readableBytes()];
            byteBuf.getBytes(0, b);
            socket.getOutputStream().write(b);
        }

        void dataWriteBack() throws IOException {
            ByteBuf backData = ctx.alloc().buffer();
            backData.writeBytes(socket.getInputStream(), socket.getInputStream().available());
            try {
                ctx.writeAndFlush(backData).sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        void close() throws IOException {
            socket.close();
        }

    }


    public TransprtDBClint(Scheduler scheduler) {
        this.scheduler = scheduler;
        this.clients = new ConcurrentHashMap<>();
        this.takes = new LinkedBlockingQueue<>();
        this.runClient = new LinkedBlockingQueue<>();
        init();
    }

    @SuppressWarnings("InfiniteLoopStatement")
    private void init() {
        //start event scan
        ThreadPoolExecutor executor = ThreadUtil.newDaemonFixedThreadPool(1, "event_scan");
        executor.execute(() -> {
            try {
                for (; ; ) {
                    for (Map.Entry<String, Client> entry : clients.entrySet()) {
                        if (closed) break;
                        try {
                            Client client = entry.getValue();
                            if (client.isMassage() && !this.runClient.contains(client)) {
                                this.runClient.put(client);
                                this.takes.put(() -> {
                                    try {
                                        client.dataWriteBack();
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
            } catch (Exception e) {
                e.getStackTrace();
                System.out.println(Thread.currentThread().getName() + e.getMessage());
            }

        });
        //start take exector
        ThreadPoolExecutor executorTake = ThreadUtil.newDaemonFixedThreadPool(4, "socket executor take");
        executorTake.execute(() -> {
            try {
                for (; ; ) {
                    try {
                        Runnable run = this.takes.take();
                        run.run();
                    } catch (InterruptedException e) {
                        System.out.println(e.getMessage());
                        throw new RuntimeException(e);
                    }
                }
            } catch (Exception e) {
                e.getStackTrace();
                System.out.println(e.getMessage());
            }
        });

    }

    void wride(ChannelHandlerContext ctx, final ByteBuf byteBuf) throws InterruptedException {
        Client client = clients.get(getClientId(ctx));
        takes.put(() -> {
            try {
                client.sendData(byteBuf);
            } catch (IOException e) {
                throw new RuntimeException("sendData error" + e);
            }
        });
    }

    public void createClient(ChannelHandlerContext ctx) {
        Socket socket = null;
        try {
            socket = new Socket();
            socket.connect(scheduler.getServerAddress());
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

    void closeClient(ChannelHandlerContext ctx) throws IOException {
        String id = getClientId(ctx);
        clients.remove(getClientId(ctx)).close();
    }

    public void close() throws IOException {
        this.closed = true;
        for (Map.Entry<String, Client> client : clients.entrySet()) {
            client.getValue().close();
        }
    }
}
