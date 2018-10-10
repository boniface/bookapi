package repository.sites.impl.cassandra

import com.outworkers.phantom.connectors.KeySpace
import com.outworkers.phantom.database.Database
import configuration.connections.DataConnection
import com.outworkers.phantom.dsl._
import domain.sites.Site
import repository.sites.SiteRepository
import repository.sites.impl.cassandra.tables.{SiteTable}

import scala.concurrent.Future

class SiteRepositoryImpl extends SiteRepository {
  override def saveEntity(entity: Site): Future[Boolean] =
    SiteDatabase.SiteTable.saveEntity(entity).map(result => result.isExhausted())

  override def getEntities: Future[Seq[Site]] =
    SiteDatabase.SiteTable.getEntities()

  override def getEntity(id: String): Future[Option[Site]] =
    SiteDatabase.SiteTable.getEntity(id)

  override def deleteEntity(entity: Site): Future[Boolean] =
    SiteDatabase.SiteTable.deleteEntity(entity.siteId).map(result => result.isExhausted())

  override def createTable: Future[Boolean] = {
    implicit def keySpace: KeySpace = DataConnection.devKeySpaceQuery.keySpace

    implicit def session: Session = DataConnection.connector.session

    SiteDatabase.SiteTable.create.ifNotExists().future().map(result => result.head.isExhausted())
  }
}

class SiteDatabase(override val connector: KeySpaceDef) extends Database[SiteDatabase](connector) {

  object SiteTable extends SiteTable with connector.Connector
}
object SiteDatabase extends SiteDatabase(DataConnection.connector)
