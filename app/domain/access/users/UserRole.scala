package domain.access.users

import java.time.LocalDateTime

import play.api.libs.json.Json

case class UserRole(userId: String,
                    date:LocalDateTime=LocalDateTime.now(),
                    roleId: String="")
object UserRole {
  implicit val userroleFmt = Json.format[UserRole]

}
