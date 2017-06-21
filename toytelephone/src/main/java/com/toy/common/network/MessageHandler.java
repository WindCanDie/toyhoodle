package com.toy.common.network;

import com.toy.common.message.Message;

/**
 * Created by Administrator on
 * 2017/6/21.
 */
public interface MessageHandler<T extends Message> {
    /**
     * Handles the receipt of a single message.
     */
    void handle(T message) throws Exception;

    /**
     * Invoked when the channel this MessageHandler is on is active.
     */
    void channelActive();

    /**
     * Invoked when an exception was caught on the Channel.
     */
    void exceptionCaught(Throwable cause);

    /**
     * Invoked when the channel this MessageHandler is on is inactive.
     */
    void channelInactive();
}
