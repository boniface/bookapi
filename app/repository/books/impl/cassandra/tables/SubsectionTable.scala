/*
 * Copyright (c) 2018/09/29 3:13 PM.
 * Author: caniksea
 * Project: bookapi
 * Last Modified: 2018/09/29 3:13 PM
 */

package repository.books.impl.cassandra.tables

import java.time.LocalDateTime

import com.outworkers.phantom.{ResultSet, Table}
import com.outworkers.phantom.connectors.RootConnector
import com.outworkers.phantom.dsl._
import com.outworkers.phantom.streams._
import com.outworkers.phantom.jdk8._
import domain.books.{Subsection}

import scala.concurrent.Future

abstract class SubsectionTable extends Table[SubsectionTable, Subsection] with RootConnector {

  override lazy val tableName = "subsections"

  object sectionId extends StringColumn with PartitionKey

  object subsectionId extends StringColumn with PrimaryKey

  object subsectionTitle extends StringColumn

  object subsectionDescription extends OptionalStringColumn

  object story extends OptionalStringColumn

  object dateCreated extends Col[LocalDateTime]

  /**
    * Save a subsection
    *
    * @param entity
    * @return
    */
  def saveEntity(entity: Subsection): Future[ResultSet] = {
    insert
      .value(_.sectionId, entity.sectionId)
      .value(_.subsectionId, entity.subsectionId)
      .value(_.subsectionTitle, entity.subsectionTitle)
      .value(_.subsectionDescription, entity.subsectionDescription)
      .value(_.story, entity.story)
      .value(_.dateCreated, entity.dateCreated)
      .future()
  }

  /**
    * Delete a subsection
    *
    * @param sectionId
    * @param subsectionId
    * @return
    */
  def deleteEntity(sectionId: String, subsectionId: String): Future[ResultSet] = {
    delete
      .where(_.sectionId eqs sectionId)
      .and(_.subsectionId eqs subsectionId)
      .future()
  }

  /**
    * Get all subsections
    *
    * @return
    */
  def getEntities: Future[Seq[Subsection]] = {
    select.fetchEnumerator() run Iteratee.collect()
  }

  /**
    * Get a subsection
    *
    * @param sectionId
    * @param subsectionId
    * @return
    */
  def isSubsectionAvailable(sectionId: String, subsectionId: String): Future[Option[Subsection]] = {
    select
      .where(_.sectionId eqs sectionId)
      .and(_.subsectionId eqs subsectionId)
      .one()
  }

  /**
    * Retrieve all subsections in a given section
    *
    * @param sectionId
    * @return
    */
  def getSectionSubsections(sectionId: String): Future[Seq[Subsection]] = {
    select
      .where(_.sectionId eqs sectionId)
      .fetchEnumerator() run Iteratee.collect()
  }
}

abstract class SubsectionByIdTable extends Table[SubsectionByIdTable, Subsection] with RootConnector {

  override lazy val tableName = "subsectionsbyid"

  object sectionId extends StringColumn with PrimaryKey

  object subsectionId extends StringColumn with PartitionKey

  object subsectionTitle extends StringColumn

  object subsectionDescription extends OptionalStringColumn

  object story extends OptionalStringColumn

  object dateCreated extends Col[LocalDateTime]

  /**
    * Save a subsection
    *
    * @param entity
    * @return
    */
  def saveEntity(entity: Subsection): Future[ResultSet] = {
    insert
      .value(_.sectionId, entity.sectionId)
      .value(_.subsectionId, entity.subsectionId)
      .value(_.subsectionTitle, entity.subsectionTitle)
      .value(_.subsectionDescription, entity.subsectionDescription)
      .value(_.story, entity.story)
      .value(_.dateCreated, entity.dateCreated)
      .future()
  }

  /**
    *
    * @param subsectionId
    * @return
    */
  def getEntity(subsectionId: String): Future[Option[Subsection]] = {
    select
      .where(_.subsectionId eqs subsectionId)
      .one()
  }

  /**
    *
    * @param subsectionId
    * @return
    */
  def deleteEntity(subsectionId: String): Future[ResultSet] = {
    delete
      .where(_.subsectionId eqs subsectionId)
      .future()
  }


}