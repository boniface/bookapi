package services.access.api

import domain.access.{IOError, MetaHeaders}
import domain.access.login.{MessageResponse, PasswordChangeCredentials, Registration}
import domain.access.users.{User, UserRole}
import services.access.api.Impl.UsersAPIImpl

import scala.concurrent.Future

trait UsersAPI extends IOService[User] {
  type Entity = User

  def createUser(metaHeaders: MetaHeaders, user: User): Future[Either[IOError, MessageResponse]]

  def getSiteUsers(metaHeaders: MetaHeaders, siteId: String): Future[Either[IOError, Seq[Entity]]]

  def getUserByEmail(metaHeaders: MetaHeaders, email: String): Future[Either[IOError, Seq[Entity]]]

  def isUserAvailable(metaHeaders: MetaHeaders, siteId: String, email: String): Future[Either[IOError, Boolean]]

  def resetAccount(metaHeaders: MetaHeaders, user: User): Future[Either[IOError, MessageResponse]]

  def changePassword(metaHeaders: MetaHeaders, credentials: PasswordChangeCredentials): Future[Either[IOError, Boolean]]

  def registerUser(metaHeaders: MetaHeaders,value: Registration):Future[Either[IOError, MessageResponse]]

}

object UsersAPI {
  def apply: UsersAPI = new UsersAPIImpl()
}
