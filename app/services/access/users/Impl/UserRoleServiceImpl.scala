package services.access.users.Impl

import configuration.util.Events
import domain.access.systemlogs.LogEvent
import domain.access.users.UserRole
import domain.access.{IOError, MetaHeaders}
import services.access.api.UserRoleAPI
import services.access.systemlogs.LogEventService
import services.access.users.UserRoleService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class UserRoleServiceImpl extends UserRoleService{
  def className = "UserRoleServiceImpl"
  val api = UserRoleAPI.apply
  type Entity = UserRole

  override def saveEntity(metaHeaders: MetaHeaders, entity: Entity): Future[Boolean] = {
    val result: Future[Either[IOError, Boolean]] = api.saveEntity(metaHeaders,entity)
    result map {
      case Right(value) => value
      case Left(error) => {
        val log = LogEvent(eventName = Events.JASONPARSE, eventType = className, message = error.toString)
        LogEventService.apply.saveEntity(log)
        false
      }
    }
  }

  override def getEntities(metaHeaders: MetaHeaders, siteId: String): Future[Seq[Entity]] = {
    val result: Future[Either[IOError, Seq[Entity]]] = api.getEntities(metaHeaders,siteId)
    result map {
      case Right(value) => value
      case Left(error) => {
        val log = LogEvent(eventName = Events.JASONPARSE, eventType = className, message = error.toString)
        LogEventService.apply.saveEntity(log)
        Seq.empty
      }
    }
  }

  override def getEntity(metaHeaders: MetaHeaders, id: String): Future[Option[Entity]] = {
    val result: Future[Either[IOError, Entity]] = api.getEntity(metaHeaders,id)
    result map {
      case Right(value) => Some(value)
      case Left(error) => {
        val log = LogEvent(eventName = Events.JASONPARSE, eventType = className, message = error.toString)
        LogEventService.apply.saveEntity(log)
        None
      }
    }
  }

  override def deleteEntity(metaHeaders: MetaHeaders, entity: Entity): Future[Boolean] = {
    val result: Future[Either[IOError, Boolean]] = api.deleteEntity(metaHeaders,entity)
    result map {
      case Right(value) => value
      case Left(error) => {
        val log = LogEvent(eventName = Events.JASONPARSE, eventType = className, message = error.toString)
        LogEventService.apply.saveEntity(log)
        false
      }
    }
  }
}
