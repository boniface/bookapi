package services.access.repository

import scala.concurrent.Future

trait Repository[A] {

  def saveEntity(entity: A): Future[Boolean]

  def getEntities: Future[Seq[A]]

  def getEntity(id: String): Future[Option[A]]

  def deleteEntity(entity: A): Future[Boolean]

  def createTable: Future[Boolean]
}
