package domain.access.users

import java.time.LocalDateTime

import play.api.libs.json.Json

case class UserStatus(userId:String="",
                      date: LocalDateTime=LocalDateTime.now(),
                      status:String=UserState.NOTVALIDATED
                     )

object UserStatus{
  implicit val userFmt = Json.format[UserStatus]

}

