package controllers.access.login

import configuration.util.{Events, HashcodeKeys}
import domain.access.MetaHeaders
import domain.access.login._
import domain.access.security._
import domain.access.systemlogs.LogEvent
import io.circe.Encoder
import io.circe.generic.auto._
import io.circe.syntax._
import javax.inject.Inject
import play.api.http.ContentTypes
import play.api.libs.json.{JsPath, JsValue, Json, JsonValidationError}
import play.api.mvc._
import services.access.login.LoginService
import services.access.systemlogs.LogEventService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class LoginController @Inject()
(cc: ControllerComponents) extends AbstractController(cc) {
  def className: String = "LoginController"
  def domainService: LoginService = LoginService.apply

  type DomainObject = MessageResponse

  def isUserRegistered(siteId: String, email:String) :Action[AnyContent] = Action.async {
    implicit request: Request[AnyContent]=>
      val headers = MetaHeaders.getHeaders(request)
      val response: Future[Boolean] = for {
        results <- domainService.isUserRegistered(siteId,email)
      } yield results
      ReqResp[Boolean](response)
  }

  def forgotPassword: Action[JsValue] = Action.async(parse.json) {
    implicit request: Request[JsValue] =>
      val headers = MetaHeaders.getHeaders(request)
      val entity = Json.fromJson[Profile](request.body).asEither
      entity match {
        case Right(value) =>
          val response: Future[DomainObject] = for {
            results <- domainService.forgotPassword(value)
          } yield results
          ReqResp[DomainObject](response)
        case Left(error) => errorResp(error)
      }
  }

  def getLoginToken: Action[JsValue] = Action.async(parse.json) {
    implicit request: Request[JsValue] =>
      val headers = MetaHeaders.getHeaders(request)
      val entity = Json.fromJson[LoginCredential](request.body).asEither
      entity match {
        case Right(value) =>
          val agent = request.headers.get(HashcodeKeys.BROWSER_AGENT).getOrElse("")
          val response: Future[UserGeneratedToken] = for {
            results <- domainService.getLoginToken(value, agent)
          } yield results
          ReqResp[UserGeneratedToken](response)
        case Left(error) => errorResp(error)
      }
  }

  def getUserAccounts(email: String): Action[AnyContent] = Action.async {
    implicit request: Request[AnyContent] =>
      val headers = MetaHeaders.getHeaders(request)
      val response: Future[Seq[Account]] = for {
        results: Seq[Account] <- domainService.getUserAccounts(email)
      } yield results
      SequenceRespReq[Account](response)
  }

  def resetPasswordRequest(resetKey: String): Action[AnyContent] = Action.async {
    implicit request: Request[AnyContent] =>
      val headers = MetaHeaders.getHeaders(request)
      val response: Future[MessageResponse] = for {
        results: MessageResponse <- domainService.resetPasswordRequest(resetKey)
      } yield results
      ReqResp[MessageResponse](response)
  }


  // ================= Common Responses ==========

  private def errorResp(error: Seq[(JsPath, Seq[JsonValidationError])]): Future[Status] = {
    Future {
      val log = LogEvent(eventName = Events.RESPONSE, eventType = className, message = error.seq.toString())
      LogEventService.apply.saveEntity(log)
      InternalServerError
    }
  }

  private def ReqResp[A: Encoder](response: Future[A]): Future[Result] = {
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

  private def OptionReqResp[A: Encoder](response: Future[Option[A]]): Future[Result] = {
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

  private def SequenceRespReq[A: Encoder](response: Future[Seq[A]]): Future[Result] = {
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
