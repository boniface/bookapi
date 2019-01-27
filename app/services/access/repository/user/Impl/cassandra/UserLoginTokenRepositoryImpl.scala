package services.access.repository.user.Impl.cassandra

import com.outworkers.phantom.connectors.KeySpace
import com.outworkers.phantom.dsl._
import configuration.connections.DataConnection
import domain.access.users.UserLoginToken
import services.access.repository.user.UserLoginTokenRepository

import scala.concurrent.Future

class UserLoginTokenRepositoryImpl extends UserLoginTokenRepository {

  lazy val table = UserLoginTokenDatabase.userLoginTokenTable

  override def saveEntity(entity: UserLoginToken): Future[Boolean] = {
    table.saveEntity(entity) map (result => result.isExhausted())
  }

  override def getEntities: Future[Seq[UserLoginToken]] = ???

  override def getEntity(id: String): Future[Option[UserLoginToken]] = ???

  override def getTokenInfo(userId: String, hashedAgent:String ):Future[Option[UserLoginToken]] ={
    table.getEntity(userId,hashedAgent)
  }

  override def deleteEntity(entity: UserLoginToken): Future[Boolean] = {
    table.deleteEntity(entity.userId, entity.hashedAgent).map( result => result.isExhausted())
  }

  override def createTable: Future[Boolean] = {
    implicit def keyspace: KeySpace = DataConnection.keySpaceQuery.keySpace

    implicit def session: Session = DataConnection.connector.session

    table.create.ifNotExists().future().map(result => result.head.isExhausted())
  }
}


