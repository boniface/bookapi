package domain.access.systemlogs

import java.time.LocalDateTime
import java.util.UUID

import configuration.util.{HashcodeKeys, Util}
import play.api.libs.json.Json


case class LogEvent(siteId: String = HashcodeKeys.HASHCODE,
                    id: String = Util.md5Hash(UUID.randomUUID().toString),
                    eventName: String = "",
                    eventType: String = "",
                    message: String = "",
                    date: LocalDateTime = LocalDateTime.now())

object LogEvent {
  implicit val syseventLog = Json.format[LogEvent]

  implicit object localDateTime extends Ordering[LocalDateTime] {
    def compare(x: LocalDateTime, y: LocalDateTime): Int = y.compareTo(x)
  }

  implicit def orderByStartDate[A <: LogEvent]: Ordering[A] =
    Ordering.by(events => events.date)


}
