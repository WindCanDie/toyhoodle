package com.toy.common.network;


import com.google.common.base.Throwables;
import com.google.common.util.concurrent.SettableFuture;
import com.toy.common.message.OneWayMessage;
import com.toy.common.message.RpcRequest;
import com.toy.common.message.buffer.NioMessageBuffer;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static com.toy.common.network.util.NettyUtil.getRemoteAddress;

/**
 * Created by Administrator
 * on 2017/6/2.
 */
public class TransportClient {
    private Channel channel;
    private Logger logger = LoggerFactory.getLogger(TransportClient.class);
    TransportResponseHandler handler;
    private volatile boolean timedOut;

    public TransportClient(Channel channel, TransportResponseHandler responseHandler) {
        this.channel = channel;
        this.handler = responseHandler;
        timedOut = false;
    }

    public Channel getChannel() {
        return channel;
    }

    public boolean isAction() {
        return !this.timedOut && (channel.isOpen() || channel.isActive());
    }

    public void sendRpc(ByteBuffer message, RpcResponseCallback callback) {
        long startTime = System.currentTimeMillis();
        if (!isAction()) {
            throw new RuntimeException("channel is cloaed");
        }
        long requestId = Math.abs(UUID.randomUUID().getLeastSignificantBits());
        handler.addRpcRequest(requestId, callback);
        channel.writeAndFlush(new RpcRequest(new NioMessageBuffer(message)))
                .addListener(future -> {
                    if (future.isSuccess()) {
                        long timeTaken = System.currentTimeMillis() - startTime;
                        if (logger.isTraceEnabled()) {
                            logger.trace("Sending request {} to {} took {} ms", requestId,
                                    getRemoteAddress(channel), timeTaken);
                        }
                    } else {
                        String errorMsg = String.format("Failed to send RPC %s to %s: %s", requestId,
                                getRemoteAddress(channel), future.cause());
                        logger.error(errorMsg, future.cause());
                        //handler.removeRpcRequest(requestId);
                        channel.close();
                        try {
                            callback.onFailure(new IOException(errorMsg, future.cause()));
                        } catch (Exception e) {
                            logger.error("Uncaught exception in RPC response callback handler!", e);
                        }
                    }
                });

    }


    public ByteBuffer sendRpcSync(ByteBuffer message, long timeoutMs) {
        final SettableFuture<ByteBuffer> result = SettableFuture.create();

        sendRpc(message, new RpcResponseCallback() {
            @Override
            public void onSuccess(ByteBuffer response) {
                ByteBuffer copy = ByteBuffer.allocate(response.remaining());
                copy.put(response);
                // flip "copy" to make it readable
                copy.flip();
                result.set(copy);
            }

            @Override
            public void onFailure(Throwable e) {
                result.setException(e);
            }
        });

        try {
            return result.get(timeoutMs, TimeUnit.MILLISECONDS);
        } catch (ExecutionException e) {
            throw Throwables.propagate(e.getCause());
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
    }

    /**
     * Sends an opaque message to the RpcHandler on the server-side. No reply is expected for the
     * message, and no delivery guarantees are made.
     *
     * @param message The message to send.
     */
    public void send(ByteBuffer message) {
        channel.writeAndFlush(new OneWayMessage(new NioMessageBuffer(message)));
    }

    public void timeOut() {
        this.timedOut = true;
    }

}
