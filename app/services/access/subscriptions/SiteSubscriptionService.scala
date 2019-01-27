package services.access.subscriptions

import domain.access.MetaHeaders
import domain.access.subscription.SiteSubscription
import services.access.CrudServiceAPI
import services.access.subscriptions.Impl.SiteSubscriptionServiceImpl

import scala.concurrent.Future

trait SiteSubscriptionService extends CrudServiceAPI[SiteSubscription]{
  def getSiteSubscriptions(metaHeaders: MetaHeaders,siteId:String): Future[Seq[SiteSubscription]]
  def validateSiteSubscription(metaHeaders: MetaHeaders,siteId:String):Future[Boolean]
  def createSiteSubscription(metaHeaders: MetaHeaders,siteId: String, subscriptionId: String): Future[Boolean]
}

object SiteSubscriptionService{
  def apply: SiteSubscriptionService = new SiteSubscriptionServiceImpl()
}



