package repository.security.Impl.cassandra

import com.outworkers.phantom.connectors.KeySpace
import com.outworkers.phantom.database.Database
import com.outworkers.phantom.dsl._
import configuration.connections.DataConnection
import domain.security.ApiKeys
import repository.security.ApiKeysRepository
import repository.security.Impl.cassandra.tables.{ApiKeysTable}

import scala.concurrent.Future

class ApiKeysRepositoryImpl extends ApiKeysRepository {
  override def saveEntity(entity: ApiKeys): Future[Boolean] = {
    ApiKeysDatabase.apiKeysTable.saveEntity(entity).map(result => result.isExhausted())
  }

  override def getEntities: Future[Seq[ApiKeys]] = {
    ApiKeysDatabase.apiKeysTable.getEntities
  }

  override def getEntity(id: String): Future[Option[ApiKeys]] = {
    ApiKeysDatabase.apiKeysTable.getEntity(id)
  }

  override def deleteEntity(entity: ApiKeys): Future[Boolean] = {
    ApiKeysDatabase.apiKeysTable.deleteEntity(entity.id).map(result => result.isExhausted())
  }

  override def createTable: Future[Boolean] = {
    implicit def keyspace: KeySpace = DataConnection.devKeySpaceQuery.keySpace

    implicit def session: Session = DataConnection.connector.session

    ApiKeysDatabase.apiKeysTable.create.ifNotExists().future().map(result => result.head.isExhausted())
  }
}


class ApiKeysDatabase(override val connector: KeySpaceDef) extends Database[ApiKeysDatabase](connector) {

  object apiKeysTable extends ApiKeysTable with connector.Connector

}

object ApiKeysDatabase extends ApiKeysDatabase(DataConnection.connector)