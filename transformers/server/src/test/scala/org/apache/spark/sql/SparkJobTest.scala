package org.apache.spark.sql

import java.util.Properties

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.catalyst.InternalRow
import org.apache.spark.sql.execution.datasources.jdbc.{JDBCRDD, JDBCRelation}
import org.apache.spark.sql.sources.Filter
import org.apache.spark.sql.types.StructType
import org.apache.spark.{SparkConf, SparkContext}
import org.scalatest.FunSuite

/**
  * Created by Administrator on 2017/8/25.
  */
class SparkJobTest extends FunSuite {
  test("job") {
    val properties = new Properties()
    properties.put("user", "root")
    properties.put("password", "123456")
    val schema: StructType = JDBCRDD.resolveTable("jdbc:mysql://127.0.0.1:3306/test", "test", properties)
    val conf = new SparkConf()
      .setAppName("AppName")
      .setMaster("local[3]")
    val sc = new SparkContext(conf)
    val jdbc = JDBCRDD.scanTable(
      sc,
      schema,
      "jdbc:mysql://127.0.0.1:3306/test",
      properties,
      "test",
      Array("id", "ss"),
      Array(),
      JDBCRelation.columnPartition(null))
    class Triple[F, S, T](val first: F, val second: S, val third: T)
    val triple = new Triple("Spark", 3, 3.1415)
    val results = sc.runJob(jdbc, (iter: Iterator[InternalRow]) => iter.toArray)
    println(results)
  }

}
