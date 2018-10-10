/*
 * Copyright (c) 2018/09/29 3:03 PM.
 * Author: caniksea
 * Project: bookapi
 * Last Modified: 2018/09/21 9:58 AM
 */

package repository.books.impl.cassandra

import com.outworkers.phantom.connectors.KeySpace
import com.outworkers.phantom.database.Database
import com.outworkers.phantom.dsl._
import configuration.connections.DataConnection
import domain.books.Book
import repository.books.BookRepository
import repository.books.impl.cassandra.tables.{BookByIdTable, BookTable}

import scala.concurrent.Future

/**
  *
  */
class BookRepositoryImpl extends BookRepository {
  override def saveEntity(entity: Book): Future[Boolean] = {
    BookDatabase.BookTable.saveEntity(entity).map(result => result.isExhausted())
    BookDatabase.BookByIdTable.saveEntity(entity).map(result => result.isExhausted())
  }

  override def getSiteEntities(siteId: String): Future[Seq[Book]] = BookDatabase.BookTable.retrieveSiteBooks(siteId)

  override def getEntities: Future[Seq[Book]] = BookDatabase.BookTable.getEntities

  override def getEntity(id: String): Future[Option[Book]] = BookDatabase.BookByIdTable.getEntity(id)

  override def deleteEntity(entity: Book): Future[Boolean] = {
    BookDatabase.BookTable.deleteEntity(entity.siteId, entity.id).map(result => result.isExhausted())
    BookDatabase.BookByIdTable.deleteEntity(entity.id).map(result => result.isExhausted())
  }

  override def createTable: Future[Boolean] = {
    implicit def keySpace: KeySpace = DataConnection.devKeySpaceQuery.keySpace

    implicit def session: Session = DataConnection.connector.session

    BookDatabase.BookTable.create.ifNotExists().future().map(result => result.head.isExhausted())
    BookDatabase.BookByIdTable.create.ifNotExists().future().map(result => result.head.isExhausted())
  }
}

/**
  *
  * @param connector
  */
class BookDatabase(override val connector: KeySpaceDef) extends Database[BookDatabase](connector) {

  object BookTable extends BookTable with connector.Connector

  object BookByIdTable extends BookByIdTable with connector.Connector

}

object BookDatabase extends BookDatabase(DataConnection.connector)