package services.access.api

import domain.access.users.UserRole
import services.access.api.Impl.UserRoleAPIImpl

trait UserRoleAPI extends IOService[UserRole]{

}
object UserRoleAPI{
  def apply: UserRoleAPI = new UserRoleAPIImpl()
}
