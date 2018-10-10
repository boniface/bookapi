package repository.users.Impl.cassandra

import com.outworkers.phantom.connectors.KeySpace
import com.outworkers.phantom.database.Database
import com.outworkers.phantom.dsl._
import configuration.connections.DataConnection
import domain.users.UserRole
import repository.users.Impl.cassandra.tables.{UserRoleTable}
import repository.users.UserRoleRepository

import scala.concurrent.Future


class UserRoleRepositoryImpl extends UserRoleRepository {

  override def createTable: Future[Boolean] = {
    implicit def keyspace: KeySpace = DataConnection.devKeySpaceQuery.keySpace
    implicit def session: Session = DataConnection.connector.session
    UserRoleDatabase.userRoleTable.create.ifNotExists().future().map(result => result.head.isExhausted())
  }

  override def saveEntity(entity: UserRole): Future[Boolean] = {
    UserRoleDatabase.userRoleTable.saveEntity(entity).map(result => result.isExhausted())
  }

  override def getEntities: Future[Seq[UserRole]] = {
    UserRoleDatabase.userRoleTable.getEntities
  }

  override def getEntity(id: String): Future[Option[UserRole]] = {
    UserRoleDatabase.userRoleTable.getEntity(id)
  }

  override def deleteEntity(entity: UserRole): Future[Boolean] = {
    UserRoleDatabase.userRoleTable.deleteEntity(entity.userId).map(result => result.isExhausted())
  }
}


class UserRoleDatabase(override val connector: KeySpaceDef) extends Database[UserRoleDatabase](connector) {

  object userRoleTable extends UserRoleTable with connector.Connector
}

object UserRoleDatabase extends UserRoleDatabase(DataConnection.connector)
