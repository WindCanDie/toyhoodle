package com.toy.mr.core

import java.util.concurrent.{ConcurrentHashMap, Executors, ThreadFactory, TimeUnit}

import akka.actor.{ActorRef, UntypedActor}
import akka.pattern.Patterns
import com.toy.mr.rpc.RpcEvn
import akka.actor.Status
import akka.remote.{DisassociatedEvent, RemotingLifecycleEvent}

import scala.collection._
import scala.collection.convert.decorateAsScala._

class Master extends UntypedActor {


  private val registerWorker: concurrent.Map[String, WorkerInfo] = new ConcurrentHashMap[String, WorkerInfo]().asScala
  private val disconnectWorker: concurrent.Map[String, String] = new ConcurrentHashMap[String, String]().asScala

  private val timeout: Long = 300
  private val heartInterval: Long = 20
  private val heartPoolSize: Int = 10
  private val heartThreadFactory: ThreadFactory = new ThreadFactory {
    override def newThread(r: Runnable): Thread = {
      val thread = new Thread("toy.mr.master.heart")
      thread.setDaemon(true)
      thread
    }
  }
  private val heartPool = Executors.newScheduledThreadPool(heartPoolSize, heartThreadFactory)


  override def preStart() {
    context.system.eventStream.subscribe(self, classOf[RemotingLifecycleEvent])
    heartPool.schedule(new Runnable {
      override def run(): Unit = {
        heartbeat()
      }
    }, heartInterval, TimeUnit.SECONDS)
  }

  override def postStop(): Unit = {

  }

  override def onReceive(message: Any) {
    message match {
      case MasterMessage.Register(host: String, post: String, core: Int) =>
        val workerid = s"toy.$host.$post"
        registerWorker.get(workerid) match {
          case Some(_) => getSender().tell(Status.Failure(new Exception(s"worker host = $host post = $post has register")), ActorRef.noSender)
          case None => registerWorker.put(workerid, new WorkerInfo(workerid, core, getSender()))
        }
      case DisassociatedEvent(_, remoteAddress, _) =>
        print(remoteAddress)
      case _ => println("not message ha ndle")
    }
  }

  private def register() {
  }

  private def heartbeat(): Unit = {
    registerWorker.foreach(e => {
      //      Patterns.ask(WorkerMessage.IsSurvive,)

    })
  }
}


object Master {
  val masterEvn = "MasterSystem"
  val master = "master"

  def main(args: Array[String]): Unit = {
    val host = args(0)
    val post = args(1)
    val evn = createEvn(host, post)
    evn.register[Master](master)
  }

  def createEvn(host: String, post: String): RpcEvn = {
    RpcEvn.create(masterEvn, host, post)
  }
}
