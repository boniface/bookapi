package repository.users.Impl.cassandra.tables

import java.time.LocalDateTime

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.streams._
import com.outworkers.phantom.jdk8._
import domain.users.User

import scala.concurrent.Future

abstract class UserTable extends Table[UserTable, User] with RootConnector {

  override lazy val tableName = "users"

  object siteId extends StringColumn with PartitionKey

  object email extends StringColumn with PrimaryKey

  object userId extends StringColumn with PrimaryKey

  object firstName extends OptionalStringColumn

  object middleName extends OptionalStringColumn

  object lastName extends OptionalStringColumn

  object password extends StringColumn

  object dateCreated extends Col[LocalDateTime]

  def saveEntity(entity: User): Future[ResultSet] = {
    insert
      .value(_.siteId, entity.siteId)
      .value(_.email, entity.email)
      .value(_.userId, entity.userId)
      .value(_.firstName, entity.firstName)
      .value(_.middleName, entity.middleName)
      .value(_.lastName, entity.lastName)
      .value(_.password, entity.password)
      .value(_.dateCreated, entity.dateCreated)
      .future()
  }

  def getSiteUsers(siteId: String): Future[Seq[User]] = {
    select
      .where(_.siteId eqs siteId)
      .fetchEnumerator() run Iteratee.collect()
  }

  def isUserAvailable(siteId: String,email:String): Future[Seq[User]] = {
    select
      .where(_.siteId eqs siteId)
      .and(_.email eqs email)
      .fetchEnumerator() run Iteratee.collect()
  }

  def getEntities: Future[Seq[User]] = {
    select
      .fetchEnumerator() run Iteratee.collect()
  }

  def deleteEntity(siteId: String, userId: String, email: String): Future[ResultSet] = {
    delete
      .where(_.siteId eqs siteId)
      .and(_.userId eqs userId)
      .and(_.email eqs email)
      .future()
  }
}

abstract class UserByEmailTable extends Table[UserByEmailTable, User] with RootConnector {

  override lazy val tableName = "usersbyemail"

  object siteId extends StringColumn with PrimaryKey

  object email extends StringColumn with PartitionKey

  object userId extends StringColumn with PrimaryKey

  object firstName extends OptionalStringColumn

  object middleName extends OptionalStringColumn

  object lastName extends OptionalStringColumn

  object password extends StringColumn

  object dateCreated extends Col[LocalDateTime]

  def saveEntity(entity: User): Future[ResultSet] = {
    insert
      .value(_.siteId, entity.siteId)
      .value(_.email, entity.email)
      .value(_.userId, entity.userId)
      .value(_.firstName, entity.firstName)
      .value(_.middleName, entity.middleName)
      .value(_.lastName, entity.lastName)
      .value(_.password, entity.password)
      .value(_.dateCreated, entity.dateCreated)
      .future()
  }


  def getUserByEmail(email: String): Future[Seq[User]] = {
    select
      .where(_.email eqs email)
      .fetchEnumerator() run Iteratee.collect()
  }

  def deleteEntity(email: String, userId: String, siteId: String): Future[ResultSet] = {
    delete
      .where(_.email eqs email)
      .and(_.userId eqs userId)
      .and(_.siteId eqs siteId)
      .future()
  }
}

abstract class UserByIdTable extends Table[UserByIdTable, User] with RootConnector {

  override lazy val tableName = "usersbyids"

  object siteId extends StringColumn with PrimaryKey

  object email extends StringColumn with PrimaryKey

  object userId extends StringColumn with PartitionKey

  object firstName extends OptionalStringColumn

  object middleName extends OptionalStringColumn

  object lastName extends OptionalStringColumn

  object password extends StringColumn

  object dateCreated extends Col[LocalDateTime]

  def saveEntity(entity: User): Future[ResultSet] = {
    insert
      .value(_.siteId, entity.siteId)
      .value(_.email, entity.email)
      .value(_.userId, entity.userId)
      .value(_.firstName, entity.firstName)
      .value(_.middleName, entity.middleName)
      .value(_.lastName, entity.lastName)
      .value(_.password, entity.password)
      .value(_.dateCreated, entity.dateCreated)
      .future()
  }

  def getEntity(userId: String): Future[Option[User]] = {
    select
      .where(_.userId eqs userId)
      .one()
  }

  def deleteEntity(userId: String): Future[ResultSet] = {
    delete
      .where(_.userId eqs userId)
      .future()
  }
}