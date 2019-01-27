package domain.access.users

import java.time.LocalDateTime

import play.api.libs.json.Json

case class User(siteId:String="",
                email: String="",
                userId:String="",
                screenName:Option[String]=None,
                firstName:Option[String]=None,
                middleName:Option[String]=None,
                lastName:Option[String]=None,
                password: String="",
                dateCreated:LocalDateTime=LocalDateTime.now())
object User{
  implicit  val userFmt = Json.format[User]
}