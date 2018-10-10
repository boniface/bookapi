/*
 * Copyright (c) 2018/09/29 3:11 PM.
 * Author: caniksea
 * Project: bookapi
 * Last Modified: 2018/09/25 8:25 AM
 */

package repository.books.impl.cassandra.tables

import java.time.LocalDateTime

import com.outworkers.phantom.{ResultSet, Table}
import com.outworkers.phantom.connectors.RootConnector
import com.outworkers.phantom.dsl._
import com.outworkers.phantom.streams._
import com.outworkers.phantom.jdk8._
import domain.books.Section

import scala.concurrent.Future

abstract class SectionTable extends Table[SectionTable, Section] with RootConnector {

  override lazy val tableName = "sections"

  object id extends StringColumn with PartitionKey

  object title extends StringColumn

  object description extends OptionalStringColumn

  object story extends OptionalStringColumn

  object subsectionIds extends ListColumn[String]

  object multimedias extends ListColumn[String]

  object dateCreated extends Col[LocalDateTime]

  /**
    * Save a section
    * @param entity
    * @return
    */
  def saveEntity(entity: Section): Future[ResultSet] = {
    insert
      .value(_.id, entity.id)
      .value(_.title, entity.title)
      .value(_.description, entity.description)
      .value(_.story, entity.story)
      .value(_.subsectionIds, entity.subsectionIds)
      .value(_.multimedias, entity.multimedias)
      .value(_.dateCreated, entity.dateCreated)
      .future()
  }

  /**
    * Delete a section
    * @param id
    * @return
    */
  def deleteEntity(id: String): Future[ResultSet] = {
    delete
      .where(_.id eqs id)
      .future()
  }

  /**
    * Fetch all sections
    * @return
    */
  def getEntities: Future[Seq[Section]] = {
    select.fetchEnumerator() run Iteratee.collect()
  }

  /**
    * Fetch a section
    * @param id
    * @return
    */
  def getEntity(id: String): Future[Option[Section]] = {
    select
      .where(_.id.eqs(id))
      .one()
  }

  /**
    * Fetch sections given IDs
    * @param ids
    * @return
    */
  def getEntitiesForIds(ids: List[String]): Future[Seq[Section]] = {
    select
      .where(_.id in ids).fetchEnumerator() run Iteratee.collect()
  }
}
