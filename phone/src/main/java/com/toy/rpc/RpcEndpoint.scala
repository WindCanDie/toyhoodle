package com.toy.rpc

trait Endpoint {

  val evn: Evn


  def onStart(): Unit = {
  }


}
