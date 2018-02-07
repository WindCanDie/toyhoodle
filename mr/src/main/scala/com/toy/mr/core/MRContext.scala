package com.toy.mr.core

import java.util.Properties

class MRContext(AppName: String) {
  private val conf = new Properties()
  private var masterUrl = conf.getOrDefault("toy.master", 33334)
  private var core = conf.getOrDefault("toy.exe.core", 1)

  def setMasterUrl(masterUrl: String): Unit = {
    this.masterUrl = masterUrl
  }

  def setCore(core: Int): Unit = {
    this.core = core
  }

  def subimit(computeDAG: ComputeDAG[_]): Unit = {

  }
}

