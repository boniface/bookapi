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

  object chapterId extends StringColumn with PartitionKey

  object sectionId extends StringColumn with PrimaryKey

  object sectionTitle extends StringColumn

  object sectionDescription extends OptionalStringColumn

  object story extends OptionalStringColumn

  object dateCreated extends Col[LocalDateTime]

  /**
    * Save a section
    * @param entity
    * @return
    */
  def saveEntity(entity: Section): Future[ResultSet] = {
    insert
      .value(_.chapterId, entity.chapterId)
      .value(_.sectionId, entity.sectionId)
      .value(_.sectionTitle, entity.sectionTitle)
      .value(_.sectionDescription, entity.sectionDescription)
      .value(_.story, entity.story)
      .value(_.dateCreated, entity.dateCreated)
      .future()
  }

  /**
    * Delete a section
    * @param chapterId
    * @param sectionId
    * @return
    */
  def deleteEntity(chapterId: String, sectionId: String): Future[ResultSet] = {
    delete
      .where(_.chapterId eqs chapterId)
      .and(_.sectionId eqs sectionId)
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
    * Retrieve all sections in a give chapter
    * @param chapterId
    * @return
    */
  def getChapterSections(chapterId: String): Future[Seq[Section]] = {
    select
      .where(_.chapterId eqs chapterId)
      .fetchEnumerator() run Iteratee.collect()
  }

  /**
    * Fetch a section given a chapter id and section id
    * @param chapterId
    * @param sectionId
    * @return
    */
  def isSectionAvailable(chapterId: String, sectionId: String): Future[Option[Section]] = {
    select
      .where(_.chapterId eqs chapterId)
      .and(_.sectionId eqs sectionId)
      .one()
  }
}

abstract class SectionByIdTable extends Table[SectionByIdTable, Section] with RootConnector {

  override lazy val tableName = "sectionsbyid"

  object chapterId extends StringColumn with PrimaryKey

  object sectionId extends StringColumn with PartitionKey

  object sectionTitle extends StringColumn

  object sectionDescription extends OptionalStringColumn

  object story extends OptionalStringColumn

  object dateCreated extends Col[LocalDateTime]

  /**
    * Save a section
    * @param entity
    * @return
    */
  def saveEntity(entity: Section): Future[ResultSet] = {
    insert
      .value(_.chapterId, entity.chapterId)
      .value(_.sectionId, entity.sectionId)
      .value(_.sectionTitle, entity.sectionTitle)
      .value(_.sectionDescription, entity.sectionDescription)
      .value(_.story, entity.story)
      .value(_.dateCreated, entity.dateCreated)
      .future()
  }

  /**
    *
    * @param sectionId
    * @return
    */
  def getEntity(sectionId: String): Future[Option[Section]] = {
    select
      .where(_.sectionId eqs sectionId)
      .one()
  }

  /**
    *
    * @param sectionId
    * @return
    */
  def deleteEntity(sectionId: String): Future[ResultSet] = {
    delete
      .where(_.sectionId eqs sectionId)
      .future()
  }

}
