package services.security

import domain.security.SiteAccessKeysApi
import services.CrudService
import services.security.Impl.SiteAccessKeysApiServiceImpl

import scala.concurrent.Future

trait SiteAccessKeysApiService extends CrudService[SiteAccessKeysApi]{
  def getSiteAccessKeysApi(siteId: String): Future[Seq[SiteAccessKeysApi]]
}

object SiteAccessKeysApiService {
  def apply: SiteAccessKeysApiService = new SiteAccessKeysApiServiceImpl()
}