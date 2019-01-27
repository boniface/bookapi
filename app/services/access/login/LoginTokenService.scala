package services.access.login

import services.access.login.Impl.LoginTokenServiceImpl

trait LoginTokenService {
  def hasTokenExpired(token: String): Boolean

  def getUserEmail(token: String): String

  def getUserHashedAgent(token: String): String

  def getUserRole(token: String): String

  def getUserSiteId(token: String): String

  def getUserUserId(token: String): String
}

object LoginTokenService{
  def apply: LoginTokenService = new LoginTokenServiceImpl()
}
