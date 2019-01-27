package services.access.subscriptions

import domain.access.MetaHeaders
import domain.access.subscription.UserSubscriptions
import domain.access.users.User
import services.access.CrudServiceAPI
import services.access.subscriptions.Impl.UserSubscriptionServiceImpl

import scala.concurrent.Future

trait UserSubscriptionService extends CrudServiceAPI[UserSubscriptions]{
  def getUsersSubscriptionsForSite(metaHeaders: MetaHeaders,siteId: String ): Future[Seq[UserSubscriptions]]
  def createUserSubscription(metaHeaders: MetaHeaders,user: User, subscriptionId: String): Future[Boolean]
  def validateUserSubscription(metaHeaders: MetaHeaders,siteId:String, userId:String):Future[Boolean]
  def getUserSubscriptionsFromSite(metaHeaders: MetaHeaders,siteId: String, userId: String): Future[Seq[UserSubscriptions]]
}

object UserSubscriptionService {
  def apply: UserSubscriptionService = new UserSubscriptionServiceImpl()
}

