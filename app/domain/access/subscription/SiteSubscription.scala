package domain.access.subscription

import java.time.LocalDateTime

import play.api.libs.json.Json

case class SiteSubscription(siteId: String,
                            id: String,
                            subscriptionId: String,
                            tokenValue: String,
                            startDate: LocalDateTime,
                            endDate: LocalDateTime
                           )

object SiteSubscription {

  implicit val subFmt = Json.format[SiteSubscription]

  implicit object localDateTime extends Ordering[LocalDateTime] {
    def compare(x: LocalDateTime, y: LocalDateTime): Int = y.compareTo(x)
  }
  implicit def orderByStartDate[A <: SiteSubscription]: Ordering[A] =
    Ordering.by(siteSubscription => siteSubscription.startDate)
  }
