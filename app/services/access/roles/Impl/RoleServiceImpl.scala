package services.access.roles.Impl

import configuration.util.Events
import domain.access.{IOError, MetaHeaders}
import domain.access.roles.Role
import domain.access.systemlogs.LogEvent
import services.access.api.RolesAPI
import services.access.roles.RoleService
import services.access.systemlogs.LogEventService

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class RoleServiceImpl extends RoleService {
  def className = "RoleServiceImpl"
  val api = RolesAPI.apply
  type Entity = Role

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
