package services.access.users.Impl

import domain.access.roles.Role
import domain.access.users.UserLoginToken
import services.access.login.LoginTokenService
import services.access.repository.user.UserLoginTokenRepository
import services.access.users.UserLoginTokenService

import scala.concurrent.Future

class UserLoginTokenServiceImpl extends UserLoginTokenService{
  def className = "UserLoginTokenServiceImpl"
  val service = LoginTokenService.apply
  type Entity = Role
  lazy val  repository = UserLoginTokenRepository.apply


  override def saveEntity(entity: UserLoginToken): Future[Boolean] = {
    repository.saveEntity(entity)
  }

  override def getEntities: Future[Seq[UserLoginToken]] = {
    repository.getEntities
  }

  override def getEntity(id: String): Future[Option[UserLoginToken]] = {
    repository.getEntity(id)
  }

  override def deleteEntity(entity: UserLoginToken): Future[Boolean] = {
    repository.deleteEntity(entity)
  }

  override def createTable: Future[Boolean] = {
    repository.createTable
  }

  override def getTokenInfo(userId: String, hashedAgent: String): Future[Option[UserLoginToken]] = {
    repository.getTokenInfo(userId,hashedAgent)
  }
}
