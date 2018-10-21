package domain.users

import java.time.LocalDateTime

import play.api.libs.json.Json

case class UserPassword(userId: String="",
                        date: LocalDateTime=LocalDateTime.now(),
                        password:String="")

object UserPassword {
  implicit val userPasswordFmt = Json.format[UserPassword]
}