package domain.access

import configuration.util.HashcodeKeys
import play.api.mvc.Request

case class MetaHeaders(token:String, agent:String, contentType:String ="" )

object MetaHeaders{
  def BROWSER_AGENT="User-Agent"
  def AUTHORIZATION="Authorization"
  def getHeaders[A](request: Request[A]): MetaHeaders ={
      val token = request.headers.get(HashcodeKeys.AUTHORIZATION).getOrElse("")
      val agent = request.headers.get(HashcodeKeys.BROWSER_AGENT).getOrElse("")
      MetaHeaders(token,agent)
  }
}
