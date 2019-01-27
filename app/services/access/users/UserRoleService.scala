package services.access.users

import domain.access.users.UserRole
import services.access.CrudServiceAPI
import services.access.users.Impl.UserRoleServiceImpl

trait UserRoleService extends CrudServiceAPI[UserRole]{

}

object UserRoleService {
  def apply: UserRoleService = new UserRoleServiceImpl()
}
