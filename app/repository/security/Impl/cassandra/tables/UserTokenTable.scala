package repository.security.Impl.cassandra.tables

import java.time.LocalDateTime

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.jdk8._
import com.outworkers.phantom.streams._
import domain.security.UserToken

import scala.concurrent.Future

abstract class UserTokenTable extends Table[UserTokenTable, UserToken] with RootConnector {

  override lazy val tableName = "usertokens"

  object userId extends StringColumn with PartitionKey

  object id extends StringColumn with PrimaryKey

  object expiryDate extends Col[LocalDateTime]

  object tokenValue extends StringColumn

  object message extends StringColumn

  def saveEntity(entity: UserToken): Future[ResultSet] = {
    insert
      .value(_.userId, entity.userId)
      .value(_.id, entity.id)
      .value(_.expiryDate, entity.expiryDate)
      .value(_.tokenValue, entity.tokenValue)
      .value(_.message, entity.message)
      .future()
  }



  def getEntities: Future[Seq[UserToken]] = {
    select
      .fetchEnumerator() run Iteratee.collect()
  }

  def deleteEntity(userId:String, id: String): Future[ResultSet] = {
    delete
      .where(_.userId eqs userId)
      .and(_.id eqs id)
      .future()
  }
}

//abstract class UserTokenByUsersTable extends Table[UserTokenByUsersTable, UserToken] {
//
//  object siteId extends StringColumn with PrimaryKey
//
//  object userId extends StringColumn with PartitionKey
//
//  object id extends StringColumn with PrimaryKey
//
//  object expiryDate extends Col[LocalDateTime]
//
//  object tokenValue extends StringColumn
//
//  object message extends StringColumn
//
//
//}


abstract class UserTokenByIdTable extends Table[UserTokenByIdTable, UserToken] with RootConnector {

  override lazy val tableName = "usertokensids"

  object userId extends StringColumn with PrimaryKey

  object id extends StringColumn with PartitionKey

  object expiryDate extends Col[LocalDateTime]

  object tokenValue extends StringColumn

  object message extends StringColumn

  def saveEntity(entity: UserToken): Future[ResultSet] = {
    insert
      .value(_.userId, entity.userId)
      .value(_.id, entity.id)
      .value(_.expiryDate, entity.expiryDate)
      .value(_.tokenValue, entity.tokenValue)
      .value(_.message, entity.message)
      .future()
  }

  def getEntity(id: String): Future[Option[UserToken]] = {
    select
      .where(_.id eqs id)
      .one()
  }

  def deleteEntity(id: String): Future[ResultSet] = {
    delete
      .where(_.id eqs id)
      .future()
  }
}