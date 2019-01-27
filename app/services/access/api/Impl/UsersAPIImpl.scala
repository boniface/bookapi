package services.access.api.Impl

import configuration.connections.NetConnection
import domain.access.login.{MessageResponse, PasswordChangeCredentials, Registration}
import domain.access.{ApiError, IOError, MetaHeaders}
import io.circe.generic.auto._
import io.circe.parser
import okhttp3.{MediaType, Request, RequestBody}
import play.api.libs.json.Json
import services.access.api.UsersAPI

import scala.concurrent.Future

class UsersAPIImpl extends UsersAPI {
  val conn = NetConnection


  override def saveEntity(headers: MetaHeaders, entity: Entity): Future[Either[IOError, Boolean]] = {
    val MEDIA_TYPE_JSON = MediaType.parse("application/json")
    val jsonStr = Json.toJson(entity).toString()
    val request = new Request.Builder()
      .header(MetaHeaders.AUTHORIZATION, headers.token)
      .header(MetaHeaders.BROWSER_AGENT, headers.agent)
      .url(conn.apiUrl + "/users/update")
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
      .url(conn.apiUrl + "/all/site/" + siteId)
      .build()
    val response = conn.getClient.newCall(request).execute().body().string()
    val decodingResult = parser.decode[Seq[Entity]](response)
    val json = decodingResult match {
      case Right(entities) => Right(entities)
      case Left(error) => Left(ApiError(error))
    }
    Future.successful(json)
  }

  override def getEntity(headers: MetaHeaders, userId: String): Future[Either[IOError, Entity]] = {
    val request = new Request.Builder()
      .header(MetaHeaders.AUTHORIZATION, headers.token)
      .header(MetaHeaders.BROWSER_AGENT, headers.agent)
      .url(conn.apiUrl + "/users/get/" + userId)
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
      .url(conn.apiUrl + "/users/delete")
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

  override def getSiteUsers(metaHeaders: MetaHeaders, siteId: String): Future[Either[IOError, Seq[Entity]]] = {
    getEntities(metaHeaders, siteId)
  }

  override def getUserByEmail(metaHeaders: MetaHeaders, email: String): Future[Either[IOError, Seq[Entity]]] = {
    val request = new Request.Builder()
      .header(MetaHeaders.AUTHORIZATION, metaHeaders.token)
      .header(MetaHeaders.BROWSER_AGENT, metaHeaders.agent)
      .url(conn.apiUrl + "/users/email/" + email)
      .build()
    val response = conn.getClient.newCall(request).execute().body().string()
    val decodingResult = parser.decode[Seq[Entity]](response)
    val json = decodingResult match {
      case Right(entities) => Right(entities)
      case Left(error) => Left(ApiError(error))
    }
    Future.successful(json)
  }


  override def isUserAvailable(metaHeaders: MetaHeaders, siteId: String, email: String): Future[Either[IOError, Boolean]] = {
    val request = new Request.Builder()
      .header(MetaHeaders.AUTHORIZATION, metaHeaders.token)
      .header(MetaHeaders.BROWSER_AGENT, metaHeaders.agent)
      .url(conn.apiUrl + "/users/available/" + siteId + "/" + email)
      .build()
    val response = conn.getClient.newCall(request).execute().body().string()
    val decodingResult = parser.decode[Boolean](response)
    val json = decodingResult match {
      case Right(entities) => Right(entities)
      case Left(error) => Left(ApiError(error))
    }
    Future.successful(json)
  }

  override def createUser(metaHeaders: MetaHeaders, user: Entity): Future[Either[IOError, MessageResponse]] = {
    val MEDIA_TYPE_JSON = MediaType.parse("application/json")
    val jsonStr = Json.toJson(user).toString()
    val request = new Request.Builder()
      .header(MetaHeaders.AUTHORIZATION, metaHeaders.token)
      .header(MetaHeaders.BROWSER_AGENT, metaHeaders.agent)
      .url(conn.apiUrl + "/users/create")
      .post(RequestBody.create(MEDIA_TYPE_JSON, jsonStr))
      .build()
    val response = conn.getClient.newCall(request).execute().body().string()
    val decodingResult = parser.decode[MessageResponse](response)
    val json = decodingResult match {
      case Right(result) => Right(result)
      case Left(error) => Left(ApiError(error))
    }
    Future.successful(json)
  }

  override def resetAccount(metaHeaders: MetaHeaders, user: Entity): Future[Either[IOError, MessageResponse]] = {
    val MEDIA_TYPE_JSON = MediaType.parse("application/json")
    val jsonStr = Json.toJson(user).toString()
    val request = new Request.Builder()
      .header(MetaHeaders.AUTHORIZATION, metaHeaders.token)
      .header(MetaHeaders.BROWSER_AGENT, metaHeaders.agent)
      .url(conn.apiUrl + "/users/reset")
      .post(RequestBody.create(MEDIA_TYPE_JSON, jsonStr))
      .build()
    val response = conn.getClient.newCall(request).execute().body().string()
    val decodingResult = parser.decode[MessageResponse](response)
    val json = decodingResult match {
      case Right(result) => Right(result)
      case Left(error) => Left(ApiError(error))
    }
    Future.successful(json)
  }

  override def changePassword(metaHeaders: MetaHeaders, credentials: PasswordChangeCredentials): Future[Either[IOError, Boolean]] = {
    val MEDIA_TYPE_JSON = MediaType.parse("application/json")
    val jsonStr = Json.toJson(credentials).toString()
    val request = new Request.Builder()
      .header(MetaHeaders.AUTHORIZATION, metaHeaders.token)
      .header(MetaHeaders.BROWSER_AGENT, metaHeaders.agent)
      .url(conn.apiUrl + "/users/chpasswd")
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



  override def registerUser(metaHeaders: MetaHeaders, value: Registration): Future[Either[IOError, MessageResponse]]= {
    val MEDIA_TYPE_JSON = MediaType.parse("application/json")
    val jsonStr = Json.toJson(value).toString()
    val request = new Request.Builder()
      .header(MetaHeaders.AUTHORIZATION, metaHeaders.token)
      .header(MetaHeaders.BROWSER_AGENT, metaHeaders.agent)
      .url(conn.apiUrl + "/users/register")
      .post(RequestBody.create(MEDIA_TYPE_JSON, jsonStr))
      .build()
    val response = conn.getClient.newCall(request).execute().body().string()
    val decodingResult = parser.decode[MessageResponse](response)
    val json = decodingResult match {
      case Right(result) => Right(result)
      case Left(error) => Left(ApiError(error))
    }
    Future.successful(json)
  }
}
