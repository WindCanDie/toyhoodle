package com.toy.mr.odc

import com.toy.mr.core.{MRContext, Partition}

abstract class OperatorDC[T](@transient private var _sc: MRContext) extends Serializable {
  def context: MRContext = _sc

  def this(@transient oneParent: OperatorDC[_]) = this(context)

  def compute(partition: Partition): Iterator[T]

  protected def getPartitions: Array[Partition]

  protected def getDependencies: None.type = None


}
