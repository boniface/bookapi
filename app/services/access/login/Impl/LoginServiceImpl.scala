package services.access.login.Impl

import java.util.UUID

import com.typesafe.config.ConfigFactory
import configuration.util.{Events, HashcodeKeys, Util}
import domain.access.IOError
import domain.access.login._
import play.api.mvc.Request
import domain.access.security._
import domain.access.systemlogs.LogEvent
import domain.access.users.UserLoginToken
import org.mindrot.jbcrypt.BCrypt
import services.access.api.LoginAPI
import services.access.login.{LoginService, LoginTokenService}
import services.access.systemlogs.LogEventService
import services.access.users.UserLoginTokenService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


class LoginServiceImpl extends LoginService {
  def className = "LoginServiceImpl"

  lazy val api = LoginAPI.apply
  lazy val userTokenService = UserLoginTokenService.apply
  lazy val loginTokenService = LoginTokenService.apply


  val config = ConfigFactory.load()
  val baseUrl = config.getString("base.url")

  override def isUserRegistered(siteId: String, email: String): Future[Boolean] = {
    val result: Future[Either[IOError, Boolean]] = api.isUserRegistered(siteId, email)
    result map {
      case Right(value) => value
      case Left(error) => {
        val log = LogEvent(eventName = Events.JASONPARSE, eventType = className, message = error.toString)
        LogEventService.apply.saveEntity(log)
        false
      }
    }
  }

  override def forgotPassword(profile: Profile): Future[MessageResponse] = {
    val result: Future[Either[IOError, MessageResponse]] = api.forgotPassword(profile)
    result map {
      case Right(value) => value
      case Left(error) => {
        val log = LogEvent(eventName = Events.JASONPARSE, eventType = className, message = error.toString)
        LogEventService.apply.saveEntity(log)
        MessageResponse.apply()
      }
    }
  }

  override def getLoginToken(credential: LoginCredential, agent: String): Future[UserGeneratedToken] = {
    val result: Future[Either[IOError, UserGeneratedToken]] = api.getLoginToken(credential, agent)
    result map {
      case Right(token) =>
        val userId = loginTokenService.getUserUserId(token.token)
        val hashedAgent = loginTokenService.getUserHashedAgent(token.token)
        userTokenService.saveEntity(UserLoginToken(userId, hashedAgent, Util.md5Hash(UUID.randomUUID().toString)))
        token
      case Left(error) =>
        val log = LogEvent(eventName = Events.JASONPARSE, eventType = className, message = error.toString)
        LogEventService.apply.saveEntity(log)
        UserGeneratedToken.apply()
    }
  }

  override def getUserAccounts(email: String): Future[Seq[Account]] = {
    val result: Future[Either[IOError, Seq[Account]]] = api.getUserAccounts(email)
    result map {
      case Right(value) => value
      case Left(error) =>
        val log = LogEvent(eventName = Events.JASONPARSE, eventType = className, message = error.toString)
        LogEventService.apply.saveEntity(log)
        Seq.empty
    }
  }

  override def resetPasswordRequest(resetKey: String): Future[MessageResponse] = {
    val result: Future[Either[IOError, MessageResponse]] = api.resetPasswordRequest(resetKey)
    result map {
      case Right(value) => value
      case Left(error) => {
        val log = LogEvent(eventName = Events.JASONPARSE, eventType = className, message = error.toString)
        LogEventService.apply.saveEntity(log)
        MessageResponse.apply()
      }
    }
  }

  override def checkLoginStatus[A](request: Request[A]): Future[LoginStatus] = {
    val token = request.headers.get(HashcodeKeys.AUTHORIZATION).getOrElse("")
    val agent = request.headers.get(HashcodeKeys.BROWSER_AGENT).getOrElse("")
    lazy val  isSecurityEnabled: Boolean = ConfigFactory.load().getBoolean("token-security.enabled")
    if (isSecurityEnabled) {
      val userId = loginTokenService.getUserUserId(token)
      val hashedAgent = loginTokenService.getUserHashedAgent(token)
      userTokenService.getTokenInfo(userId, hashedAgent)
        .withFilter(_ => isTokenValid(token,agent,hashedAgent)) map {
        case Some(_) => LoginStatus(Events.TOKENVALID)
        case None => throw TokenFailExcerption("Site Subscription Pass:")
      }

    } else Future.successful(LoginStatus(Events.TOKENVALID))
  }

  private def isTokenValid(token:String,agent:String,hashedAgent:String): Boolean ={
    loginTokenService.hasTokenExpired(token) &&  BCrypt.checkpw(agent, hashedAgent)
  }

}
