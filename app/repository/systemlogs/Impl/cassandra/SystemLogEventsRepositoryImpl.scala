package repository.systemlogs.Impl.cassandra

import com.outworkers.phantom.connectors.KeySpace
import com.outworkers.phantom.database.Database
import com.outworkers.phantom.dsl._
import configuration.connections.DataConnection
import domain.systemlogs.SystemLogEvents
import repository.systemlogs.Impl.cassandra.tables.{SystemLogEventsByIdTable, SystemLogEventsTable}
import repository.systemlogs.SystemLogEventsRepository

import scala.concurrent.Future


class SystemLogEventsRepositoryImpl extends SystemLogEventsRepository {

  override def createTable: Future[Boolean] = {
    implicit def keyspace: KeySpace = DataConnection.devKeySpaceQuery.keySpace
    implicit def session: Session = DataConnection.connector.session
    SystemLogEventsDatabase.systemLogEventsTable.create.ifNotExists().future().map(result => result.head.isExhausted())
    SystemLogEventsDatabase.systemLogEventsByIdTable.create.ifNotExists().future().map(result => result.head.isExhausted())
  }

  override def saveEntity(entity: SystemLogEvents): Future[Boolean] = {
    SystemLogEventsDatabase.systemLogEventsTable.saveEntity(entity).map(result => result.isExhausted())
    SystemLogEventsDatabase.systemLogEventsByIdTable.saveEntity(entity).map(result => result.isExhausted())
  }

  override def getEntities: Future[Seq[SystemLogEvents]] = {
    SystemLogEventsDatabase.systemLogEventsTable.getEntities
  }

  override def getEntity(id: String): Future[Option[SystemLogEvents]] = {
    SystemLogEventsDatabase.systemLogEventsByIdTable.getEntity(id)
  }

  override def deleteEntity(entity: SystemLogEvents): Future[Boolean] = {
    SystemLogEventsDatabase.systemLogEventsTable.deleteEntity(entity.siteId,entity.id).map(result => result.isExhausted())
    SystemLogEventsDatabase.systemLogEventsByIdTable.deleteEntity(entity.id).map(result => result.isExhausted())
  }

  override def getSiteLogEvents(siteId: String): Future[Seq[SystemLogEvents]] = {
    SystemLogEventsDatabase.systemLogEventsTable.getSiteLogEvents(siteId)
  }
}


class SystemLogEventsDatabase(override val connector: KeySpaceDef) extends Database[SystemLogEventsDatabase](connector) {

  object systemLogEventsTable extends SystemLogEventsTable with connector.Connector
  object systemLogEventsByIdTable extends SystemLogEventsByIdTable with connector.Connector
}

object SystemLogEventsDatabase extends SystemLogEventsDatabase(DataConnection.connector)
