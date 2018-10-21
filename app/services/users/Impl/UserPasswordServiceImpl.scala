package services.users.Impl

import domain.users.UserPassword
import services.users.UserPasswordService

import scala.concurrent.Future

class UserPasswordServiceImpl extends UserPasswordService{
  override def saveEntity(entity: UserPassword): Future[Boolean] = ???

  override def getEntities: Future[Seq[UserPassword]] = ???

  override def getEntity(id: String): Future[Option[UserPassword]] = ???

  override def deleteEntity(entity: UserPassword): Future[Boolean] = ???

  override def createTable: Future[Boolean] = ???
}
