/*
 * Copyright (c) 2018/09/29 3:04 PM.
 * Author: caniksea
 * Project: bookapi
 * Last Modified: 2018/09/24 4:12 PM
 */

package repository.books.impl.cassandra

import com.outworkers.phantom.connectors.KeySpace
import com.outworkers.phantom.database.Database
import com.outworkers.phantom.dsl._
import configuration.connections.DataConnection
import domain.books.Chapter
import repository.books.ChapterRepository
import repository.books.impl.cassandra.tables.{ChapterByIdTable, ChapterTable}

import scala.concurrent.Future

class ChapterRepositoryImpl extends ChapterRepository {
  override def saveEntity(entity: Chapter): Future[Boolean] = {
    ChapterDatabase.ChapterTable.saveEntity(entity).map(result => result.isExhausted())
    ChapterDatabase.ChapterByIdTable.saveEntity(entity).map(result => result.isExhausted())
  }

  override def getEntities: Future[Seq[Chapter]] =
    ChapterDatabase.ChapterTable.getEntities

  override def getEntity(chapterId: String): Future[Option[Chapter]] =
    ChapterDatabase.ChapterByIdTable.getEntity(chapterId)

  override def getBookChapters(bookId: String): Future[Seq[Chapter]] = ChapterDatabase.ChapterTable.getBookChapters(bookId)

  override def deleteEntity(entity: Chapter): Future[Boolean] = {
    ChapterDatabase.ChapterTable.deleteEntity(entity.bookId, entity.chapterId).map(result => result.isExhausted())
    ChapterDatabase.ChapterByIdTable.deleteEntity(entity.chapterId).map(result => result.isExhausted())
  }

  override def createTable: Future[Boolean] = {
    implicit def keySpace: KeySpace = DataConnection.devKeySpaceQuery.keySpace

    implicit def session: Session = DataConnection.connector.session

    ChapterDatabase.ChapterTable.create.ifNotExists().future().map(result => result.head.isExhausted())
    ChapterDatabase.ChapterByIdTable.create.ifNotExists().future().map(result => result.head.isExhausted())
  }
}

class ChapterDatabase(override val connector: KeySpaceDef) extends Database[ChapterDatabase](connector) {

  object ChapterTable extends ChapterTable with connector.Connector

  object ChapterByIdTable extends ChapterByIdTable with connector.Connector

}

object ChapterDatabase extends ChapterDatabase(DataConnection.connector)
