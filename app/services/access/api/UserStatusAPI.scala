package services.access.api

import domain.access.users.UserStatus
import services.access.api.Impl.UserStatusAPIImpl

trait UserStatusAPI extends IOService[UserStatus]{

}
object UserStatusAPI {
  def apply: UserStatusAPI = new UserStatusAPIImpl()
}
