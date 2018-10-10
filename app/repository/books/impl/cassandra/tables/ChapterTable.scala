/*
 * Copyright (c) 2018/09/29 3:07 PM.
 * Author: caniksea
 * Project: bookapi
 * Last Modified: 2018/09/25 8:20 AM
 */

package repository.books.impl.cassandra.tables

import java.time.LocalDateTime

import com.outworkers.phantom.{ResultSet, Table}
import com.outworkers.phantom.connectors.RootConnector
import com.outworkers.phantom.dsl._
import com.outworkers.phantom.streams._
import com.outworkers.phantom.jdk8._
import domain.books.Chapter

import scala.concurrent.Future

abstract class ChapterTable extends Table[ChapterTable, Chapter] with RootConnector {

  override lazy val tableName = "chapters"

  object id extends StringColumn with PartitionKey

  object title extends StringColumn

  object description extends OptionalStringColumn

  object story extends OptionalStringColumn

  object sectionIds extends ListColumn[String]

  object multimedias extends ListColumn[String]

  object dateCreated extends Col[LocalDateTime]

  /**
    * Save a chapter
    * @param entity
    * @return
    */
  def saveEntity(entity: Chapter): Future[ResultSet] = {
    insert
      .value(_.id, entity.id)
      .value(_.title, entity.title)
      .value(_.description, entity.description)
      .value(_.story, entity.story)
      .value(_.sectionIds, entity.sectionIds)
      .value(_.multimedias, entity.multimedias)
      .value(_.dateCreated, entity.dateCreated)
      .future()
  }

  /**
    * Delete a chapter
    * @param id
    * @return
    */
  def deleteEntity(id: String): Future[ResultSet] = {
    delete
      .where(_.id eqs id)
      .future()
  }

  /**
    * Fetch all chapters
    * @return
    */
  def getEntities: Future[Seq[Chapter]] = {
    select.fetchEnumerator() run Iteratee.collect()
  }

  /**
    * Fetch a chapter
    * @param id
    * @return
    */
  def getEntity(id: String): Future[Option[Chapter]] = {
    select
      .where(_.id.eqs(id))
      .one()
  }

  /**
    * Fetch a list of chapters given IDs
    * @param ids
    * @return
    */
  def getEntitiesForIds(ids: List[String]): Future[Seq[Chapter]] = {
    select
      .where(_.id in ids).fetchEnumerator() run Iteratee.collect()
  }

}
