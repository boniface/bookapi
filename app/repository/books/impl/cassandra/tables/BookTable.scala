/*
 * Copyright (c) 2018/09/29 3:05 PM.
 * Author: caniksea
 * Project: bookapi
 * Last Modified: 2018/09/25 8:18 AM
 */

package repository.books.impl.cassandra.tables

import java.time.LocalDateTime

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.streams._
import com.outworkers.phantom.jdk8._
import domain.books.{Book}

import scala.concurrent.Future


abstract class BookTable extends Table[BookTable, Book] with RootConnector  {

  override lazy val tableName = "books"

  object siteId extends StringColumn with PartitionKey

  object id extends StringColumn with PrimaryKey

  object title extends StringColumn

  object isbn extends StringColumn

  object eisbn extends StringColumn

  object author extends StringColumn

  object publisher extends StringColumn

  object description extends OptionalStringColumn

  object story extends OptionalStringColumn

  object datePublished extends Col[LocalDateTime]

  object chapterIds extends ListColumn[String]

  object multimedias extends ListColumn[String]

  object dateCreated extends Col[LocalDateTime]

  /**
    * Insert new book
    * @param entity
    * @return
    */
  def saveEntity(entity: Book): Future[ResultSet] = {
    insert
      .value(_.id, entity.id)
      .value(_.siteId, entity.siteId)
      .value(_.title, entity.title)
      .value(_.isbn, entity.isbn)
      .value(_.eisbn, entity.eisbn)
      .value(_.author, entity.author)
      .value(_.publisher, entity.publisher)
      .value(_.description, entity.description)
      .value(_.story, entity.story)
      .value(_.datePublished, entity.datePublished)
      .value(_.chapterIds, entity.chapterIds)
      .value(_.multimedias, entity.multimedias)
      .value(_.dateCreated, entity.dateCreated)
      .future()
  }

  /**
    * Retrieve all books in a give site
    * @param siteId
    * @return
    */
  def retrieveSiteBooks(siteId: String): Future[Seq[Book]] = {
    select
      .where(_.siteId eqs siteId)
      .fetchEnumerator() run Iteratee.collect()
  }

  /**
    * Retrieve a book from site
    * @param siteId
    * @param id
    * @return
    */
  def isBookAvailable(siteId: String, id: String): Future[Seq[Book]] = {
    select
      .where(_.siteId eqs siteId)
      .and(_.id eqs id)
      .fetchEnumerator() run Iteratee.collect()
  }

  /**
    * Retrieve all books
    * @return
    */
  def getEntities: Future[Seq[Book]] = {
    select.fetchEnumerator() run Iteratee.collect()
  }

  /**
    * Delete a book from a site
    * @param siteId
    * @param id
    * @return
    */
  def deleteEntity(siteId: String, id: String): Future[ResultSet] = {
    delete
      .where(_.siteId eqs siteId)
      .and(_.id eqs id)
      .future()
  }


}

abstract class BookByIdTable extends Table[BookByIdTable, Book] with RootConnector {

  override lazy val tableName = "booksbyids"

  object siteId extends StringColumn with PrimaryKey

  object id extends StringColumn with PartitionKey

  object title extends StringColumn

  object isbn extends StringColumn

  object eisbn extends StringColumn

  object author extends StringColumn

  object publisher extends StringColumn

  object description extends OptionalStringColumn

  object story extends OptionalStringColumn

  object datePublished extends Col[LocalDateTime]

  object chapterIds extends ListColumn[String]

  object multimedias extends ListColumn[String]

  object dateCreated extends Col[LocalDateTime]

  /**
    * Insert a new book
    * @param entity
    * @return
    */
  def saveEntity(entity: Book): Future[ResultSet] = {
    insert
      .value(_.id, entity.id)
      .value(_.siteId, entity.siteId)
      .value(_.title, entity.title)
      .value(_.isbn, entity.isbn)
      .value(_.eisbn, entity.eisbn)
      .value(_.author, entity.author)
      .value(_.publisher, entity.publisher)
      .value(_.description, entity.description)
      .value(_.story, entity.story)
      .value(_.datePublished, entity.datePublished)
      .value(_.chapterIds, entity.chapterIds)
      .value(_.multimedias, entity.multimedias)
      .value(_.dateCreated, entity.dateCreated)
      .future()
  }

  /**
    * Get a book
    * @param id
    * @return
    */
  def getEntity(id: String): Future[Option[Book]] = {
    select
      .where(_.id eqs id)
      .one()
  }

  /**
    * Delete a book
    * @param id
    * @return
    */
  def deleteEntity(id: String): Future[ResultSet] = {
    delete
      .where(_.id eqs id)
      .future()
  }
}