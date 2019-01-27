package domain.access.login

import play.api.libs.json.Json

case class MessageResponse(statusCode: Int=0, headers: Map[String, String]=Map.empty, body: String="")

object MessageResponse {
  implicit val messageFmt = Json.format[MessageResponse]
}
