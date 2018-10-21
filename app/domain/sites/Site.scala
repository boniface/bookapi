package domain.sites

import java.time.LocalDateTime

import play.api.libs.json.Json

case class Site(
               siteId: String,
               siteName: String,
               siteDescription: Option[String],
               dateCreated: LocalDateTime = LocalDateTime.now
               )
object Site {
  implicit val siteFormat = Json.format[Site]
}
