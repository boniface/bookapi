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
import repository.books.impl.cassandra.tables.{SubsectionTable}

import scala.concurrent.Future

class SubsectionRepositoryImpl extends SubsectionRepository{
  override def saveEntity(entity: Subsection): Future[Boolean] =
    SubsectionDatabase.SubsectionTable.saveEntity(entity).map(result => result.isExhausted())

  override def getEntities: Future[Seq[Subsection]] =
    SubsectionDatabase.SubsectionTable.getEntities

  override def getEntitiesForIds(ids: List[String]): Future[Seq[Subsection]] =
    SubsectionDatabase.SubsectionTable.getEntitiesForIds(ids)

  override def getEntity(id: String): Future[Option[Subsection]] =
    SubsectionDatabase.SubsectionTable.getEntity(id)

  override def deleteEntity(entity: Subsection): Future[Boolean] =
    SubsectionDatabase.SubsectionTable.deleteEntity(entity.id).map(result => result.isExhausted())

  override def createTable: Future[Boolean] = {

    implicit def keySpace: KeySpace = DataConnection.devKeySpaceQuery.keySpace

    implicit def session: Session = DataConnection.connector.session

    SubsectionDatabase.SubsectionTable.create.ifNotExists().future().map(result => result.head.isExhausted())
  }
}

class SubsectionDatabase(override val connector: KeySpaceDef) extends Database[SubsectionDatabase](connector) {

  object SubsectionTable extends SubsectionTable with connector.Connector

}

object SubsectionDatabase extends SubsectionDatabase(DataConnection.connector)
