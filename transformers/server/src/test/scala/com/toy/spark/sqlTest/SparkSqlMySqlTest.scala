package com.toy.spark.sqlTest

import java.util.Properties

import org.apache.spark.sql.SparkSession
import org.scalatest.FunSuite

class SparkSqlMySqlTest extends FunSuite {
  test("sparkMysql") {
    val sparkSession = SparkSession.builder.
      master("local")
      .appName("spark session example")
      .config("spark.sql.warehouse.dir", "file:///D://test")
      .getOrCreate()
    val properties = new Properties()

    properties.put("user", "root")
    properties.put("password", "921028")
    val url = "jdbc:mysql://127.0.0.1:3306/test"
    val stud_scoreDF = sparkSession.read.jdbc(url, "test", properties)
    stud_scoreDF.show()
    stud_scoreDF.queryExecution
    while (true){}

  }
}
