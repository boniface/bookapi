package services.security

import domain.mail.MessageResponse
import domain.security._
import domain.users.User
import play.api.mvc.Request
import services.security.Impl.LoginServiceImpl


import scala.concurrent.Future

trait LoginService {
  def isUserRegistered(entity: User): Future[Boolean]

  def forgotPassword(profile: Profile): Future[MessageResponse]

  def resetAccount(user: User): Future[MessageResponse]

  def getLoginStatus[A](request: Request[A]): Future[LoginStatus]

  def changePassword(credentials: PasswordChangeCredentials): Future[Boolean]

  def getLoginToken(credential: LoginCredential, agent: String): Future[UserGeneratedToken]

  def getUserAccounts(email: String): Future[Seq[User]]

  def resetPassword(token: String): Future[Option[Int]]

  def requestNewPassword(token: String): Future[MessageResponse]

}

object LoginService {
  def apply: LoginService = new LoginServiceImpl()
}
