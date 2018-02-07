package com.yw.databus.core.rpc;

import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.server.ServerContext;
import org.apache.thrift.server.TServerEventHandler;
import org.apache.thrift.transport.TTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class RpcEventHandler implements TServerEventHandler {
    private Map<String, RpcEndpoint> registTable;
    private Logger logger = LoggerFactory.getLogger(RpcEventHandler.class);

    public RpcEventHandler(Map<String, RpcEndpoint> registTable) {
        this.registTable = registTable;
    }

    @Override
    public void preServe() {
        for (Map.Entry<String, RpcEndpoint> entry : registTable.entrySet()) {
            try {
                entry.getValue().parper();
            } catch (Exception e) {
                logger.error(e.getMessage());
                throw new RuntimeException("Service parper error", e);
            }
        }
    }

    @Override
    public ServerContext createContext(TProtocol input, TProtocol output) {
        return null;
    }

    @Override
    public void deleteContext(ServerContext serverContext, TProtocol input, TProtocol output) {
    }

    @Override
    public void processContext(ServerContext serverContext, TTransport inputTransport, TTransport outputTransport) {
    }
}
