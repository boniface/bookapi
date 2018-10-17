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

  object bookId extends StringColumn with PartitionKey

  object chapterId extends StringColumn with PrimaryKey

  object chapterTitle extends StringColumn

  object chapterDescription extends OptionalStringColumn

  object story extends OptionalStringColumn

  object dateCreated extends Col[LocalDateTime]

  /**
    * Save a chapter
    * @param entity
    * @return
    */
  def saveEntity(entity: Chapter): Future[ResultSet] = {
    insert
      .value(_.bookId, entity.bookId)
      .value(_.chapterId, entity.chapterId)
      .value(_.chapterTitle, entity.chapterTitle)
      .value(_.chapterDescription, entity.chapterDescription)
      .value(_.story, entity.story)
      .value(_.dateCreated, entity.dateCreated)
      .future()
  }

  /**
    * Delete a chapter given book id and chapter id
    * @param bookId
    * @param chapterId
    * @return
    */
  def deleteEntity(bookId: String, chapterId: String): Future[ResultSet] = {
    delete
      .where(_.bookId eqs bookId)
      .and(_.chapterId eqs chapterId)
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
    * Retrieve all chapters in a give book
    * @param bookId
    * @return
    */
  def getBookChapters(bookId: String): Future[Seq[Chapter]] = {
    select
      .where(_.bookId eqs bookId)
      .fetchEnumerator() run Iteratee.collect()
  }

  /**
    * Fetch a chapter
    * @param id
    * @return
    */
  def isChapterAvailable(bookId: String, chapterId: String): Future[Option[Chapter]] = {
    select
      .where(_.bookId.eqs(bookId))
      .and(_.chapterId.eqs(chapterId))
      .one()
  }

}


abstract class ChapterByIdTable extends Table[ChapterByIdTable, Chapter] with RootConnector {

  override lazy val tableName = "chaptersbyid"

  object bookId extends StringColumn with PrimaryKey

  object chapterId extends StringColumn with PartitionKey

  object chapterTitle extends StringColumn

  object chapterDescription extends OptionalStringColumn

  object story extends OptionalStringColumn

  object dateCreated extends Col[LocalDateTime]

  /**
    * Save a chapter
    * @param entity
    * @return
    */
  def saveEntity(entity: Chapter): Future[ResultSet] = {
    insert
      .value(_.bookId, entity.bookId)
      .value(_.chapterId, entity.chapterId)
      .value(_.chapterTitle, entity.chapterTitle)
      .value(_.chapterDescription, entity.chapterDescription)
      .value(_.story, entity.story)
      .value(_.dateCreated, entity.dateCreated)
      .future()
  }

  /**
    *
    * @param chapterId
    * @return
    */
  def getEntity(chapterId: String): Future[Option[Chapter]] = {
    select
      .where(_.chapterId eqs chapterId)
      .one()
  }

  /**
    *
    * @param chapterId
    * @return
    */
  def deleteEntity(chapterId: String): Future[ResultSet] = {
    delete
      .where(_.chapterId eqs chapterId)
      .future()
  }

}