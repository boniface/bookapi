package controllers.access.users

import configuration.util.Events
import domain.access.MetaHeaders
import domain.access.login.{MessageResponse, PasswordChangeCredentials}
import domain.access.security.TokenFailExcerption
import domain.access.systemlogs.LogEvent
import domain.access.users.User
import io.circe.Encoder
import io.circe.generic.auto._
import io.circe.syntax._
import javax.inject.Inject
import play.api.http.ContentTypes
import play.api.libs.json.{JsPath, JsValue, Json, JsonValidationError}
import play.api.mvc.{Action, _}
import services.access.login.LoginService
import services.access.systemlogs.LogEventService
import services.access.users.UserService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class UserController @Inject()
(cc: ControllerComponents) extends AbstractController(cc)  {
  def className: String ="UserController"
  def domainService: UserService = UserService.apply
  def loginService: LoginService = LoginService.apply

  type DomainObject = User


  def createUser: Action[JsValue] = Action.async(parse.json) {
    implicit request: Request[JsValue] =>
      val headers = MetaHeaders.getHeaders(request)
      val entity = Json.fromJson[DomainObject](request.body).asEither
      entity match {
        case Right(value) =>
          val response: Future[MessageResponse] = for {
            _ <- loginService.checkLoginStatus(request)
            results: MessageResponse <- domainService.createUser(headers,value)
          } yield results
          ReqResp[MessageResponse](response)
        case Left(error) => errorResp(error)
      }
  }


  def updateUser: Action[JsValue] = Action.async(parse.json) {
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

  def resetAccount: Action[JsValue] = Action.async(parse.json) {
    implicit request: Request[JsValue] =>
      val headers = MetaHeaders.getHeaders(request)
      val entity = Json.fromJson[User](request.body).asEither
      entity match {
        case Right(value) =>
          val response: Future[MessageResponse] = for {
            _ <- loginService.checkLoginStatus(request)
            results <- domainService.resetAccount(headers,value)
          } yield results
          ReqResp[MessageResponse](response)
        case Left(error) => errorResp(error)
      }
  }

  def changePassword: Action[JsValue] = Action.async(parse.json) {
    implicit request: Request[JsValue] =>
      val headers = MetaHeaders.getHeaders(request)
      val entity = Json.fromJson[PasswordChangeCredentials](request.body).asEither
      entity match {
        case Right(value) =>
          val response: Future[Boolean] = for {
            _ <- loginService.checkLoginStatus(request)
            results: Boolean <- domainService.changePassword(headers,value)
          } yield results
          ReqResp[Boolean](response)
        case Left(error) => errorResp(error)
      }
  }

  def getSiteUsers(siteId: String): Action[AnyContent] = Action.async {
    implicit request: Request[AnyContent]=>
      val headers = MetaHeaders.getHeaders(request)
      val response: Future[Seq[DomainObject]] = for {
        _ <- loginService.checkLoginStatus(request)
        results <- UserService.apply.getSiteUsers(headers,siteId)
      } yield results
      SequenceRespReq[DomainObject](response)
  }

  def getUserByEmail(email: String):Action[AnyContent] = Action.async {
    implicit request: Request[AnyContent]=>
      val headers = MetaHeaders.getHeaders(request)
      val response: Future[Seq[DomainObject]] = for {
        _ <- loginService.checkLoginStatus(request)
        results <- UserService.apply.getUserByEmail(headers,email)
      } yield results
      SequenceRespReq[DomainObject](response)
  }

  def isUserAvailable(siteId: String, email:String) :Action[AnyContent] = Action.async {
    implicit request: Request[AnyContent]=>
      val headers = MetaHeaders.getHeaders(request)
      val response: Future[Boolean] = for {
        _ <- loginService.checkLoginStatus(request)
        results <- UserService.apply.isUserAvailable(headers,siteId,email)
      } yield results
      ReqResp[Boolean](response)
  }


  def getUser(userId: String): Action[AnyContent] = Action.async {
    implicit request: Request[AnyContent]=>
      val headers = MetaHeaders.getHeaders(request)
      val response: Future[Option[DomainObject]] = for {
        _ <- loginService.checkLoginStatus(request)
        results: Option[User] <- UserService.apply.getEntity(headers,userId)
      } yield results
      OptionReqResp[DomainObject](response)
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
