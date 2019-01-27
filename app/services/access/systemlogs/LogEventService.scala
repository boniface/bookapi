package services.access.systemlogs

import domain.access.systemlogs.LogEvent
import services.CrudService
import services.access.systemlogs.Impl.LogEventServiceImpl

import scala.concurrent.Future

trait LogEventService extends CrudService[LogEvent]{
  def getSiteLogEvents(siteId:String): Future[Seq[LogEvent]]


}

object LogEventService {
  def apply: LogEventService = new LogEventServiceImpl()
}
