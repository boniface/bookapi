package domain.access.login

import play.api.libs.json.Json

case class Profile(email:String ="",
                   siteId:String="",
                   userId:String="")

object Profile {
  implicit val credentialFmt = Json.format[Profile]
}
