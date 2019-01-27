package controllers.access.login

import javax.inject.Inject
import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._


class LoginRouter@Inject()
(loginController: LoginController) extends SimpleRouter {
  override def routes: Routes = {
    case GET(p"/isregistered/$siteId/$email") =>
      loginController.isUserRegistered(siteId,email)
    case POST(p"/forgotpassword") =>
      loginController.forgotPassword
    case POST(p"/getlogintoken") =>
      loginController.getLoginToken
    case GET(p"/getuseraccounts/$email") =>
      loginController.getUserAccounts(email)
    case GET(p"/passwordreset/$resetkey") =>
      loginController.resetPasswordRequest(resetkey)
  }
}