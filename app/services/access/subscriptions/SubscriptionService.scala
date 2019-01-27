package services.access.subscriptions

import domain.access.subscription.Subscription
import services.access.CrudServiceAPI
import services.access.subscriptions.Impl.SubscriptionServiceImpl

trait SubscriptionService extends CrudServiceAPI[Subscription]{


}

object SubscriptionService{
  def apply: SubscriptionService = new SubscriptionServiceImpl()
}
