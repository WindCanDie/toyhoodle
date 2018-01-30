package com.toy.mr.rpc

import akka.actor.{Actor, ActorRef, ActorSelection, ActorSystem, Props}
import com.typesafe.config.{Config, ConfigFactory}

import scala.reflect.ClassTag

class RpcEvn(evnName: String, configStr: String) {
  private val config: Config = ConfigFactory.parseString(configStr)
  private val actorSystem = ActorSystem(evnName, config)

  def register[T <: Actor : ClassTag](actorName: String): ActorRef = {
    actorSystem.actorOf(Props[T], actorName)
  }

  def actorRef(evnRef: String, hostRef: String, portRef: String, actorNameRef: String): ActorSelection = {
    actorSystem.actorSelection(s"akka.tcp://$evnRef@$hostRef:$portRef/user/$actorNameRef")
  }

}

object RpcEvn {
  private var rpcEvn: Option[RpcEvn] = Option.empty
  private val lifecycleEvents = "on"

  def getEvn: RpcEvn = {
    rpcEvn match {
      case Some(s) => s
      case None => throw new RuntimeException("rpcEvn is not create")
    }
  }


  def create(evnName: String, rpcHost: String, rpcPost: String): RpcEvn = {
    rpcEvn match {
      case Some(_) => throw new RuntimeException("rpcEvn is exist")
      case None => {
        val configStr =
          s"""
             |akka.actor.provider = "akka.remote.RemoteActorRefProvider"
             |akka.remote.netty.tcp.hostname = $rpcHost
             |akka.remote.netty.tcp.port = $rpcPost
             |akka.remote.log-remote-lifecycle-events = on
       """.stripMargin
        rpcEvn = Option.apply(new RpcEvn(evnName, configStr))
      }
        getEvn
    }
  }
}
