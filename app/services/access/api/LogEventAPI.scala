package services.access.api

import domain.access.IOError
import domain.access.systemlogs.LogEvent
import services.access.api.Impl.LogEventAPIImpl

import scala.concurrent.Future

trait LogEventAPI {

  def saveEntity(entity: LogEvent):  Future[Either[IOError, Boolean]]

  def getEntities(siteId:String): Future[Either[IOError, Seq[LogEvent]]]

  def getEntity(id: String): Future[Either[IOError, LogEvent]]

  def deleteEntity(entity: LogEvent):  Future[Either[IOError, Boolean]]

}

object LogEventAPI{
  def apply: LogEventAPI = new LogEventAPIImpl()
}
