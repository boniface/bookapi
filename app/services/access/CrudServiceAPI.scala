package services.access

import domain.access.MetaHeaders

import scala.concurrent.Future

trait CrudServiceAPI [A] {

  def saveEntity(metaHeaders:MetaHeaders, entity: A): Future[Boolean]

  def getEntities(metaHeaders:MetaHeaders,siteId:String): Future[Seq[A]]

  def getEntity(metaHeaders:MetaHeaders,id: String):  Future[Option[A]]

  def deleteEntity(metaHeaders:MetaHeaders,entity: A):  Future[Boolean]

}
