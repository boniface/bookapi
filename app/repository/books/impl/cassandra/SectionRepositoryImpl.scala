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
import domain.books.Section
import repository.books.SectionRepository
import repository.books.impl.cassandra.tables.{SectionByIdTable, SectionTable}

import scala.concurrent.Future

class SectionRepositoryImpl extends SectionRepository {
  override def saveEntity(entity: Section): Future[Boolean] = {
    SectionDatabase.SectionTable.saveEntity(entity).map(result => result.isExhausted())
    SectionDatabase.SectionByIdTable.saveEntity(entity).map(result => result.isExhausted())
  }

  override def getEntities: Future[Seq[Section]] =
    SectionDatabase.SectionTable.getEntities

  override def getChapterSections(chapterId: String): Future[Seq[Section]] =
    SectionDatabase.SectionTable.getChapterSections(chapterId)

  override def getEntity(sectionId: String): Future[Option[Section]] =
    SectionDatabase.SectionByIdTable.getEntity(sectionId)

  override def deleteEntity(entity: Section): Future[Boolean] = {
    SectionDatabase.SectionTable.deleteEntity(entity.chapterId, entity.sectionId).map(result => result.isExhausted())
    SectionDatabase.SectionByIdTable.deleteEntity(entity.sectionId).map(result => result.isExhausted())
  }

  override def createTable: Future[Boolean] = {

    implicit def keySpace: KeySpace = DataConnection.devKeySpaceQuery.keySpace

    implicit def session: Session = DataConnection.connector.session

    SectionDatabase.SectionTable.create.ifNotExists().future().map(result => result.head.isExhausted())
    SectionDatabase.SectionByIdTable.create.ifNotExists().future().map(result => result.head.isExhausted())
  }
}

class SectionDatabase(override val connector: KeySpaceDef) extends Database[SectionDatabase](connector){

  object SectionTable extends SectionTable with connector.Connector

  object SectionByIdTable extends SectionByIdTable with connector.Connector

}

object SectionDatabase extends SectionDatabase(DataConnection.connector)
