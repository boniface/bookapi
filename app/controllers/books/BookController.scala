package controllers.books

import domain.books.Book
import domain.security.TokenFailExcerption
import javax.inject.Inject
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._
import services.books.BookService

import scala.concurrent.ExecutionContext.Implicits.global

class BookController @Inject()
(cc: ControllerComponents) extends AbstractController(cc) {

  def create: Action[JsValue] = Action.async(parse.json) {
    request =>
      val input = request.body
      val entity = Json.fromJson[Book](input).get
      val response = for {
        //        auth <- TokenCheckService.apply.getLoginStatus(request)
        results <- BookService.apply.saveEntity(entity)
      } yield results
      response.map(ans => Ok(Json.toJson(entity)))
        .recover {
          case tokenFailExcerption: TokenFailExcerption => Unauthorized
          case otherException: Exception => InternalServerError
        }
  }

  def update: Action[JsValue] = create

  def getEntity(id: String): Action[AnyContent] = Action.async {
    implicit request: Request[AnyContent] =>
      val input = request.body
      val response = for {
        //        auth <- TokenCheckService.apply.getTokenFromParam(request)
        results <- BookService.apply.getEntity(id)
      } yield results
      response.map(ans => Ok(Json.toJson(ans)))
        .recover {
          case tokenCheckFailed: TokenFailExcerption => Unauthorized
          case otherException: Exception => InternalServerError
        }
  }

  def getEntities: Action[AnyContent] = Action.async {
    implicit request: Request[AnyContent] =>
      val input = request.body
      val response = for {
        //        auth <- TokenCheckService.apply.getTokenFromParam(request)
        results <- BookService.apply.getEntities
      } yield  results
      response.map(ans => Ok(Json.toJson(ans)))
        .recover{
          case tokenFailExcerption: TokenFailExcerption => Unauthorized
          case otherException: Exception => InternalServerError
        }
  }

  def getSiteEntities(siteId: String): Action[AnyContent] = Action.async {
    implicit request: Request[AnyContent] =>
      val input = request.body
      val response = for {
        //        auth <- TokenCheckService.apply.getTokenFromParam(request)
        results <- BookService.apply.getSiteEntities(siteId)
      } yield  results
      response.map(ans => Ok(Json.toJson(ans)))
        .recover{
          case tokenFailExcerption: TokenFailExcerption => Unauthorized
          case otherException: Exception => InternalServerError
        }
  }

  def delete: Action[JsValue] = Action.async(parse.json) {
    request =>
      val input = request.body
      val entity = Json.fromJson[Book](input).get
      val response = for {
        //        auth <- TokenCheckService.apply.getTokenFromParam(request)
        results <- BookService.apply.deleteEntity(entity)
      } yield results
      response.map(ans => Ok(Json.toJson(ans)))
        .recover{
          case tokenFailExcerption: TokenFailExcerption => Unauthorized
          case otherException: Exception => InternalServerError
        }
  }
}
