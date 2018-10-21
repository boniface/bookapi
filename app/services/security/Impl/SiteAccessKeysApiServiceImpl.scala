package services.security.Impl

import domain.security.SiteAccessKeysApi
import services.security.SiteAccessKeysApiService

import scala.concurrent.Future

class SiteAccessKeysApiServiceImpl extends SiteAccessKeysApiService {
  override def saveEntity(entity: SiteAccessKeysApi): Future[Boolean] = ???

  override def getEntities: Future[Seq[SiteAccessKeysApi]] = ???

  override def getEntity(id: String): Future[Option[SiteAccessKeysApi]] = ???

  override def deleteEntity(entity: SiteAccessKeysApi): Future[Boolean] = ???

  override def createTable: Future[Boolean] = ???

  override def getSiteAccessKeysApi(siteId: String): Future[Seq[SiteAccessKeysApi]] = ???
}
