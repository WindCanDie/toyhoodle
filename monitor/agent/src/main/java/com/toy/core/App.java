package com.toy.core;

import com.toy.api.RPCContext;
import com.toy.toytelephone.rpc.RpcAddress;

public class App {
    public static void main(String[] arge) {
        RPCContext context = RPCContext.create("", "", "");
        com.toy.Worker worker = (com.toy.Worker) context.getActorRef(new RpcAddress("1", 1, "2"));
        worker.name();
    }
}
