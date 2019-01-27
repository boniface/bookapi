package services.access.api.Impl

import configuration.connections.NetConnection
import domain.access.{ApiError, IOError, MetaHeaders}
import domain.access.login.{Account, LoginCredential, MessageResponse, Profile}
import domain.access.security.UserGeneratedToken
import io.circe.parser
import io.circe.generic.auto._
import okhttp3.{MediaType, Request, RequestBody}
import play.api.libs.json.Json
import services.access.api.LoginAPI

import scala.concurrent.Future

class LoginAPIImpl extends LoginAPI {
  val conn: NetConnection.type = NetConnection

  override def isUserRegistered(siteId: String, email: String): Future[Either[IOError, Boolean]] = {
    val request = new Request.Builder()
      .url(conn.apiUrl + "/login/isavailable/" + siteId + "/" + email)
      .build()
    val response = conn.getClient.newCall(request).execute().body().string()
    val decodingResult = parser.decode[Boolean](response)
    val json = decodingResult match {
      case Right(entities) => Right(entities)
      case Left(error) => Left(ApiError(error))
    }
    Future.successful(json)
  }

  override def forgotPassword(profile: Profile): Future[Either[IOError, MessageResponse]] = {
    val MEDIA_TYPE_JSON = MediaType.parse("application/json")
    val jsonStr = Json.toJson(profile).toString()
    val request = new Request.Builder()
      .url(conn.apiUrl + "/login/forgotpassword")
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

  override def getLoginToken(credential: LoginCredential, agent: String): Future[Either[IOError, UserGeneratedToken]] = {
    val MEDIA_TYPE_JSON = MediaType.parse("application/json")
    val metaHeaders = MetaHeaders("", agent = agent)
    val jsonStr = Json.toJson(credential).toString()
    val request = new Request.Builder()
      .header(MetaHeaders.AUTHORIZATION, metaHeaders.token)
      .header(MetaHeaders.BROWSER_AGENT, metaHeaders.agent)
      .url(conn.apiUrl + "/login/getlogintoken")
      .post(RequestBody.create(MEDIA_TYPE_JSON, jsonStr))
      .build()
    val response = conn.getClient.newCall(request).execute().body().string()
    val decodingResult = parser.decode[UserGeneratedToken](response)
    val json = decodingResult match {
      case Right(result) => Right(result)
      case Left(error) => Left(ApiError(error))
    }
    Future.successful(json)
  }

  override def getUserAccounts(email: String): Future[Either[IOError, Seq[Account]]] = {
    val request = new Request.Builder()
      .url(conn.apiUrl + "/login/user/" + email)
      .build()
    val response = conn.getClient.newCall(request).execute().body().string()
    val decodingResult = parser.decode[Seq[Account]](response)
    val json = decodingResult match {
      case Right(entities) => Right(entities)
      case Left(error) => Left(ApiError(error))
    }
    Future.successful(json)
  }

  override def resetPasswordRequest(resetKey: String): Future[Either[IOError, MessageResponse]] = {
    val request = new Request.Builder()
      .url(conn.apiUrl + "/login/reset/" + resetKey)
      .build()
    val response = conn.getClient.newCall(request).execute().body().string()
    val decodingResult = parser.decode[MessageResponse](response)
    val json = decodingResult match {
      case Right(entities) => Right(entities)
      case Left(error) => Left(ApiError(error))
    }
    Future.successful(json)
  }
}
