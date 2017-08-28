package com.toy.transformers.api

import java.util.Properties

import org.apache.spark.sql.{DataFrame, Dataset}

/**
  * Created by Administrator on 2017/8/28.
  */
class Session(config: Properties) {

  def setUrl(url: String): Unit = {
    config.setProperty("toy.server.url", url)
  }

  def setAppName(appName: String): Unit = {
    config.setProperty("toy.appName", appName);
  }

  def read: DataFrameReader = new DataFrameReader(this)

  def sql(sqlText: String): Dataset = new Dataset(this)

}
