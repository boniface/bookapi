package domain.access.users

import play.api.libs.json.Json

case class UserLoginToken(userId: String = "",
                          hashedAgent: String = "",
                          id: String = "")

object UserLoginToken {
  implicit val tokenFmt = Json.format[UserLoginToken]
}
