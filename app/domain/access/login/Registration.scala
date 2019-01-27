package domain.access.login

import play.api.libs.json.Json

case  class Registration(email:String, siteId:String) {

}

object Registration{
  implicit val registerFmt = Json.format[Registration]
}
