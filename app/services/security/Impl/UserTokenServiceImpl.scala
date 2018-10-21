package services.security.Impl

import domain.security.UserToken
import repository.security.UserTokenRepository
import services.security.{TokenService, UserTokenService}

import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Future

class UserTokenServiceImpl extends UserTokenService{
  override def saveEntity(entity: UserToken): Future[Boolean] = {
    UserTokenRepository.apply.saveEntity(entity)
  }

  override def getEntities: Future[Seq[UserToken]] = {
    UserTokenRepository.apply.getEntities
  }

  override def getEntity(id: String): Future[Option[UserToken]] = {
    UserTokenRepository.apply.getEntity(id)
  }

  override def deleteEntity(entity: UserToken): Future[Boolean] = {
    UserTokenRepository.apply.deleteEntity(entity)
  }

  override def createTable: Future[Boolean] = {
    UserTokenRepository.apply.createTable
  }

  override def getSiteUserTokens(siteId: String): Future[Seq[UserToken]] = ???

  override def getUserTokens(userId: String): Future[Seq[UserToken]] = {
    UserTokenRepository.apply.getUserTokens(userId)
  }


  override def revokeUserToken(token: String): Future[Boolean] = {

    for {
      claims <- TokenService.apply.getJwtClaimsFromTokenString(token)
      siteId <- TokenService.apply.getSiteIdFromTokenString(token)
      userId <- TokenService.apply.getSiteIdFromTokenString(token)
      deleted <- UserTokenService.apply.deleteEntity(UserToken(siteId, userId, claims.getJwtId))
    } yield deleted

  }
}
