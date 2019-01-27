package domain.access.roles

import play.api.libs.json.Json

/**
  * Created by hashcode on 2016/08/12.
  */
case class Roles(id: String, rolename: String)

object Roles {

  implicit val rolesFmt = Json.format[Roles]

  def identity: Roles = Roles(RoleValues.ANONYMOUS, RoleValues.ANONYMOUS)

}
