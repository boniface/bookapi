package services.access.users

import domain.access.MetaHeaders
import domain.access.login.{MessageResponse, PasswordChangeCredentials, Registration}
import domain.access.users.User
import services.access.CrudServiceAPI
import services.access.users.Impl.UserServiceImpl

import scala.concurrent.Future

trait UserService extends CrudServiceAPI[User]{
  def createUser(metaHeaders: MetaHeaders,user: User):Future[MessageResponse]
  def getSiteUsers(metaHeaders: MetaHeaders,siteId: String): Future[Seq[User]]
  def getUserByEmail(metaHeaders: MetaHeaders,email: String): Future[Seq[User]]
  def isUserAvailable(metaHeaders: MetaHeaders,siteId:String, email:String): Future[Boolean]
  def registerUser(metaHeaders: MetaHeaders,value: Registration):Future[MessageResponse]

  def resetAccount(metaHeaders: MetaHeaders,user:User):Future[MessageResponse]
  def changePassword(metaHeaders: MetaHeaders, credentials:PasswordChangeCredentials):Future[Boolean]



}

object UserService {
  def apply: UserService = new UserServiceImpl()
}
