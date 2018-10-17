package repository.users.Impl.cassandra

import com.outworkers.phantom.connectors.KeySpace
import com.outworkers.phantom.database.Database
import com.outworkers.phantom.dsl._
import configuration.connections.DataConnection
import domain.users.User
import repository.users.Impl.cassandra.tables._
import repository.users.UserRepository

import scala.concurrent.Future

class UserRepositoryImpl extends UserRepository {
  override def saveEntity(entity: User): Future[Boolean] = {
    UserDatabase.userTable.saveEntity(entity).map(result => result.isExhausted())
    UserDatabase.userByEmailTable.saveEntity(entity).map(result => result.isExhausted())
    UserDatabase.userByIdTable.saveEntity(entity).map(result => result.isExhausted())
  }

  override def getEntities: Future[Seq[User]] = {
    UserDatabase.userTable.getEntities
  }

  override def getEntity(userId: String): Future[Option[User]] = {
    UserDatabase.userByIdTable.getEntity(userId)
  }

  override def deleteEntity(entity: User): Future[Boolean] = {
    UserDatabase.userTable.deleteEntity(entity.siteId, entity.userId, entity.email).map(result => result.isExhausted())
    UserDatabase.userByEmailTable.deleteEntity(entity.email, entity.userId, entity.siteId).map(result => result.isExhausted())
    UserDatabase.userByIdTable.deleteEntity(entity.userId).map(result => result.isExhausted())
  }

  override def createTable: Future[Boolean] = {
    implicit def keyspace: KeySpace = DataConnection.devKeySpaceQuery.keySpace

    implicit def session: Session = DataConnection.connector.session

    UserDatabase.userTable.create.ifNotExists().future().map(result => result.head.isExhausted())
    UserDatabase.userByEmailTable.create.ifNotExists().future().map(result => result.head.isExhausted())
    UserDatabase.userByIdTable.create.ifNotExists().future().map(result => result.head.isExhausted())
  }


}

class UserDatabase(override val connector: KeySpaceDef) extends Database[UserDatabase](connector) {

  object userTable extends UserTable with connector.Connector

  object userByEmailTable extends UserByEmailTable with connector.Connector

  object userByIdTable extends UserByIdTable with connector.Connector

}

object UserDatabase extends UserDatabase(DataConnection.connector)
