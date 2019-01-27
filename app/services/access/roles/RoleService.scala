package services.access.roles

import domain.access.roles.Role
import services.access.CrudServiceAPI
import services.access.roles.Impl.RoleServiceImpl

trait RoleService  extends CrudServiceAPI[Role]{

}

object RoleService {
  def apply: RoleService = new RoleServiceImpl()
}
