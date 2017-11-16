package com.toy.rpc

class RpcEvn() {

  def getRefEndpoit(url: String): RpcRefEndpoit = {
    new RpcRefEndpoit
  }

  def getRefEndpoit(endpoit: RpcEndpoint): RpcRefEndpoit = {
    new RpcRefEndpoit
  }
}
