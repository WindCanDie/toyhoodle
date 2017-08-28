package com.toy.transformers.api

import java.sql.ResultSet

import org.apache.spark.sql._

/**
  * Created by Administrator on 2017/8/28.
  */
class Dataset(session: Session) extends Serializable {
  /**
    * Returns a new Dataset sorted by the specified column, all in ascending order.
    * {{{
    *   // The following 3 are equivalent
    *   ds.sort("sortcol")
    *   ds.sort($"sortcol")
    *   ds.sort($"sortcol".asc)
    * }}}
    *
    * @group typedrel
    * @since 2.0.0
    */
  @scala.annotation.varargs
  def sort(sortCol: String, sortCols: String*): Dataset = {
    null
  }

  /**
    * Returns a new Dataset sorted by the given expressions. For example:
    * {{{
    *   ds.sort($"col1", $"col2".desc)
    * }}}
    *
    * @group typedrel
    * @since 2.0.0
    */
  @scala.annotation.varargs
  def sort(sortExprs: Column*): Dataset = {
    null
  }

  /**
    * Returns a new Dataset sorted by the given expressions.
    * This is an alias of the `sort` function.
    *
    * @group typedrel
    * @since 2.0.0
    */
  @scala.annotation.varargs
  def orderBy(sortCol: String, sortCols: String*): Dataset = sort(sortCol, sortCols: _*)

  /**
    * Returns a new Dataset sorted by the given expressions.
    * This is an alias of the `sort` function.
    *
    * @group typedrel
    * @since 2.0.0
    */
  @scala.annotation.varargs
  def orderBy(sortExprs: Column*): Dataset = sort(sortExprs: _*)

  /**
    * Selects column based on the column name and return it as a [[Column]].
    * Note that the column name can also reference to a nested column like `a.b`.
    *
    * @group untypedrel
    * @since 2.0.0
    */
  def col(colName: String): Column = colName match {
    case "*" => null
    case _ => null
  }

  /**
    * Returns a new Dataset with an alias set.
    *
    * @group typedrel
    * @since 1.6.0
    */
  def as(alias: String): Dataset = {
    null
  }

  /**
    * (Scala-specific) Returns a new Dataset with an alias set.
    *
    * @group typedrel
    * @since 2.0.0
    */
  def as(alias: Symbol): Dataset = as(alias.name)

  /**
    * Returns a new Dataset with an alias set. Same as `as`.
    *
    * @group typedrel
    * @since 2.0.0
    */
  def alias(alias: String): Dataset = as(alias)

  /**
    * (Scala-specific) Returns a new Dataset with an alias set. Same as `as`.
    *
    * @group typedrel
    * @since 2.0.0
    */
  def alias(alias: Symbol): Dataset = as(alias)

  /**
    * Selects a set of column based expressions.
    * {{{
    *   ds.select($"colA", $"colB" + 1)
    * }}}
    *
    * @group untypedrel
    * @since 2.0.0
    */
  @scala.annotation.varargs
  def select(cols: Column*): Dataset = {
    null
  }

  /**
    * Selects a set of columns. This is a variant of `select` that can only select
    * existing columns using column names (i.e. cannot construct expressions).
    *
    * {{{
    *   // The following two are equivalent:
    *   ds.select("colA", "colB")
    *   ds.select($"colA", $"colB")
    * }}}
    *
    * @group untypedrel
    * @since 2.0.0
    */
  @scala.annotation.varargs
  def select(col: String, cols: String*): Dataset = null

  /**
    * Selects a set of SQL expressions. This is a variant of `select` that accepts
    * SQL expressions.
    *
    * {{{
    *   // The following are equivalent:
    *   ds.selectExpr("colA", "colB as newName", "abs(colC)")
    *   ds.select(expr("colA"), expr("colB as newName"), expr("abs(colC)"))
    * }}}
    *
    * @group untypedrel
    * @since 2.0.0
    */
  @scala.annotation.varargs
  def selectExpr(exprs: String*): DataFrame = {
    null
  }

  /**
    * Filters rows using the given condition.
    * {{{
    *   // The following are equivalent:
    *   peopleDs.filter($"age" > 15)
    *   peopleDs.where($"age" > 15)
    * }}}
    *
    * @group typedrel
    * @since 1.6.0
    */
  def filter(condition: Column): Dataset = {
    null
  }

  /**
    * Filters rows using the given SQL expression.
    * {{{
    *   peopleDs.filter("age > 15")
    * }}}
    *
    * @group typedrel
    * @since 1.6.0
    */
  def filter(conditionExpr: String): Dataset = {
    null
  }

  /**
    * Filters rows using the given condition. This is an alias for `filter`.
    * {{{
    *   // The following are equivalent:
    *   peopleDs.filter($"age" > 15)
    *   peopleDs.where($"age" > 15)
    * }}}
    *
    * @group typedrel
    * @since 1.6.0
    */
  def where(condition: Column): Dataset = filter(condition)

  /**
    * Filters rows using the given SQL expression.
    * {{{
    *   peopleDs.where("age > 15")
    * }}}
    *
    * @group typedrel
    * @since 1.6.0
    */
  def where(conditionExpr: String): Dataset = {
    null
  }

  /**
    * Groups the Dataset using the specified columns, so we can run aggregation on them. See
    * [[RelationalGroupedDataset]] for all the available aggregate functions.
    *
    * {{{
    *   // Compute the average for all numeric columns grouped by department.
    *   ds.groupBy($"department").avg()
    *
    *   // Compute the max age and average salary, grouped by department and gender.
    *   ds.groupBy($"department", $"gender").agg(Map(
    *     "salary" -> "avg",
    *     "age" -> "max"
    *   ))
    * }}}
    *
    * @group untypedrel
    * @since 2.0.0
    */
  @scala.annotation.varargs
  def groupBy(cols: Column*): Dataset = {
    null
  }

  def exector(): ResultSet = {
    null
  }

  def exector(obj: Any): ResultSet = {
    null
  }


}
