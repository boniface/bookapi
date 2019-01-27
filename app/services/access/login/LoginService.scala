package services.access.login

import domain.access.login.{MessageResponse, _}
import domain.access.security._
import play.api.mvc.Request
import services.access.login.Impl.LoginServiceImpl

import scala.concurrent.Future

trait LoginService {
  def isUserRegistered(siteId:String, email:String): Future[Boolean]
  def forgotPassword(profile:Profile): Future[MessageResponse]
  def getLoginToken(credential: LoginCredential, agent: String): Future[UserGeneratedToken]
  def getUserAccounts(email: String): Future[Seq[Account]]
  def resetPasswordRequest(resetKey: String):Future[MessageResponse]

  def checkLoginStatus[A](request: Request[A]): Future[LoginStatus]

 }

object LoginService{
  def apply: LoginService = new LoginServiceImpl()
}
