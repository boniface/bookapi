package controllers.access.subscriptions

import javax.inject.Inject
import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._

class SubsRouter @Inject()
(subscriptionController: SubscriptionController,
 userSubscriptionController: UserSubscriptionController,
 siteSubscriptionController: SiteSubscriptionController) extends SimpleRouter{

  override def routes: Routes = {

    //Subscriptions

    case POST(p"/create") =>
      subscriptionController.createSubscriptions
    case POST(p"/update") =>
      subscriptionController.update
    case GET(p"/get/$subId") =>
      subscriptionController.getSubscription(subId)
    case GET(p"/active/$siteId") =>
      subscriptionController.getActiveSubscriptions(siteId)
    case GET(p"/deactivated/$siteId") =>
      subscriptionController.getDeactivatedSubscriptions(siteId)
    case GET(p"/get/all/$siteId") =>
      subscriptionController.getSubscriptions(siteId)


    // UserSubscriptions
    case POST(p"/user/create/$subId") =>
      userSubscriptionController.createUserSubscription(subId)
    case POST(p"/user/update") =>
      userSubscriptionController.userSubscriptionUpdate
    case POST(p"/user/delete") =>
      userSubscriptionController.delete
    case GET(p"/user/get/$userId") =>
      userSubscriptionController.getUserSubscription(userId)
    case GET(p"/user/validate/$siteId/$userId") =>
      userSubscriptionController.validateUserSubscription(siteId,userId)
    case GET(p"/user/get/site/$siteId") =>
      userSubscriptionController.getUsersSubscriptionsForSite(siteId)
    case GET(p"/user/site/$siteId/$userId") =>
      userSubscriptionController.getUserSubscriptionsFromSite(siteId,  userId)

      // SiteSubscriptions

    case POST(p"/site/create") =>
      siteSubscriptionController.create
    case GET(p"/site/subs/$siteId") =>
      siteSubscriptionController.getSiteSubscriptions(siteId)
    case GET(p"/site/create/sub/$siteId/$subId") =>
      siteSubscriptionController.createSiteSubscription(siteId,subId)
    case GET(p"/site/all/$userId") =>
      siteSubscriptionController.validateSubscription(userId)
    case GET(p"/site/get/$userId") =>
      siteSubscriptionController.getSubscriptionForSite(userId)
  }

}
