package controllers.dashboard

import domain.dashboard.SiteBook
import javax.inject.Inject
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._
import services.books.BookService
import services.sites.SiteService

import scala.concurrent.ExecutionContext.Implicits.global

class DashboardController @Inject()
(cc: ControllerComponents) extends AbstractController(cc) {

  def getBooksPerSite: Action[JsValue] = Action.async(parse.json) {
    request =>
      val input = request.body
      val siteResponse = for {
        result <- SiteService.apply.getEntities
      } yield result

      siteResponse.map(sites => {
//        val siteBooks: Seq[SiteBook] = for {
//          site <- sites
//          bookCount <- BookService.apply.getBookCountForSite(site.siteId)
//        } yield SiteBook(site.siteName, bookCount)
        Ok(Json.toJson(sites))
      })
  }

}