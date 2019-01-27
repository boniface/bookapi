package services.access.repository.user

import domain.access.users.UserLoginToken
import services.access.repository.Repository
import services.access.repository.user.Impl.cassandra.UserLoginTokenRepositoryImpl

import scala.concurrent.Future

trait UserLoginTokenRepository extends Repository[UserLoginToken]{
  def getTokenInfo(userId: String, hashedAgent:String ):Future[Option[UserLoginToken]]
}

object  UserLoginTokenRepository {
  def apply: UserLoginTokenRepository = new UserLoginTokenRepositoryImpl()
}

