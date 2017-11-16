package com.toy.rpc

/**
  * EVN 回调方法
  */
trait RpcEndpoint {

  val evn: RpcEvn

  def self: RpcRefEndpoit = {
    evn.getRefEndpoit(this)
  }

  def onStart(): Unit = {
  }

  def receive(): PartialFunction[Any, Unit] = {
    case _ => throw new RuntimeException(self + " does not implement 'receive'")
  }

  def recevieAndReply(context: RpcCallContext): PartialFunction[Any,Unit] ={
    case _ => context.sendFailue(new RuntimeException(self + " does not implement 'receive'"))
  }

  def onError(cause: Throwable): Unit = {
    throw cause;
  }


}
