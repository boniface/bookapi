package services.access.users

import domain.access.users.UserStatus
import services.access.CrudServiceAPI
import services.access.users.Impl.UserStatusServiceImpl

trait UserStatusService extends CrudServiceAPI[UserStatus]{

}

object UserStatusService {
  def apply: UserStatusService = new UserStatusServiceImpl()
}
