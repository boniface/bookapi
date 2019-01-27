package services.access.api

import domain.access.{IOError, MetaHeaders}
import domain.access.subscription.{SiteSubscription, UserSubscriptions}
import domain.access.users.User
import services.access.api.Impl.UserSubscriptionAPIImpl

import scala.concurrent.Future

trait UserSubscriptionAPI extends IOService[UserSubscriptions]{
  def getUsersSubscriptionsForSite(metaHeaders: MetaHeaders,siteId: String ):Future[Either[IOError, Seq[UserSubscriptions]]]
  def createUserSubscription(metaHeaders: MetaHeaders,user: User, subscriptionId: String): Future[Either[IOError, Boolean]]
  def validateUserSubscription(metaHeaders: MetaHeaders,siteId:String, userId:String):Future[Either[IOError, Boolean]]
  def getUserSubscriptionsFromSite(metaHeaders: MetaHeaders,siteId: String, userId: String): Future[Either[IOError, Seq[UserSubscriptions]]]

}
object UserSubscriptionAPI{
  def apply: UserSubscriptionAPI = new UserSubscriptionAPIImpl()
}
