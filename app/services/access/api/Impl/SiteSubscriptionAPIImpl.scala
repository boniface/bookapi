package services.access.api.Impl

import configuration.connections.NetConnection
import domain.access.{ApiError, IOError, MetaHeaders}
import io.circe.generic.auto._
import io.circe.parser
import okhttp3.{MediaType, Request, RequestBody}
import play.api.libs.json.Json
import services.access.api.SiteSubscriptionAPI

import scala.concurrent.Future

class SiteSubscriptionAPIImpl extends SiteSubscriptionAPI {
  val conn = NetConnection

  override def saveEntity(headers: MetaHeaders, entity: Entity): Future[Either[IOError, Boolean]] = {
    val MEDIA_TYPE_JSON = MediaType.parse("application/json")
    val jsonStr = Json.toJson(entity).toString()
    val request = new Request.Builder()
      .header(MetaHeaders.AUTHORIZATION, headers.token)
      .header(MetaHeaders.BROWSER_AGENT, headers.agent)
      .url(conn.apiUrl + "/subscriptions/site/create")
      .post(RequestBody.create(MEDIA_TYPE_JSON, jsonStr))
      .build()
    val response = conn.getClient.newCall(request).execute().body().string()
    val decodingResult = parser.decode[Boolean](response)
    val json = decodingResult match {
      case Right(result) => Right(result)
      case Left(error) => Left(ApiError(error))
    }
    Future.successful(json)
  }

  override def getEntities(headers: MetaHeaders, siteId: String): Future[Either[IOError, Seq[Entity]]] = {
    val request = new Request.Builder()
      .header(MetaHeaders.AUTHORIZATION, headers.token)
      .header(MetaHeaders.BROWSER_AGENT, headers.agent)
      .url(conn.apiUrl + "/subscriptions/site/subs/" + siteId)
      .build()
    val response = conn.getClient.newCall(request).execute().body().string()
    val decodingResult = parser.decode[Seq[Entity]](response)
    val json = decodingResult match {
      case Right(entities) => Right(entities)
      case Left(error) => Left(ApiError(error))
    }
    Future.successful(json)
  }

  override def getEntity(headers: MetaHeaders, id: String): Future[Either[IOError, Entity]] = {
    val request = new Request.Builder()
      .header(MetaHeaders.AUTHORIZATION, headers.token)
      .header(MetaHeaders.BROWSER_AGENT, headers.agent)
      .url(conn.apiUrl + "/subscriptions/site/get/" + id)
      .build()
    val response = conn.getClient.newCall(request).execute().body().string()
    val decodingResult = parser.decode[Entity](response)
    val json = decodingResult match {
      case Right(entity) => Right(entity)
      case Left(error) => Left(ApiError(error))
    }
    Future.successful(json)
  }

  override def deleteEntity(headers: MetaHeaders, entity: Entity): Future[Either[IOError, Boolean]] = {
    val MEDIA_TYPE_JSON = MediaType.parse("application/json")
    val jsonStr = Json.toJson(entity).toString()
    val request = new Request.Builder()
      .header(MetaHeaders.AUTHORIZATION, headers.token)
      .header(MetaHeaders.BROWSER_AGENT, headers.agent)
      .url(conn.apiUrl + "/subscriptions/site/delete")
      .post(RequestBody.create(MEDIA_TYPE_JSON, jsonStr))
      .build()
    val response = conn.getClient.newCall(request).execute().body().string()
    val decodingResult = parser.decode[Boolean](response)
    val json = decodingResult match {
      case Right(result) => Right(result)
      case Left(error) => Left(ApiError(error))
    }
    Future.successful(json)
  }

  override def getSiteSubscriptions(metaHeaders: MetaHeaders, siteId: String): Future[Either[IOError, Seq[Entity]]] = {
    getSiteSubscriptions(metaHeaders, siteId)
  }

  override def validateSiteSubscription(metaHeaders: MetaHeaders, siteId: String): Future[Either[IOError, Boolean]] = {
    val request = new Request.Builder()
      .header(MetaHeaders.AUTHORIZATION, metaHeaders.token)
      .header(MetaHeaders.BROWSER_AGENT, metaHeaders.agent)
      .url(conn.apiUrl + "/subscriptions/site/validate/" + siteId)
      .build()
    val response = conn.getClient.newCall(request).execute().body().string()
    val decodingResult = parser.decode[Boolean](response)
    val json = decodingResult match {
      case Right(entity) => Right(entity)
      case Left(error) => Left(ApiError(error))
    }
    Future.successful(json)
  }

  override def createSiteSubscription(metaHeaders: MetaHeaders, siteId: String, subscriptionId: String): Future[Either[IOError, Boolean]] = {
    val request = new Request.Builder()
      .header(MetaHeaders.AUTHORIZATION, metaHeaders.token)
      .header(MetaHeaders.BROWSER_AGENT, metaHeaders.agent)
      .url(conn.apiUrl + "/subscriptions/site/create/" + siteId + "/" + subscriptionId)
      .build()
    val response = conn.getClient.newCall(request).execute().body().string()
    val decodingResult = parser.decode[Boolean](response)
    val json = decodingResult match {
      case Right(entity) => Right(entity)
      case Left(error) => Left(ApiError(error))
    }
    Future.successful(json)
  }
}
