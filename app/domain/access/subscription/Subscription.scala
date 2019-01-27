package domain.access.subscription

import java.time.LocalDateTime

import play.api.libs.json.Json


case class Subscription( id: String="",
                         subscriptionType: String="HOST",
                         value: BigDecimal=BigDecimal(0.0),
                         duration: Int=0,
                         description: String="",
                         dateCreated: LocalDateTime=LocalDateTime.now,
                         status: String=SubscriptionEvents.ACTIVATED)

object Subscription {
  implicit val subFomat = Json.format[Subscription]
}