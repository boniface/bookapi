package controllers.sites

import domain.security.TokenFailExcerption
import domain.sites.Site
import javax.inject.Inject
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._
import services.sites.SiteService

import scala.concurrent.ExecutionContext.Implicits.global

class SiteController @Inject()
(cc: ControllerComponents) extends AbstractController(cc)  {

  def create: Action[JsValue] = Action.async(parse.json) {
    request =>
      val input = request.body
      val entity = Json.fromJson[Site](input).get
      val response = for {
        //        auth <- TokenCheckService.apply.getLoginStatus(request)
        results <- SiteService.apply.saveEntity(entity)
      } yield results
      response.map(ans => Ok(Json.toJson(entity)))
        .recover {
          case tokenCheckFailed: TokenFailExcerption => Unauthorized
          case otherException: Exception => InternalServerError
        }
  }

  def update: Action[JsValue] = create

  def getEntity(id: String): Action[AnyContent] = Action.async {
    implicit request: Request[AnyContent]=>
      val input = request.body
      val response = for {
        //        auth <- TokenCheckService.apply.getTokenFromParam(request)
        results <- SiteService.apply.getEntity(id)
      } yield results
      response.map(ans => Ok(Json.toJson(ans)))
        .recover {
          case tokenCheckFailed: TokenFailExcerption => Unauthorized
          case otherException: Exception => InternalServerError
        }
  }

  def getEntities: Action[AnyContent] = Action.async {
    implicit request: Request[AnyContent]=>
      val input = request.body
      val response = for {
        //        auth <- TokenCheckService.apply.getTokenFromParam(request)
        results <- SiteService.apply.getEntities
      } yield results
      response.map(ans => Ok(Json.toJson(ans)))
        .recover {
          case tokenCheckFailed: TokenFailExcerption => Unauthorized
          case otherException: Exception => InternalServerError
        }
  }

  def delete: Action[JsValue] = Action.async(parse.json) {
    request =>
      val input = request.body
      val entity = Json.fromJson[Site](input).get
      val response = for {
        //        auth <- TokenCheckService.apply.getTokenFromParam(request)
        results <- SiteService.apply.deleteEntity(entity)
      } yield results
      response.map(ans => Ok(Json.toJson(ans)))
        .recover {
          case tokenCheckFailed: TokenFailExcerption => Unauthorized
          case otherException: Exception => InternalServerError
        }
  }

}
