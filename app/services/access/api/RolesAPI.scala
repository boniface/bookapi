package services.access.api


import domain.access.roles.Role
import services.access.api.Impl.RolesAPIImpl

trait RolesAPI  extends IOService[Role]{

}

object RolesAPI{
  def apply: RolesAPI = new RolesAPIImpl()
}
