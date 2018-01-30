package com.toy.mr.core

object MasterMessage extends Serializable {

  case class Register(host: String, post: String, core: Int)

}
