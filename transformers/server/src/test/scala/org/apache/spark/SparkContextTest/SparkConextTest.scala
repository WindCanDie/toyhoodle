package org.apache.spark.SparkContextTest

import java.util.Properties

import org.apache.commons.lang3.SerializationUtils
import org.apache.spark.TaskContext
import org.apache.spark.internal.Logging
import org.apache.spark.rdd.RDD
import org.apache.spark.util.ClosureCleaner

import scala.reflect.ClassTag

/**
  * Created by Administrator on 2017/8/25.
  */
class SparkConextTest extends Logging{
  private[spark] def clean[F <: AnyRef](f: F, checkSerializable: Boolean = true): F = {
    ClosureCleaner.clean(f, checkSerializable)
    f
  }
  protected[spark] val localProperties = new InheritableThreadLocal[Properties] {
    override protected def childValue(parent: Properties): Properties = {
      // Note: make a clone such that changes in the parent properties aren't reflected in
      // the those of the children threads, which has confusing semantics (SPARK-10563).
      SerializationUtils.clone(parent)
    }
    override protected def initialValue(): Properties = new Properties()
  }
  def runJob[T, U: ClassTag](
                              rdd: RDD[T],
                              func: (TaskContext, Iterator[T]) => U,
                              partitions: Seq[Int],
                              resultHandler: (Int, U) => Unit): Unit = {
//    val callSite = getCallSite
//    val cleanedFunc = clean(func)
//    logInfo("Starting job: " + callSite.shortForm)
//    if (conf.getBoolean("spark.logLineage", false)) {
//      logInfo("RDD's recursive dependencies:\n" + rdd.toDebugString)
//    }
//    dagScheduler.runJob(rdd, cleanedFunc, partitions, callSite, resultHandler, localProperties.get)
//    progressBar.foreach(_.finishAll())
//    rdd.doCheckpoint()
  }
}
