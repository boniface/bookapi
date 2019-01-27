package domain.access.login

import play.api.libs.json.Json

case class Account(siteId:String="",email: String="",userId:String="", siteName:String)

object Account{
  implicit  val userFmt = Json.format[Account]
}