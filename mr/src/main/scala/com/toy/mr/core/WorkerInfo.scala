package com.toy.mr.core

import akka.actor.ActorRef

class WorkerInfo(val workerId: String,val core: Int,val WorkerRef: ActorRef) {
}
