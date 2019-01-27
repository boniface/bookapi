package domain.access.subscription

import java.time.LocalDateTime

import play.api.libs.json.Json

case class UserSubscriptions(siteId:String,
                             userId: String,
                             id:String,
                             subscriptionId: String,
                             tokenValue: String,
                             startDate: LocalDateTime,
                             endDate: LocalDateTime
                            )

object UserSubscriptions {

  implicit val subFmt = Json.format[UserSubscriptions]
  implicit object localDateTime extends Ordering[LocalDateTime] {
    def compare(x: LocalDateTime, y: LocalDateTime): Int = x.compareTo(y)
  }
  implicit def orderByStartDate[A <: UserSubscriptions]: Ordering[A] =
    Ordering.by(userSubscriptions => userSubscriptions.startDate)

}
