package controllers.sites

import javax.inject.Inject
import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._

/**
  * @author caniksea
  * @param siteController
  */
class SiteRouter @Inject() (siteController: SiteController) extends SimpleRouter {
  override def routes: Routes = {
    case GET(p"/getall") => siteController.getEntities
    case GET(p"/get/$id") => siteController.getEntity(id)
    case POST(p"/create") => siteController.create
    case POST(p"/update") => siteController.update
    case POST(p"/delete") => siteController.delete
  }
}
