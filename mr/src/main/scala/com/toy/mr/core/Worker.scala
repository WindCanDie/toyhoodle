package com.toy.mr.core

import akka.actor.{ActorSelection, UntypedActor}
import akka.pattern.Patterns
import akka.remote.DisassociatedEvent
import com.toy.mr.rpc.RpcEvn
import org.slf4j.{Logger, LoggerFactory}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

class Worker extends UntypedActor {
  val logger = LoggerFactory.getLogger(Worker.getClass)
  val timeout = 3000

  private[Worker] val masterURI = s"akka.tcp://${Master.masterEvn}@${Worker.masterHost}:${Worker.masterPost}/user/${Master.master}"
  val masterRef: ActorSelection = context.actorSelection(masterURI)

  val core = 1


  override def preStart(): Unit = {
    val funct = Patterns.ask(masterRef, MasterMessage.Register(Worker.host, Worker.post, core), timeout)
    funct.onComplete {
      case Success(_) => logger.info(s"worker ${Worker.host}:${Worker.post} register success")
      case Failure(e) => logger.error(e.getLocalizedMessage)
    }
  }

  override def onReceive(message: Any) {
    message match {
      case WorkerMessage.ReplyTest() => println("Test linking success")
      case _ => println("asd")
    }
  }
}

object Worker {
  val workerEvn = "WorterSystem"
  val worker = "Worker"
  var masterHost: String = _
  var masterPost: String = _
  var host: String = _
  var post: String = _

  def main(args: Array[String]): Unit = {
    host = args(0)
    post = args(1)
    masterHost = args(2)
    masterPost = args(3)
    val evn = createEvn(host, post)
    evn.register[Worker](worker)
  }

  def createEvn(host: String, post: String): RpcEvn = {
    RpcEvn.create(workerEvn, host, post)
  }
}
