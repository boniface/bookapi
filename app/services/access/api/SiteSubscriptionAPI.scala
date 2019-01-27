package services.access.api

import domain.access.{IOError, MetaHeaders}
import domain.access.subscription.SiteSubscription
import services.access.api.Impl.SiteSubscriptionAPIImpl

import scala.concurrent.Future

trait SiteSubscriptionAPI extends IOService[SiteSubscription]{
  type Entity = SiteSubscription
  def getSiteSubscriptions(metaHeaders: MetaHeaders,siteId:String): Future[Either[IOError, Seq[Entity]]]
  def validateSiteSubscription(metaHeaders: MetaHeaders,siteId:String):Future[Either[IOError, Boolean]]
  def createSiteSubscription(metaHeaders: MetaHeaders,siteId: String, subscriptionId: String): Future[Either[IOError, Boolean]]

}
object SiteSubscriptionAPI{
  def apply: SiteSubscriptionAPI = new SiteSubscriptionAPIImpl()
}
