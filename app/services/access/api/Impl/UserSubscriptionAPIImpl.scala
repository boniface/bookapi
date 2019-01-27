package services.access.api.Impl

import configuration.connections.NetConnection
import domain.access.subscription.UserSubscriptions
import domain.access.users.User
import domain.access.{ApiError, IOError, MetaHeaders}
import io.circe.parser
import io.circe.generic.auto._
import okhttp3.{MediaType, Request, RequestBody}
import play.api.libs.json.Json
import services.access.api.UserSubscriptionAPI

import scala.concurrent.Future

class UserSubscriptionAPIImpl extends UserSubscriptionAPI{
  val conn = NetConnection
  type Entity = UserSubscriptions

  override def createUserSubscription(metaHeaders: MetaHeaders, user: User, subscriptionId: String): Future[Either[IOError, Boolean]] = {
    val MEDIA_TYPE_JSON = MediaType.parse("application/json")
    val jsonStr = Json.toJson(user).toString()
    val request = new Request.Builder()
      .header(MetaHeaders.AUTHORIZATION, metaHeaders.token)
      .header(MetaHeaders.BROWSER_AGENT, metaHeaders.agent)
      .url(conn.apiUrl + "/subscriptions/user/create/"+subscriptionId)
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

  override def saveEntity(headers: MetaHeaders, entity: Entity): Future[Either[IOError, Boolean]] = {
    val MEDIA_TYPE_JSON = MediaType.parse("application/json")
    val jsonStr = Json.toJson(entity).toString()
    val request = new Request.Builder()
      .header(MetaHeaders.AUTHORIZATION, headers.token)
      .header(MetaHeaders.BROWSER_AGENT, headers.agent)
      .url(conn.apiUrl + "/subscriptions/user/update")
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

  override def getEntities(headers: MetaHeaders, userId: String): Future[Either[IOError, Seq[Entity]]] = {
    val request = new Request.Builder()
      .header(MetaHeaders.AUTHORIZATION, headers.token)
      .header(MetaHeaders.BROWSER_AGENT, headers.agent)
      .url(conn.apiUrl + "/subscriptions/user/get/site/" + userId)
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
      .url(conn.apiUrl + "/subscriptions/user/get/" + id)
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
      .url(conn.apiUrl + "/subscriptions/user/delete")
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

  override def getUsersSubscriptionsForSite(metaHeaders: MetaHeaders, siteId: String): Future[Either[IOError, Seq[Entity]]] = {
    val request = new Request.Builder()
      .header(MetaHeaders.AUTHORIZATION, metaHeaders.token)
      .header(MetaHeaders.BROWSER_AGENT, metaHeaders.agent)
      .url(conn.apiUrl + "/user/get/site/" + siteId)
      .build()
    val response = conn.getClient.newCall(request).execute().body().string()
    val decodingResult = parser.decode[Seq[Entity]](response)
    val json = decodingResult match {
      case Right(entities) => Right(entities)
      case Left(error) => Left(ApiError(error))
    }
    Future.successful(json)
  }



  override def validateUserSubscription(metaHeaders: MetaHeaders, siteId: String, userId: String): Future[Either[IOError, Boolean]] = {
    val request = new Request.Builder()
      .header(MetaHeaders.AUTHORIZATION, metaHeaders.token)
      .header(MetaHeaders.BROWSER_AGENT, metaHeaders.agent)
      .url(conn.apiUrl + "/subscriptions/user/validate/" + siteId + "/" + userId)
      .build()
    val response = conn.getClient.newCall(request).execute().body().string()
    val decodingResult = parser.decode[Boolean](response)
    val json = decodingResult match {
      case Right(entity) => Right(entity)
      case Left(error) => Left(ApiError(error))
    }
    Future.successful(json)
  }

  override def getUserSubscriptionsFromSite(metaHeaders: MetaHeaders, siteId: String, userId: String): Future[Either[IOError, Seq[Entity]]] = {
    val request = new Request.Builder()
      .header(MetaHeaders.AUTHORIZATION, metaHeaders.token)
      .header(MetaHeaders.BROWSER_AGENT, metaHeaders.agent)
      .url(conn.apiUrl + "/subscriptions/user/site/" + siteId+"/"+userId)
      .build()
    val response = conn.getClient.newCall(request).execute().body().string()
    val decodingResult = parser.decode[Seq[Entity]](response)
    val json = decodingResult match {
      case Right(entities) => Right(entities)
      case Left(error) => Left(ApiError(error))
    }
    Future.successful(json)
  }
}
