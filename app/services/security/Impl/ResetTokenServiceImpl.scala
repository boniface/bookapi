package services.security.Impl

import domain.security.ResetToken
import services.security.ResetTokenService

import scala.concurrent.Future

class ResetTokenServiceImpl extends ResetTokenService{

  override def saveEntity(entity: ResetToken): Future[Boolean] = ???

  override def getEntities: Future[Seq[ResetToken]] = ???

  override def getEntity(id: String): Future[Option[ResetToken]] = ???

  override def deleteEntity(entity: ResetToken): Future[Boolean] = ???

  override def createTable: Future[Boolean] = ???
}


