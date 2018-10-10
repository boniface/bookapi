package repository.users.Impl.cassandra.tables

import java.time.LocalDateTime

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.jdk8._
import com.outworkers.phantom.streams._
import domain.users.UserRole

import scala.concurrent.Future

abstract class UserRoleTable extends Table[UserRoleTable, UserRole] with RootConnector {

  override lazy val tableName = "userroles"

  object userId extends StringColumn with PartitionKey

  object date extends Col[LocalDateTime]

  object roleId extends StringColumn

  def saveEntity(entity: UserRole): Future[ResultSet] = {
    insert
      .value(_.userId, entity.userId)
      .value(_.date, entity.date)
      .value(_.roleId, entity.roleId)
      .future()
  }

  def getEntity(emailId: String): Future[Option[UserRole]] = {
    select
      .where(_.userId eqs emailId)
      .one()
  }

  def getEntities: Future[Seq[UserRole]] = {
    select
      .fetchEnumerator() run Iteratee.collect()
  }

  def deleteEntity(emailId: String): Future[ResultSet] = {
    delete
      .where(_.userId eqs emailId)
      .future()
  }
}