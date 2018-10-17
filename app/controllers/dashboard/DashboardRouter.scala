package controllers.dashboard

import javax.inject.Inject
import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._

class DashboardRouter @Inject() (dashboardController: DashboardController) extends SimpleRouter {

  override def routes: Routes = {
    case GET(p"/getbookspersite") => dashboardController.getBooksPerSite
  }
}
