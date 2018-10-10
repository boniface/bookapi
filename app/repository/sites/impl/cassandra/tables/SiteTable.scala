package repository.sites.impl.cassandra.tables

import java.time.LocalDateTime

import com.outworkers.phantom.dsl._
import com.outworkers.phantom.streams._
import com.outworkers.phantom.jdk8._
import domain.sites.Site

import scala.concurrent.Future

/**
  * @author caniksea
  */
abstract class SiteTable extends Table[SiteTable, Site] with RootConnector {

  override lazy val tableName = "sites"

  object siteId extends StringColumn with PartitionKey

  object siteName extends StringColumn

  object siteDescription extends OptionalStringColumn

  object dateCreated extends Col[LocalDateTime]

  /**
    * Insert new site
    * @param entity
    * @return
    */
  def saveEntity(entity: Site): Future[ResultSet] = {
    insert
      .value(_.siteId, entity.siteId)
      .value(_.siteName, entity.siteName)
      .value(_.siteDescription, entity.siteDescription)
      .value(_.dateCreated, entity.dateCreated)
      .future()
  }

  /**
    * Retrieve all sites
    * @return
    */
  def getEntities(): Future[Seq[Site]] = {
    select.fetchEnumerator() run Iteratee.collect()
  }

  /**
    * Retrieve one site given id.
    * @param siteId
    * @return
    */
  def getEntity(siteId: String): Future[Option[Site]] = {
    select
      .where(_.siteId.eqs(siteId))
      .one()
  }

  /**
    * Delete a site
    * @param siteId
    * @return
    */
  def deleteEntity(siteId: String): Future[ResultSet] = {
    delete
      .where(_.siteId eqs siteId)
      .future()
  }
}
