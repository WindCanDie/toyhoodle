package com.toy.core;

import com.toy.toytelephone.rpc.RpcAddress;

public interface SreverMessage {
    class Register implements SreverMessage {
        RpcAddress address;
        String name;

        public Register(RpcAddress address, String name) {
            this.address = address;
            this.name = name;
        }
    }

}
