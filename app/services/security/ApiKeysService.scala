package services.security

import domain.security.ApiKeys
import org.jose4j.jwk.PublicJsonWebKey
import services.CrudService
import services.security.Impl.ApiKeysServiceImpl

import scala.concurrent.Future

trait ApiKeysService extends CrudService[ApiKeys]{
  def initKey:Future[Boolean]

  def generateResetToken():String

  def getPublicJsonWebKey(publicApiKey: Option[ApiKeys]):PublicJsonWebKey

  def generateJsonPublicKey(phrase: String): String

}

object ApiKeysService{
  def apply: ApiKeysService = new ApiKeysServiceImpl()
}
