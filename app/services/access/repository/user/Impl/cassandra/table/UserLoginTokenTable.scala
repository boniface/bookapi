package services.access.repository.user.Impl.cassandra.table

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.keys.PrimaryKey
import domain.access.users.UserLoginToken

import scala.concurrent.Future

abstract class UserLoginTokenTable extends Table[UserLoginTokenTable, UserLoginToken] with RootConnector {

  object userId extends StringColumn with PartitionKey

  object hashedAgent extends StringColumn with PartitionKey

  object id extends StringColumn with  PrimaryKey


  override lazy val tableName = "usertokens"

  def saveEntity(entity: UserLoginToken): Future[ResultSet] = {
    insert
      .value(_.userId, entity.userId)
      .value(_.hashedAgent, entity.hashedAgent)
      .value(_.id, entity.id)
      .future()
  }

  def getEntity(userId:String, hashedAgent: String): Future[Option[UserLoginToken]] = {
    select
      .where(_.userId eqs userId)
      .and(_.hashedAgent eqs hashedAgent)
      .one()
  }

  def deleteEntity(userId:String, hashedAgent: String): Future[ResultSet] = {
    delete
      .where(_.userId eqs userId)
      .and(_.hashedAgent eqs hashedAgent)
      .future()
  }
}