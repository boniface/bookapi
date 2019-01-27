package services.access.api

import domain.access.users.UserPassword
import services.access.api.Impl.UserPasswordAPIImpl

trait UserPasswordAPI extends IOService[UserPassword]{

}
object UserPasswordAPI{
  def apply: UserPasswordAPI = new UserPasswordAPIImpl()
}
