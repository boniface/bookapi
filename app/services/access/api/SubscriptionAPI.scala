package services.access.api

import domain.access.subscription.Subscription
import services.access.api.Impl.SubscriptionAPIImpl

trait SubscriptionAPI extends IOService[Subscription]{

}
object SubscriptionAPI{
  def apply: SubscriptionAPI = new SubscriptionAPIImpl()
}
