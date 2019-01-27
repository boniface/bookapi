package controllers.access.users

import javax.inject.Inject
import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._


class UsersRouter@Inject()
(userController: UserController,
 userRoleController: UserRoleController,
 userStatusController: UserStatusController,
 userPasswordController: UserPasswordController) extends SimpleRouter {
  override def routes: Routes = {
    //USER
    case POST(p"/create") =>
      userController.createUser
    case POST(p"/update") =>
      userController.updateUser
    case POST(p"/resetaccount") =>
      userController.resetAccount
    case POST(p"/changepassword") =>
      userController.changePassword
    case GET(p"/all/site/$siteId") =>
      userController.getSiteUsers(siteId)
    case GET(p"/email/$email") =>
      userController.getUserByEmail(email)
    case GET(p"/available/$siteId/$email") =>
      userController.isUserAvailable(siteId,email)
    case GET(p"/get/$userId") =>
      userController.getUser(userId)


    //User role
    case POST(p"/role/create") =>
      userRoleController.create
    case POST(p"/role/update") =>
      userRoleController.update
    case POST(p"/role/delete") =>
      userRoleController.deleteEntity
    case GET(p"/role/get/$id") =>
      userRoleController.getEntity(id)


    //status
    case POST(p"/status/create") =>
      userStatusController.create
    case POST(p"/status/update") =>
      userStatusController.update
    case POST(p"/status/delete") =>
      userStatusController.deleteEntity
    case GET(p"/status/get/$id") =>
      userStatusController.getEntity(id)



    //passwd
    case POST(p"/pass/create") =>
      userPasswordController.create
    case POST(p"/pass/update") =>
      userPasswordController.update
    case POST(p"/pass/delete") =>
      userPasswordController.deleteEntity
    case GET(p"/pass/get/$id") =>
      userPasswordController.getEntity(id)

  }
}