package domain.access.login

import play.api.libs.json.Json

case class ResetToken(resetokenvalue: String="", userId: String="")

object ResetToken {

  implicit val tokenResets = Json.format[ResetToken]
}
