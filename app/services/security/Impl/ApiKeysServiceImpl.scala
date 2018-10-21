package services.security.Impl

import configuration.util.HashcodeKeys
import domain.security.ApiKeys
import org.apache.commons.lang3.RandomStringUtils
import org.jose4j.jwk.{EcJwkGenerator, EllipticCurveJsonWebKey, JsonWebKey, PublicJsonWebKey}
import org.jose4j.keys.EllipticCurves
import repository.security.ApiKeysRepository
import services.security.ApiKeysService

import scala.concurrent.Future

class ApiKeysServiceImpl extends ApiKeysService {


  override def saveEntity(entity: ApiKeys): Future[Boolean] = {
    ApiKeysRepository.apply.saveEntity(entity)
  }

  override def getEntities: Future[Seq[ApiKeys]] = {
    ApiKeysRepository.apply.getEntities
  }

  override def getEntity(id: String): Future[Option[ApiKeys]] = {
    ApiKeysRepository.apply.getEntity(id)
  }

  override def deleteEntity(entity: ApiKeys): Future[Boolean] = {
    ApiKeysRepository.apply.deleteEntity(entity)
  }

  override def createTable: Future[Boolean] = {
    ApiKeysRepository.apply.createTable
  }

  override def generateResetToken(): String = {
    val length: Int = 32
    val useLetters: Boolean = true
    val useNumbers: Boolean = true
    RandomStringUtils.random(length, useLetters, useNumbers).toLowerCase
  }

  override def getPublicJsonWebKey(publicApiKey: Option[ApiKeys]): PublicJsonWebKey = {
    val key = publicApiKey match {
      case Some(apiKeys: ApiKeys) =>  apiKeys.value
      case None => ApiKeys.apply().value
    }
    PublicJsonWebKey.Factory.newPublicJwk(key)
  }

  override def generateJsonPublicKey(phrase: String): String = {
    val key: EllipticCurveJsonWebKey = EcJwkGenerator.generateJwk(EllipticCurves.P256)
    key.setKeyId(phrase)
    key.toJson(JsonWebKey.OutputControlLevel.INCLUDE_PRIVATE)
  }

  override def initKey: Future[Boolean] = {
    val key = generateJsonPublicKey(HashcodeKeys.PUBLICKEY)
    val keys = ApiKeys(HashcodeKeys.PUBLICKEY,key,HashcodeKeys.ACTIVE)
    saveEntity(keys)
  }
}
