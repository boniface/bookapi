package controllers.books

import domain.books.Chapter
import javax.inject.Inject
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._
import services.books.ChapterService
import domain.security.TokenFailExcerption

import scala.concurrent.ExecutionContext.Implicits.global

/**
  * @author caniksea
  * @param cc
  */
class ChapterController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def create: Action[JsValue] = Action.async(parse.json) {
    request =>
      val input = request.body
      val entity = Json.fromJson[Chapter](input).get
      val response = for {
        //        auth <- TokenCheckService.apply.getLoginStatus(request)
        results <- ChapterService.apply.saveEntity(entity)
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
        //        auth <- TokenCheckService.apply.getLoginStatus(request)
        result <- ChapterService.apply.getEntity(id)
      } yield result
      response.map(ans => Ok(Json.toJson(ans)))
        .recover {
          case tokenFailExcerption: TokenFailExcerption => Unauthorized
          case otherException: Exception => InternalServerError
        }
  }

  def getEntities: Action[AnyContent] = Action.async {
    implicit request: Request[AnyContent] =>
      val input = request.body
      val response = for {
        //        auth <- TokenCheckService.apply.getLoginStatus(request)
        results <- ChapterService.apply.getEntities
      } yield results
      response.map(ans => Ok(Json.toJson(ans)))
        .recover {
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
        results <- ChapterService.apply.getEntitiesForIds(idList)
      } yield results
      response.map(ans => Ok(Json.toJson(ans)))
        .recover {
          case tokenFailExcerption: TokenFailExcerption => Unauthorized
          case otherException: Exception => InternalServerError
        }
  }

  def delete: Action[JsValue] = Action.async(parse.json) {
    request =>
      val input = request.body
      val entity = Json.fromJson[Chapter](input).get
      val response = for {
        //        auth <- TokenCheckService.apply.getLoginStatus(request)
        result <- ChapterService.apply.deleteEntity(entity)
      } yield result
      response.map(ans => Ok(Json.toJson(ans)))
        .recover {
          case tokenFailExcerption: TokenFailExcerption => Unauthorized
          case otherException: Exception => InternalServerError
        }
  }

}
