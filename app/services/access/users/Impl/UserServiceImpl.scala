package services.access.users.Impl

import configuration.util.Events
import domain.access.login.{MessageResponse, PasswordChangeCredentials, Registration}
import domain.access.systemlogs.LogEvent
import domain.access.users._
import domain.access.{IOError, MetaHeaders}
import services.access.api.UsersAPI
import services.access.systemlogs.LogEventService
import services.access.users.UserService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


class UserServiceImpl extends UserService {
  def className = "UserServiceImpl"
  val api = UsersAPI.apply
  type Entity = User

  override def getSiteUsers(metaHeaders: MetaHeaders, siteId: String): Future[Seq[Entity]] = {
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

  override def getUserByEmail(metaHeaders: MetaHeaders, email: String): Future[Seq[Entity]] = {
    val result: Future[Either[IOError, Seq[Entity]]] = api.getUserByEmail(metaHeaders,email)
    result map {
      case Right(value) => value
      case Left(error) =>
        val log = LogEvent(eventName = Events.JASONPARSE, eventType = className, message = error.toString)
        LogEventService.apply.saveEntity(log)
        Seq.empty
    }
  }


  override def isUserAvailable(metaHeaders: MetaHeaders, siteId: String, email: String): Future[Boolean] = {
    val result: Future[Either[IOError, Boolean]] = api.isUserAvailable(metaHeaders,siteId,email)
    result map {
      case Right(value) => value
      case Left(error) => {
        val log = LogEvent(eventName = Events.JASONPARSE, eventType = className, message = error.toString)
        LogEventService.apply.saveEntity(log)
        false
      }
    }
  }


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

  override def createUser(metaHeaders: MetaHeaders,user: Entity): Future[MessageResponse] = {
    val result: Future[Either[IOError, MessageResponse]] = api.createUser(metaHeaders,user)
    result map {
      case Right(value) => value
      case Left(error) => {
        val log = LogEvent(eventName = Events.JASONPARSE, eventType = className, message = error.toString)
        LogEventService.apply.saveEntity(log)
        MessageResponse.apply()
      }
    }
  }

  override def registerUser(metaHeaders: MetaHeaders, value: Registration): Future[MessageResponse] = {
    val result: Future[Either[IOError, MessageResponse]] = api.registerUser(metaHeaders,value)
    result map {
      case Right(value) => value
      case Left(error) => {
        val log = LogEvent(eventName = Events.JASONPARSE, eventType = className, message = error.toString)
        LogEventService.apply.saveEntity(log)
        MessageResponse.apply()
      }
    }
  }

  override def resetAccount(metaHeaders: MetaHeaders, user: Entity): Future[MessageResponse] = {
    val result: Future[Either[IOError, MessageResponse]] = api.resetAccount(metaHeaders,user)
    result map {
      case Right(value) => value
      case Left(error) => {
        val log = LogEvent(eventName = Events.JASONPARSE, eventType = className, message = error.toString)
        LogEventService.apply.saveEntity(log)
        MessageResponse.apply()
      }
    }
  }

  override def changePassword(metaHeaders: MetaHeaders, credentials: PasswordChangeCredentials): Future[Boolean] = {
    val result: Future[Either[IOError, Boolean]] = api.changePassword(metaHeaders,credentials)
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
