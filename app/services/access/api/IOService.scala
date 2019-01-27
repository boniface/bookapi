package services.access.api

import domain.access.{IOError, MetaHeaders}

import scala.concurrent.Future

trait IOService[A] {

  def saveEntity(metaHeaders:MetaHeaders, entity: A):  Future[Either[IOError, Boolean]]

  def getEntities(metaHeaders:MetaHeaders,siteId:String): Future[Either[IOError, Seq[A]]]

  def getEntity(metaHeaders:MetaHeaders,id: String): Future[Either[IOError, A]]

  def deleteEntity(metaHeaders:MetaHeaders,entity: A):  Future[Either[IOError, Boolean]]
}
