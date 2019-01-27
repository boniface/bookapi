package services.access.api

import domain.access.IOError
import domain.access.login._
import domain.access.security.UserGeneratedToken
import services.access.api.Impl.LoginAPIImpl

import scala.concurrent.Future

trait LoginAPI {

  def isUserRegistered(siteId: String, email: String): Future[Either[IOError, Boolean]]

  def forgotPassword(profile: Profile): Future[Either[IOError, MessageResponse]]

  def getLoginToken(credential: LoginCredential, agent: String): Future[Either[IOError, UserGeneratedToken]]

  def getUserAccounts(email: String): Future[Either[IOError, Seq[Account]]]

  def resetPasswordRequest(resetKey: String): Future[Either[IOError, MessageResponse]]

}

object LoginAPI {
  def apply: LoginAPI = new LoginAPIImpl()
}
