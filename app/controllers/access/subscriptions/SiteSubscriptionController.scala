package controllers.access.subscriptions

import configuration.util.Events
import domain.access.MetaHeaders
import domain.access.security.TokenFailExcerption
import domain.access.subscription.SiteSubscription
import domain.access.systemlogs.LogEvent
import io.circe.Encoder
import io.circe.generic.auto._
import io.circe.syntax._
import javax.inject.Inject
import play.api.http.ContentTypes
import play.api.libs.json.{JsPath, JsValue, Json, JsonValidationError}
import play.api.mvc._
import services.access.login.LoginService
import services.access.subscriptions.SiteSubscriptionService
import services.access.systemlogs.LogEventService

import scala.concurrent.{ExecutionContext, Future}

class SiteSubscriptionController @Inject()
(cc: ControllerComponents)
(implicit ec: ExecutionContext) extends AbstractController(cc) {
  def className: String ="SiteSubscriptionController"
  def domainService: SiteSubscriptionService = SiteSubscriptionService.apply
  def loginService: LoginService = LoginService.apply
  type DomainObject = SiteSubscription

  def create: Action[JsValue] = Action.async(parse.json) {
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

  def getSubscriptionForSite(sitedId: String): Action[AnyContent] = Action.async {
    implicit request: Request[AnyContent] =>
      val headers = MetaHeaders.getHeaders(request)
      val response: Future[Option[SiteSubscription]] = for {
        _ <- loginService.checkLoginStatus(request)
        results <- domainService.getEntity(headers,sitedId)
      } yield results
      OptionReqResp[DomainObject](response)
  }

  def createSiteSubscription(sitedId: String, subId:String): Action[AnyContent] = Action.async {
    implicit request: Request[AnyContent] =>
      val headers = MetaHeaders.getHeaders(request)
      val response: Future[Boolean] = for {
        _ <- loginService.checkLoginStatus(request)
        results <- domainService.createSiteSubscription(headers,sitedId,subId)
      } yield results
      ReqResp[Boolean](response)
  }

  def getSiteSubscriptions(sitedId: String): Action[AnyContent] = Action.async {
    implicit request: Request[AnyContent] =>
      val headers = MetaHeaders.getHeaders(request)
      val response: Future[Seq[SiteSubscription]] = for {
        _ <- loginService.checkLoginStatus(request)
        results <- domainService.getSiteSubscriptions(headers,sitedId)
      } yield results
    SequenceRespReq[DomainObject](response)
  }


  def validateSubscription(siteId: String): Action[AnyContent] = Action.async {
    implicit request: Request[AnyContent] =>
      val headers = MetaHeaders.getHeaders(request)
      val response: Future[Boolean] = for {
        _ <- loginService.checkLoginStatus(request)
        results <- domainService.validateSiteSubscription(headers,siteId)
      } yield results
      ReqResp[Boolean](response)
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
