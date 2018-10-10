package controllers.security

import javax.inject.Inject
import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._

/**
  * @author caniksea
  * @param userTokenController
  */
class SecurityRouter @Inject()(userTokenController: UserTokenController) extends SimpleRouter {
  override def routes: Routes = {
    case GET(p"/getall") =>
      userTokenController.getEntities
    case GET(p"/get/$id") =>
      userTokenController.getEntity(id)
    case POST(p"/create") =>
      userTokenController.create
    case POST(p"/update") =>
      userTokenController.update
    case POST(p"/delete") =>
      userTokenController.deleteEntity
  }
}
