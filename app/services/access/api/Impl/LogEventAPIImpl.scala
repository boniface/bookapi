package services.access.api.Impl

import configuration.connections.NetConnection
import domain.access.systemlogs.LogEvent
import domain.access.{ApiError, IOError, MetaHeaders}
import io.circe.parser
import io.circe.generic.auto._
import okhttp3.{MediaType, Request, RequestBody}
import play.api.libs.json.Json
import services.access.api.LogEventAPI

import scala.concurrent.Future

class LogEventAPIImpl extends LogEventAPI {
  val conn: NetConnection.type = NetConnection
  type Entity = LogEvent
  val headers = MetaHeaders("None","None")

  override def saveEntity(entity: Entity): Future[Either[IOError, Boolean]] = {
    val MEDIA_TYPE_JSON = MediaType.parse("application/json")
    val jsonStr = Json.toJson(entity).toString()
    val request = new Request.Builder()
      .header(MetaHeaders.AUTHORIZATION, headers.token)
      .header(MetaHeaders.BROWSER_AGENT, headers.agent)
      .url(conn.apiUrl + "/systemlogs/create")
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

  override def getEntities(siteId: String): Future[Either[IOError, Seq[Entity]]] = {
    val request = new Request.Builder()
      .header(MetaHeaders.AUTHORIZATION, headers.token)
      .header(MetaHeaders.BROWSER_AGENT, headers.agent)
      .url(conn.apiUrl + "/systemlogs/get/site/" + siteId)
      .build()
    val response = conn.getClient.newCall(request).execute().body().string()
    val decodingResult = parser.decode[Seq[Entity]](response)
    val json = decodingResult match {
      case Right(entities) => Right(entities)
      case Left(error) => Left(ApiError(error))
    }
    Future.successful(json)
  }

  override def getEntity(id: String): Future[Either[IOError, Entity]] = {
    val request = new Request.Builder()
      .header(MetaHeaders.AUTHORIZATION, headers.token)
      .header(MetaHeaders.BROWSER_AGENT, headers.agent)
      .url(conn.apiUrl + "/systemlogs/get/" + id)
      .build()
    val response = conn.getClient.newCall(request).execute().body().string()
    val decodingResult = parser.decode[Entity](response)
    val json = decodingResult match {
      case Right(entity) => Right(entity)
      case Left(error) => Left(ApiError(error))
    }
    Future.successful(json)
  }

  override def deleteEntity(entity: Entity): Future[Either[IOError, Boolean]] = {
    val MEDIA_TYPE_JSON = MediaType.parse("application/json")
    val jsonStr = Json.toJson(entity).toString()
    val request = new Request.Builder()
      .header(MetaHeaders.AUTHORIZATION, headers.token)
      .header(MetaHeaders.BROWSER_AGENT, headers.agent)
      .url(conn.apiUrl + "/systemlogs/delete")
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
}
