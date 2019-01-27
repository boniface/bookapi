package controllers.access.roles

import configuration.util.Events
import domain.access.MetaHeaders
import domain.access.roles.Role
import domain.access.security.TokenFailExcerption
import domain.access.systemlogs.LogEvent
import javax.inject.Inject
import play.api.http.ContentTypes
import play.api.libs.json.{JsPath, JsValue, Json, JsonValidationError}
import play.api.mvc._
import services.access.roles.RoleService
import services.access.systemlogs.LogEventService
import io.circe.Encoder
import io.circe.syntax._
import io.circe.generic.auto._
import services.access.login.LoginService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class RoleController @Inject()
(cc: ControllerComponents) extends AbstractController(cc) {
  def className: String ="RoleController"
  def domainService: RoleService = RoleService.apply
  def loginService: LoginService = LoginService.apply
  type DomainObject = Role

  def createEntity: Action[JsValue] = Action.async(parse.json) {
    implicit request: Request[JsValue] =>
      val headers = MetaHeaders.getHeaders(request)
      val entity = Json.fromJson[DomainObject](request.body).asEither
      entity match {
        case Right(value) =>
          val response: Future[Boolean] = for {
            _ <- loginService.checkLoginStatus(request)
            results: Boolean <- domainService.saveEntity(headers,value)
          } yield results
          ReqResp[Boolean](response)
        case Left(error) => errorResp(error)
      }
  }

  def updateEntity: Action[JsValue] = Action.async(parse.json) {
    implicit request: Request[JsValue] =>
      val headers = MetaHeaders.getHeaders(request)
      val entity = Json.fromJson[DomainObject](request.body).asEither
      entity match {
        case Right(value) =>
          val response: Future[Boolean] = for {
            _ <- loginService.checkLoginStatus(request)
            results <- domainService.saveEntity(headers,value)
          } yield results
          ReqResp[Boolean](response)
        case Left(error) => errorResp(error)
      }
  }
  def getEntity(id: String): Action[AnyContent] = Action.async {
    implicit request: Request[AnyContent] =>
      val headers = MetaHeaders.getHeaders(request)
      val response: Future[Option[DomainObject]] = for {
        _ <- loginService.checkLoginStatus(request)
        results: Option[DomainObject] <- domainService.getEntity(headers,id)
      } yield results
      OptionReqResp[DomainObject](response)
  }

  def getEntitiesById(siteId: String): Action[AnyContent] = Action.async {
    implicit request: Request[AnyContent] =>
      val headers = MetaHeaders.getHeaders(request)
      val response = for {
        _ <- loginService.checkLoginStatus(request)
        results: Seq[DomainObject] <- domainService.getEntities(headers,siteId)
      } yield results
      SequenceRespReq[DomainObject](response)
  }

  def deleteEntity: Action[JsValue] = Action.async(parse.json) {
    implicit request: Request[JsValue] =>
      val headers = MetaHeaders.getHeaders(request)
      val entity = Json.fromJson[DomainObject](request.body).asEither
      entity match {
        case Right(value) =>
          val response: Future[Boolean] = for {
            _ <- loginService.checkLoginStatus(request)
            results <- domainService.deleteEntity(headers,value)
          } yield results
          ReqResp[Boolean](response)
        case Left(error) => errorResp(error)
      }
  }
  // ================= Common Responses ==========

  private def errorResp(error: Seq[(JsPath, Seq[JsonValidationError])]): Future[Status] = {
    Future {
      val log = LogEvent(eventName = Events.RESPONSE, eventType = className, message = error.seq.toString())
      LogEventService.apply.saveEntity(log)
      InternalServerError
    }
  }

  private def ReqResp[A:Encoder](response: Future[A]): Future[Result] = {
    response.map(result =>
      Ok(result.asJson.noSpaces)
      .as(ContentTypes.JSON)
    ).recover {
        case exp: TokenFailExcerption =>
          val log = LogEvent(eventName = Events.TOKENFAILED, eventType = className, message = exp.getMessage)
          LogEventService.apply.saveEntity(log)
          Unauthorized
        case exp: Exception =>
          val log = LogEvent(eventName = Events.RESPONSE, eventType = className, message = exp.getMessage)
          LogEventService.apply.saveEntity(log)
          InternalServerError
      }
  }

  private def OptionReqResp[A:Encoder](response: Future[Option[A]]): Future[Result] = {
    response.map(result =>
      Ok(result.asJson.noSpaces)
        .as(ContentTypes.JSON)
      ).recover {
        case exp: TokenFailExcerption =>
          val log = LogEvent(eventName = Events.TOKENFAILED, eventType = className, message = exp.getMessage)
          LogEventService.apply.saveEntity(log)
          Unauthorized
        case exp: Exception =>
          val log = LogEvent(eventName = Events.RESPONSE, eventType = className, message = exp.getMessage)
          LogEventService.apply.saveEntity(log)
          InternalServerError
      }
  }

  private def SequenceRespReq[A:Encoder](response: Future[Seq[A]]): Future[Result] = {
    response.map(result =>
      Ok(result.asJson.noSpaces)
        .as(ContentTypes.JSON)
    ).recover {
        case exp: TokenFailExcerption =>
          val log = LogEvent(eventName = Events.TOKENFAILED, eventType = className, message = exp.getMessage)
          LogEventService.apply.saveEntity(log)
          Unauthorized
        case exp: Exception =>
          val log = LogEvent(eventName = Events.RESPONSE, eventType = className, message = exp.getMessage)
          LogEventService.apply.saveEntity(log)
          InternalServerError
      }
  }
}
