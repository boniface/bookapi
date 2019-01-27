package services.access.users

import domain.access.users.UserLoginToken
import services.CrudService
import services.access.users.Impl.UserLoginTokenServiceImpl

import scala.concurrent.Future

trait UserLoginTokenService extends CrudService[UserLoginToken]{

  def getTokenInfo(userId: String, hashedAgent:String ):Future[Option[UserLoginToken]]

}

object UserLoginTokenService {
  def apply: UserLoginTokenService = new UserLoginTokenServiceImpl()
}
