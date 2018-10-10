package repository.security.Impl.cassandra.tables

import java.time.LocalDateTime

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.jdk8._
import com.outworkers.phantom.streams._
import domain.security.ApiKeys

import scala.concurrent.Future

abstract class ApiKeysTable extends Table[ApiKeysTable, ApiKeys] with RootConnector {

  override lazy val tableName = "apikeys"

  object id extends StringColumn with PartitionKey

  object value extends StringColumn

  object status extends StringColumn

  object date extends Col[LocalDateTime]

  def saveEntity(entity: ApiKeys): Future[ResultSet] = {
    insert
      .value(_.id, entity.id)
      .value(_.date, entity.date)
      .value(_.status, entity.status)
      .value(_.value, entity.value)
      .future()
  }

  def getEntity(id: String): Future[Option[ApiKeys]] = {
    select
      .where(_.id eqs id)
      .one()
  }

  def getEntities: Future[Seq[ApiKeys]] = {
    select
      .fetchEnumerator() run Iteratee.collect()
  }

  def deleteEntity(id: String): Future[ResultSet] = {
    delete
      .where(_.id eqs id)
      .future()
  }
}