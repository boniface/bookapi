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
import domain.books.Subsection
import repository.books.SubsectionRepository
import repository.books.impl.cassandra.tables.{SubsectionByIdTable, SubsectionTable}

import scala.concurrent.Future

class SubsectionRepositoryImpl extends SubsectionRepository{

  override def saveEntity(entity: Subsection): Future[Boolean] = {
    SubsectionDatabase.SubsectionTable.saveEntity(entity).map(result => result.isExhausted())
    SubsectionDatabase.SubsectionByIdTable.saveEntity(entity).map(result => result.isExhausted())
  }

  override def getEntities: Future[Seq[Subsection]] =
    SubsectionDatabase.SubsectionTable.getEntities

  override def getSectionSubsections(sectionId: String): Future[Seq[Subsection]] =
    SubsectionDatabase.SubsectionTable.getSectionSubsections(sectionId)

  override def getEntity(subsectionId: String): Future[Option[Subsection]] =
    SubsectionDatabase.SubsectionByIdTable.getEntity(subsectionId)

  override def deleteEntity(entity: Subsection): Future[Boolean] = {
    SubsectionDatabase.SubsectionTable.deleteEntity(entity.sectionId, entity.subsectionId).map(result => result.isExhausted())
    SubsectionDatabase.SubsectionByIdTable.deleteEntity(entity.subsectionId).map(result => result.isExhausted())
  }

  override def createTable: Future[Boolean] = {

    implicit def keySpace: KeySpace = DataConnection.devKeySpaceQuery.keySpace

    implicit def session: Session = DataConnection.connector.session

    SubsectionDatabase.SubsectionTable.create.ifNotExists().future().map(result => result.head.isExhausted())
    SubsectionDatabase.SubsectionByIdTable.create.ifNotExists().future().map(result => result.head.isExhausted())
  }
}

class SubsectionDatabase(override val connector: KeySpaceDef) extends Database[SubsectionDatabase](connector) {

  object SubsectionTable extends SubsectionTable with connector.Connector
  object SubsectionByIdTable extends SubsectionByIdTable with connector.Connector

}

object SubsectionDatabase extends SubsectionDatabase(DataConnection.connector)
