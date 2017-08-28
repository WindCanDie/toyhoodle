package com.toy.transformers.api

import java.util.Properties

/**
  * Created by Administrator on 2017/8/28.
  */
class DataFrameReader(session: Session) {
  def jdbc(url: String, table: String, properties: Properties): Boolean = {
    return true
  }

}
