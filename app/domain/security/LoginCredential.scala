package domain.security

import play.api.libs.json.Json

case class LoginCredential(email:String ="", siteId:String="", userId:String="", password:String="")

object LoginCredential {
  implicit val credentialFmt = Json.format[LoginCredential]
}
