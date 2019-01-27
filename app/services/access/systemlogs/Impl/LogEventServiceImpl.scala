package services.access.systemlogs.Impl

import configuration.util.Events
import domain.access.IOError
import domain.access.systemlogs.LogEvent
import services.access.api.LogEventAPI
import services.access.systemlogs.LogEventService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class LogEventServiceImpl extends LogEventService {
  def className = "RoleServiceImpl"
  val api = LogEventAPI.apply
  type Entity = LogEvent

  override def saveEntity(entity: LogEvent): Future[Boolean] = {
    val result: Future[Either[IOError, Boolean]] = api.saveEntity(entity)
    result map {
      case Right(value) => value
      case Left(error) => {
        val log = LogEvent(eventName = Events.JASONPARSE, eventType = className, message = error.toString)
        LogEventService.apply.saveEntity(log)
        false
      }
    }
  }

  override def getEntity(id: String): Future[Option[LogEvent]] = {
    val result: Future[Either[IOError, Entity]] = api.getEntity(id)
    result map {
      case Right(value) => Some(value)
      case Left(error) => {
        val log = LogEvent(eventName = Events.JASONPARSE, eventType = className, message = error.toString)
        LogEventService.apply.saveEntity(log)
        None
      }
    }
  }

  override def deleteEntity(entity: LogEvent): Future[Boolean] = {
    val result: Future[Either[IOError, Boolean]] = api.deleteEntity(entity)
    result map {
      case Right(value) => value
      case Left(error) => {
        val log = LogEvent(eventName = Events.JASONPARSE, eventType = className, message = error.toString)
        LogEventService.apply.saveEntity(log)
        false
      }
    }
  }

  override def getSiteLogEvents(siteId: String): Future[Seq[LogEvent]] = {
    val result: Future[Either[IOError, Seq[Entity]]] = api.getEntities(siteId)
    result map {
      case Right(value) => value
      case Left(error) => {
        val log = LogEvent(eventName = Events.JASONPARSE, eventType = className, message = error.toString)
        LogEventService.apply.saveEntity(log)
        Seq.empty
      }
    }
  }

  override def createTable: Future[Boolean] = ???
  override def getEntities: Future[Seq[LogEvent]] = ???

}
