package services.access.subscriptions.Impl

import configuration.util.Events
import domain.access.{IOError, MetaHeaders}
import domain.access.subscription.SiteSubscription
import domain.access.systemlogs.LogEvent
import services.access.api.SiteSubscriptionAPI
import services.access.subscriptions.SiteSubscriptionService
import services.access.systemlogs.LogEventService

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class SiteSubscriptionServiceImpl extends SiteSubscriptionService {
  def className = "SiteSubscriptionServiceImpl"
  val api = SiteSubscriptionAPI.apply
  type Entity = SiteSubscription


  override def saveEntity(metaHeaders: MetaHeaders, entity: SiteSubscription): Future[Boolean] = {
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

  override def getEntities(metaHeaders: MetaHeaders, siteId: String): Future[Seq[SiteSubscription]] = {
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

  override def getEntity(metaHeaders: MetaHeaders, id: String): Future[Option[SiteSubscription]] = {
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

  override def deleteEntity(metaHeaders: MetaHeaders, entity: SiteSubscription): Future[Boolean] = {
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

  override def getSiteSubscriptions(metaHeaders: MetaHeaders, siteId: String): Future[Seq[Entity]] = {
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

  override def validateSiteSubscription(metaHeaders: MetaHeaders, siteId: String): Future[Boolean] = {
    val result: Future[Either[IOError, Boolean]] = api.validateSiteSubscription(metaHeaders,siteId)
    result map {
      case Right(value) => value
      case Left(error) => {
        val log = LogEvent(eventName = Events.JASONPARSE, eventType = className, message = error.toString)
        LogEventService.apply.saveEntity(log)
        false
      }
    }
  }

  override def createSiteSubscription(metaHeaders: MetaHeaders, siteId: String, subscriptionId: String): Future[Boolean] = {
    val result: Future[Either[IOError, Boolean]] = api.createSiteSubscription(metaHeaders,siteId,subscriptionId)
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
