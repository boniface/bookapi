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
import repository.books.impl.cassandra.tables.{ChapterTable}

import scala.concurrent.Future

class ChapterRepositoryImpl extends ChapterRepository {
  override def saveEntity(entity: Chapter): Future[Boolean] =
    ChapterDatabase.ChapterTable.saveEntity(entity).map(result => result.isExhausted())

  override def getEntities: Future[Seq[Chapter]] =
    ChapterDatabase.ChapterTable.getEntities

  override def getEntity(id: String): Future[Option[Chapter]] = ChapterDatabase.ChapterTable.getEntity(id)

  override def getEntitiesForIds(ids: List[String]): Future[Seq[Chapter]] = ChapterDatabase.ChapterTable.getEntitiesForIds(ids)

  override def deleteEntity(entity: Chapter): Future[Boolean] =
    ChapterDatabase.ChapterTable.deleteEntity(entity.id).map(result => result.isExhausted())

  override def createTable: Future[Boolean] = {
    implicit def keySpace: KeySpace = DataConnection.devKeySpaceQuery.keySpace

    implicit def session: Session = DataConnection.connector.session

    ChapterDatabase.ChapterTable.create.ifNotExists().future().map(result => result.head.isExhausted())
  }
}

class ChapterDatabase(override val connector: KeySpaceDef) extends Database[ChapterDatabase](connector) {

  object ChapterTable extends ChapterTable with connector.Connector

}

object ChapterDatabase extends ChapterDatabase(DataConnection.connector)
