package domain.access.roles

import play.api.libs.json.Json

case class Role(siteId: String = "",
                id: String = "",
                roleName: String = "",
                description: String = "")

object Role {

  implicit val rolesFmt = Json.format[Role]

}
