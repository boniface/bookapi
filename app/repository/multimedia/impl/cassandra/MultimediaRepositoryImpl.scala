/*
 * Copyright (c) 2018/09/29 3:04 PM.
 * Author: caniksea
 * Project: bookapi
 * Last Modified: 2018/09/25 8:44 AM
 */

package repository.multimedia.impl.cassandra

import com.outworkers.phantom.connectors.KeySpace
import com.outworkers.phantom.database.Database
import com.outworkers.phantom.dsl._
import configuration.connections.DataConnection
import domain.multimedia.Multimedia
import repository.multimedia.MultimediaRepository
import repository.multimedia.impl.cassandra.tables.MultimediaTable

import scala.concurrent.Future

class MultimediaRepositoryImpl extends MultimediaRepository {
  override def saveEntity(entity: Multimedia): Future[Boolean] =
    MultimediaDatabase.MultimediaTable.saveEntity(entity).map(result => result.isExhausted())

  override def getEntities: Future[Seq[Multimedia]] =
    MultimediaDatabase.MultimediaTable.getEntities()

  override def getEntity(id: String): Future[Option[Multimedia]] =
    MultimediaDatabase.MultimediaTable.getEntity(id)

  override def deleteEntity(entity: Multimedia): Future[Boolean] =
    MultimediaDatabase.MultimediaTable.deleteEntity(entity.multimediaId).map(result => result.isExhausted())

  override def createTable: Future[Boolean] = {

    implicit def keySpace: KeySpace = DataConnection.devKeySpaceQuery.keySpace

    implicit def session: Session = DataConnection.connector.session

    MultimediaDatabase.MultimediaTable.create.ifNotExists().future().map(result => result.head.isExhausted())
  }
}

class MultimediaDatabase(override val connector: KeySpaceDef) extends Database[MultimediaDatabase] (connector) {

  object MultimediaTable extends MultimediaTable with connector.Connector

}

object MultimediaDatabase extends MultimediaDatabase(DataConnection.connector)
