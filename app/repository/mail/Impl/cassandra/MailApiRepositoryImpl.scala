package repository.mail.Impl.cassandra

import com.outworkers.phantom.connectors.KeySpace
import com.outworkers.phantom.database.Database
import com.outworkers.phantom.dsl._
import configuration.connections.DataConnection
import domain.mail.MailApi
import repository.mail.Impl.cassandra.tables.{MailApiTable}
import repository.mail.MailApiRepository

import scala.concurrent.Future

class MailApiRepositoryImpl extends MailApiRepository{
  override def saveEntity(entity: MailApi): Future[Boolean] = {
    MailApiDatabase.mailApiTable.saveEntity(entity) map (result => result.isExhausted())
  }

  override def getEntities: Future[Seq[MailApi]] = {
    MailApiDatabase.mailApiTable.getEntities
  }

  override def getEntity(id: String): Future[Option[MailApi]] = {
    MailApiDatabase.mailApiTable.getEntity(id)
  }

  override def deleteEntity(entity: MailApi): Future[Boolean] = {
    MailApiDatabase.mailApiTable.deleteEntity(entity.id) map (result => result.isExhausted())
  }

  override def createTable: Future[Boolean] = {
    implicit def keyspace: KeySpace = DataConnection.devKeySpaceQuery.keySpace
    implicit def session: Session = DataConnection.connector.session
    MailApiDatabase.mailApiTable.create.ifNotExists().future().map(result => result.head.isExhausted())

  }
}

class MailApiDatabase(override val connector: KeySpaceDef) extends Database[MailApiDatabase](connector) {
  object mailApiTable extends MailApiTable with connector.Connector

}

object MailApiDatabase extends MailApiDatabase(DataConnection.connector)
