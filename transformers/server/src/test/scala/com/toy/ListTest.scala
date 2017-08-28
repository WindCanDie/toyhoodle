package com.toy

import org.scalatest.FunSuite

/**
  * Created by Administrator on 2017/8/25.
  */
class ListTest extends FunSuite {
  test("list") {
    var list: List[String] = List("a", "v", "d")
    list = "c" :: list
    println(list)
  }
}
