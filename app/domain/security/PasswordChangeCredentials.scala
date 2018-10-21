package domain.security

import play.api.libs.json.Json

case class PasswordChangeCredentials(
                                      email:String ="",
                                      userId:String="",
                                      oldPassword:String="",
                                      newPassword:String="") {
  require(newPassword.nonEmpty)
}

object PasswordChangeCredentials {
  implicit val credentialFmt = Json.format[PasswordChangeCredentials]
}
