package controllers.books

import domain.books.Section
import domain.security.TokenFailExcerption
import javax.inject.Inject
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._
import services.books.SectionService

import scala.concurrent.ExecutionContext.Implicits.global

class SectionController @Inject()
(cc: ControllerComponents) extends AbstractController(cc) {

  def create: Action[JsValue] = Action.async(parse.json) {
    request =>
      val input = request.body
      val entity = Json.fromJson[Section](input).get
      val response = for {
        //        auth <- TokenCheckService.apply.getLoginStatus(request)
        result <- SectionService.apply.saveEntity(entity)
      } yield result
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
        //        auth <- TokenCheckService.apply.getLoginStatus(request)
        results <- SectionService.apply.getEntity(id)
      } yield results
      response.map(ans => Ok(Json.toJson(ans)))
        .recover{
          case tokenFailExcerption: TokenFailExcerption => Unauthorized
          case otherException: Exception => InternalServerError
        }
  }

  def getEntitiesForIds(ids: String): Action[AnyContent] = Action.async {
    val idList = ids.split(",").map(_.trim).filter(!_.isEmpty).toList
    implicit request: Request[AnyContent] =>
      val input = request.body
      val response = for {
      //        auth <- TokenCheckService.apply.getLoginStatus(request)
        results <- SectionService.apply.getEntitiesForIds(idList)
       } yield results
      response.map(ans => Ok(Json.toJson(ans)))
        .recover{
          case tokenFailExcerption: TokenFailExcerption => Unauthorized
          case otherException: Exception => InternalServerError
        }
  }

  def delete: Action[JsValue] = Action.async(parse.json) {
    request =>
      val input = request.body
      val entity = Json.fromJson[Section](input).get
      val response = for {
        //        auth <- TokenCheckService.apply.getLoginStatus(request)
        result <- SectionService.apply.deleteEntity(entity)
      } yield result
      response.map(ans => Ok(Json.toJson(ans)))
        .recover {
          case tokenFailExcerption: TokenFailExcerption => Unauthorized
          case otherException: Exception => InternalServerError
        }
  }

}
