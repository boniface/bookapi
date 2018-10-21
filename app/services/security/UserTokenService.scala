package services.security

import domain.security.UserToken
import services.CrudService
import services.security.Impl.UserTokenServiceImpl

import scala.concurrent.Future

trait UserTokenService  extends CrudService[UserToken]{

  def getSiteUserTokens(siteId: String): Future[Seq[UserToken]]

  def revokeUserToken(token: String): Future[Boolean]

  def getUserTokens(userId: String): Future[Seq[UserToken]]

}

object UserTokenService {
  def apply: UserTokenService = new UserTokenServiceImpl()
}
