package repository.security.Impl.cassandra

import com.outworkers.phantom.connectors.KeySpace
import com.outworkers.phantom.database.Database
import com.outworkers.phantom.dsl._
import configuration.connections.DataConnection
import domain.security.UserToken
import repository.security.Impl.cassandra.tables.{UserTokenByIdTable, UserTokenTable}
import repository.security.UserTokenRepository

import scala.concurrent.Future

class UserTokenRepositoryImpl extends UserTokenRepository{

  override def saveEntity(entity: UserToken): Future[Boolean] = {
    UserTokenDatabase.userTokenTable.saveEntity(entity).map(result => result.isExhausted())
    UserTokenDatabase.userTokenByIdTable.saveEntity(entity).map(result => result.isExhausted())
  }

  override def getEntities: Future[Seq[UserToken]] = {
    UserTokenDatabase.userTokenTable.getEntities
  }

  override def getEntity(id: String): Future[Option[UserToken]] = {
    UserTokenDatabase.userTokenByIdTable.getEntity(id)
  }

  override def deleteEntity(entity: UserToken): Future[Boolean] = {
    UserTokenDatabase.userTokenTable.deleteEntity(entity.siteId,entity.id).map(result => result.isExhausted())
    UserTokenDatabase.userTokenByIdTable.deleteEntity(entity.id).map(result => result.isExhausted())

  }

  override def createTable: Future[Boolean] = {
    implicit def keyspace: KeySpace = DataConnection.devKeySpaceQuery.keySpace
    implicit def session: Session = DataConnection.connector.session
    UserTokenDatabase.userTokenTable.create.ifNotExists().future().map(result => result.head.isExhausted())
    UserTokenDatabase.userTokenByIdTable.create.ifNotExists().future().map(result => result.head.isExhausted())
  }


  override def getUserTokens(userId: String): Future[Seq[UserToken]] = ???
}

class UserTokenDatabase(override val connector: KeySpaceDef) extends Database[UserTokenDatabase](connector) {
  object userTokenTable extends UserTokenTable with connector.Connector
  object userTokenByIdTable extends UserTokenByIdTable with connector.Connector
}

object UserTokenDatabase extends UserTokenDatabase(DataConnection.connector)