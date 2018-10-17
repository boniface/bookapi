package domain.dashboard

import play.api.libs.json.Json

case class SiteBook(siteName: String, bookCount: Option[Long])

object SiteBook {
  implicit val siteBookFormat = Json.format[SiteBook]
}
