package com.toy.toytelephone.rpc.netty;

import com.toy.toytelephone.rpc.RpcEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;

/**
 * Created by Administrator on
 * 2017/6/1.
 */
public class Inbox {
    private RpcEndpoint rpcEndpoint;
    private NettyRpcEndpointRef rpcEndpointRef;
    private LinkedList<InboxMessage> messages = new LinkedList<>();
    private boolean enableConcurrent = false;
    private int numActiveThreads = 1;
    private boolean stopped = false;
    private Logger logger = LoggerFactory.getLogger(Inbox.class);
    public Inbox(RpcEndpoint rpcEndpoint, NettyRpcEndpointRef rpcEndpointRef) {
        this.rpcEndpoint = rpcEndpoint;
        this.rpcEndpointRef = rpcEndpointRef;
        messages.add(new InboxMessage.OnStart());
    }

    public synchronized void stop() {
        if (!stopped) {
            enableConcurrent = false;
            stopped = true;
            messages.add(new InboxMessage.OnStop());
            // Note: The concurrent events in messages will be processed one by one.
        }
    }

    public void process(Dispatcher dispatcher) {
        InboxMessage message = null;
        synchronized (this) {
            if (!enableConcurrent && numActiveThreads != 0) return;
            message = messages.poll();
            if (message != null) {
                numActiveThreads += 1;
            } else {
                return;
            }
        }
        while (true) {
            if (message instanceof InboxMessage.OnStart) {
                rpcEndpoint.onStart();
            } else if (message instanceof InboxMessage.OnStop) {
                rpcEndpoint.onStop();
            }


            synchronized (this) {
                if (!enableConcurrent && numActiveThreads != 1) {
                    // If we are not the only one worker, exit
                    numActiveThreads -= 1;
                    return;
                }
                message = messages.poll();
                if (message == null) {
                    numActiveThreads -= 1;
                    return;
                }
            }
        }
    }

    public void post(InboxMessage message) {
        if (stopped) {
            // We already put "OnStop" into "messages", so we should drop further messages
            onDrop(message);
        } else {
            messages.add(message);
        }
    }

    protected void onDrop(InboxMessage message) {
        logger.warn("Drop "+message+" because $endpointRef is stopped");
    }

}
