package services.security.Impl

import java.time.LocalDateTime

import com.typesafe.config.ConfigFactory
import configuration.util.{Events, HashcodeKeys}
import domain.security.{ApiKeys, UserGeneratedToken, UserToken}
import domain.users.User
import org.jose4j.jwa.AlgorithmConstraints
import org.jose4j.jwa.AlgorithmConstraints.ConstraintType
import org.jose4j.jws.{AlgorithmIdentifiers, JsonWebSignature}
import org.jose4j.jwt.JwtClaims
import org.jose4j.jwt.consumer.JwtConsumerBuilder
import services.security
import services.security.{ApiKeysService, AuthenticationService, TokenService, UserTokenService}
import services.users.UserRoleService

import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Future
import scala.util.{Failure, Success, Try}

class TokenServiceImpl extends TokenService {
  val config = ConfigFactory.load()
  val time = config.getInt("key.inMinutes")



  override def createClaims(email: String, siteId: String, role: String, agent: String): JwtClaims = {
    val hashedAgent = AuthenticationService.apply.getHashedPassword(agent)
    val claims = new JwtClaims
    claims.setIssuer(HashcodeKeys.ISSUER)
    claims.setAudience(HashcodeKeys.SITEUSERS)
    claims.setExpirationTimeMinutesInTheFuture(time)
    claims.setGeneratedJwtId()
    claims.setIssuedAtToNow()
    claims.setNotBeforeMinutesInThePast(2)
    claims.setSubject(HashcodeKeys.SITEACCESS)
    claims.setClaim(HashcodeKeys.EMAIL, email)
    claims.setClaim(HashcodeKeys.ROLE, role)
    claims.setClaim(HashcodeKeys.SITEID, siteId)
    claims.setClaim(HashcodeKeys.AGENT, hashedAgent)
    claims
  }

  override def getTokenStringFromClaims(claims: JwtClaims): Future[String] = {
    for {
      publickKey: Option[ApiKeys] <- ApiKeysService.apply.getEntity(HashcodeKeys.PUBLICKEY)
    } yield {
      val senderJwk = ApiKeysService.apply.getPublicJsonWebKey(publickKey)
      val jws = new JsonWebSignature
      jws.setPayload(claims.toJson)
      jws.setKey(senderJwk.getPrivateKey)
      jws.setKeyIdHeaderValue(senderJwk.getKeyId)
      jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.ECDSA_USING_P256_CURVE_AND_SHA256)
      jws.getCompactSerialization
    }
  }

  override def getJwtClaimsFromTokenString(token: String): Future[JwtClaims] = {
    for {
      publickKey: Option[ApiKeys] <- ApiKeysService.apply.getEntity(HashcodeKeys.PUBLICKEY)
    } yield {
      val senderJwk = ApiKeysService.apply.getPublicJsonWebKey(publickKey)
      val jwsAlgConstraints = new AlgorithmConstraints(ConstraintType.WHITELIST, AlgorithmIdentifiers.ECDSA_USING_P256_CURVE_AND_SHA256)
      val jwtConsumer = new JwtConsumerBuilder()
        .setRequireExpirationTime()
        .setAllowedClockSkewInSeconds(30)
        .setRequireSubject()
        .setExpectedIssuer(HashcodeKeys.ISSUER)
        .setExpectedAudience(HashcodeKeys.SITEUSERS)
        .setVerificationKey(senderJwk.getPublicKey)
        .setJwsAlgorithmConstraints(jwsAlgConstraints)
        .build()
      jwtConsumer.processToClaims(token)
    }
  }

  override def verifyClaimsWithAgent(claims: JwtClaims, agentValue: String): Boolean = {
    val agent = claims.getClaimsMap.get(HashcodeKeys.AGENT)
    AuthenticationService.apply.checkPassword(agentValue, agent.toString)
  }

  override def verifyTokenString(token: String): Future[Boolean] = {
    for {
      publickKey: Option[ApiKeys] <- ApiKeysService.apply.getEntity(HashcodeKeys.PUBLICKEY)
    } yield {
      val senderJwk = ApiKeysService.apply.getPublicJsonWebKey(publickKey)
      val jwsAlgConstraints = new AlgorithmConstraints(ConstraintType.WHITELIST, AlgorithmIdentifiers.ECDSA_USING_P256_CURVE_AND_SHA256)
      val jwtConsumer = new JwtConsumerBuilder()
        .setRequireExpirationTime()
        .setAllowedClockSkewInSeconds(30)
        .setRequireSubject()
        .setExpectedIssuer(HashcodeKeys.ISSUER)
        .setExpectedAudience(HashcodeKeys.SITEUSERS)
        .setVerificationKey(senderJwk.getPublicKey)
        .setJwsAlgorithmConstraints(jwsAlgConstraints)
        .build()
      Try(jwtConsumer.processToClaims(token)) match {
        case Success(claim) => true
        case Failure(f: Throwable) => false
      }
    }
  }

  private  def getClaimAttributeFromTokenString(token: String, claim: String): Future[String] = {
    getJwtClaimsFromTokenString(token) map (claims => claims.getClaimsMap().get(claim).toString)
  }

  override def getUserRoleFromTokenString(token: String): Future[String] = {
    getClaimAttributeFromTokenString(token, HashcodeKeys.ROLE)
  }

  override def getUserEmailFromTokenString(token: String): Future[String] = {
    getClaimAttributeFromTokenString(token, HashcodeKeys.EMAIL)
  }

  override def getSiteIdFromTokenString(token: String): Future[String] = {
    getClaimAttributeFromTokenString(token, HashcodeKeys.SITEID)
  }



  override def generatedNewUserToken(user: User, agent: String): Future[UserGeneratedToken] = {
    for {
      role <- UserRoleService.apply.getEntity(user.userId)
      claims <- Future {
        createClaims(user.email, user.siteId, role.get.roleId, agent) // Null Pointer Potential Refactor
      }
      token: String <- getTokenStringFromClaims(claims)
    } yield {
      val createdToken = UserToken(user.siteId,
        user.userId,
        claims.getJwtId,
        LocalDateTime.now.plusMinutes(time),
        token,
        Events.TOKENCREATED)
      UserTokenService.apply.saveEntity(createdToken)
      UserGeneratedToken(token, Events.TOKENVALID, Events.TOKENSUCCESSMESSAGE, user.email)
    }
  }

  override def getGeneratedToken(email: String): Future[Option[UserGeneratedToken]] = ???

  override def isTokenStringValid(token: String, agent: String): Future[Boolean] = {
    val isTokenValid = for {
      valid <- verifyTokenString(token)
      hashExpired <- tokeInStorage(token)
    } yield {
      if (valid) {
        for {
          claims <- getJwtClaimsFromTokenString(token)
        } yield verifyClaimsWithAgent(claims, agent) && hashExpired
      } else {
        Future {
          false
        }
      }
    }
    isTokenValid.flatten
  }

  override def invalidateTokenString(token: String): Future[Boolean] = {
    for{
      claim <- getJwtClaimsFromTokenString(token)
      userToken  <- UserTokenService.apply.getEntity(claim.getJwtId)
      deleted <-UserTokenService.apply.deleteEntity(userToken.getOrElse(UserToken.apply()))
    } yield deleted
  }

  private def tokeInStorage(tokenId: String): Future[Boolean] = security.UserTokenService.apply.getEntity(tokenId) map {
    case Some(_) => true
    case None => false
  }

  override def createAccessToken(id: String, subId: String): Future[String] = ???
  override def verifyAccessToken(id:String, token: String): Future[Boolean] = for {
    publicKey  <- ApiKeysService.apply.getEntity(HashcodeKeys.PUBLICKEY)
  } yield verifyTokens(id, token:String, publicKey)

  private def verifyTokens(id:String, token:String, publickKey: Option[ApiKeys]):Boolean = {
    val senderJwk = ApiKeysService.apply.getPublicJsonWebKey(publickKey)
    val jwsAlgConstraints = new AlgorithmConstraints(ConstraintType.WHITELIST, AlgorithmIdentifiers.ECDSA_USING_P256_CURVE_AND_SHA256)
    val jwtConsumer = new JwtConsumerBuilder()
      .setRequireExpirationTime()
      .setAllowedClockSkewInSeconds(30)
      .setRequireSubject()
      .setExpectedIssuer(HashcodeKeys.ISSUER)
      .setExpectedAudience(id)
      .setVerificationKey(senderJwk.getPublicKey)
      .setJwsAlgorithmConstraints(jwsAlgConstraints)
      .build()
    Try(jwtConsumer.processToClaims(token)) match {
      case Success(_) => true
      case Failure(_: Throwable) => false
    }
  }
}
