package com.toy.rpc

trait RpcCallContext {

  def sendFailue(exception: Exception)

  def send(req: Any)

}
