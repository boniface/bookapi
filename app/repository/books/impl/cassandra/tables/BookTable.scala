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

  object bookId extends StringColumn with PrimaryKey

  object bookTitle extends StringColumn

  object bookDescription extends OptionalStringColumn

  object story extends OptionalStringColumn

  object isbn extends OptionalStringColumn

  object eisbn extends OptionalStringColumn

  object author extends StringColumn

  object publisher extends StringColumn

  object datePublished extends Col[LocalDateTime]

  object dateCreated extends Col[LocalDateTime]

  /**
    * Insert new book
    * @param entity
    * @return
    */
  def saveEntity(entity: Book): Future[ResultSet] = {
    insert
      .value(_.siteId, entity.siteId)
      .value(_.bookId, entity.bookId)
      .value(_.bookTitle, entity.bookTitle)
      .value(_.bookDescription, entity.bookDescription)
      .value(_.story, entity.story)
      .value(_.isbn, entity.isbn)
      .value(_.eisbn, entity.eisbn)
      .value(_.author, entity.author)
      .value(_.publisher, entity.publisher)
      .value(_.datePublished, entity.datePublished)
      .value(_.dateCreated, entity.dateCreated)
      .future()
  }

  /**
    * Retrieve all books in a give site
    * @param siteId
    * @return
    */
  def getSiteEntities(siteId: String): Future[Seq[Book]] = {
    select
      .where(_.siteId eqs siteId)
      .fetchEnumerator() run Iteratee.collect()
  }

  /**
    * Retrieve a book from site
    * @param siteId
    * @param bookId
    * @return
    */
  def isBookAvailable(siteId: String, bookId: String): Future[Option[Book]] = {
    select
      .where(_.siteId eqs siteId)
      .and(_.bookId eqs bookId)
      .one()
  }

  /**
    * Retrieve all books
    * @return
    */
  def getEntities: Future[Seq[Book]] = {
    select.fetchEnumerator() run Iteratee.collect()
  }

  /**
    *
    * @param siteId
    * @param bookId
    * @return
    */
  def deleteEntity(siteId: String, bookId: String): Future[ResultSet] = {
    delete
      .where(_.siteId eqs siteId)
      .and(_.bookId eqs bookId)
      .future()
  }

  /**
    *
    * @param siteId
    * @return
    */
  def getBookCountForSite(siteId: String): Future[Option[Long]] = {
    select
      .count().where(_.siteId eqs siteId)
      .one()
  }


}

abstract class BookByIdTable extends Table[BookByIdTable, Book] with RootConnector {

  override lazy val tableName = "booksbyid"

  object siteId extends StringColumn with PrimaryKey

  object bookId extends StringColumn with PartitionKey

  object bookTitle extends StringColumn

  object bookDescription extends OptionalStringColumn

  object story extends OptionalStringColumn

  object isbn extends OptionalStringColumn

  object eisbn extends OptionalStringColumn

  object author extends StringColumn

  object publisher extends StringColumn

  object datePublished extends Col[LocalDateTime]

  object dateCreated extends Col[LocalDateTime]

  /**
    * Insert a new book
    * @param entity
    * @return
    */
  def saveEntity(entity: Book): Future[ResultSet] = {
    insert
      .value(_.siteId, entity.siteId)
      .value(_.bookId, entity.bookId)
      .value(_.bookTitle, entity.bookTitle)
      .value(_.bookDescription, entity.bookDescription)
      .value(_.story, entity.story)
      .value(_.isbn, entity.isbn)
      .value(_.eisbn, entity.eisbn)
      .value(_.author, entity.author)
      .value(_.publisher, entity.publisher)
      .value(_.datePublished, entity.datePublished)
      .value(_.dateCreated, entity.dateCreated)
      .future()
  }

  /**
    * Get a book
    * @param bookId
    * @return
    */
  def getEntity(bookId: String): Future[Option[Book]] = {
    select
      .where(_.bookId eqs bookId)
      .one()
  }

  /**
    * Delete a book
    * @param bookId
    * @return
    */
  def deleteEntity(bookId: String): Future[ResultSet] = {
    delete
      .where(_.bookId eqs bookId)
      .future()
  }
}