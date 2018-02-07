package com.toy.mr.odc


import com.toy.mr.core.Partition

import scala.reflect.ClassTag

class MapODC[U: ClassTag, T: ClassTag]( var prev: OperatorDC[T]) extends OperatorDC[U](prev) {
  override def compute(partition: Partition) = ???

  override protected def getPartitions = ???
}
