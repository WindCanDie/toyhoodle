package com.toy.common.network.util;

import io.netty.channel.Channel;

/**
 * Created by Administrator
 * on 2017/6/21.
 */
public class NettyUtil {
    /**
     * Returns the remote address on the channel or "&lt;unknown remote&gt;" if none exists.
     */
    public static String getRemoteAddress(Channel channel) {
        if (channel != null && channel.remoteAddress() != null) {
            return channel.remoteAddress().toString();
        }
        return "<unknown remote>";
    }
}
