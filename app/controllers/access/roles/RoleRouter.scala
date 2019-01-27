package controllers.access.roles

import javax.inject.Inject

import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._

class RoleRouter@Inject()
(roleController: RoleController) extends SimpleRouter {
  override def routes: Routes = {
    //ROLES
    case GET(p"/get/$id") =>
      roleController.getEntity(id)
    case GET(p"/get/site/$siteId") =>
      roleController.getEntitiesById(siteId)
    case POST(p"/create") =>
      roleController.createEntity
    case POST(p"/update") =>
      roleController.updateEntity
    case POST(p"/delete") =>
      roleController.deleteEntity
  }
}
