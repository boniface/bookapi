package services.access.login.Impl

import domain.access.security.Claims
import org.jose4j.jwt.NumericDate
import org.jose4j.jwt.consumer.{JwtConsumer, JwtConsumerBuilder, JwtContext}
import services.access.login.LoginTokenService

class LoginTokenServiceImpl extends LoginTokenService {

  lazy val jwtConsumer: JwtConsumer = new JwtConsumerBuilder()
    .setSkipAllValidators()
    .setDisableRequireSignature()
    .setSkipSignatureVerification()
    .build()

  override def hasTokenExpired(token: String): Boolean = {
    def jwtContext: JwtContext = jwtConsumer.process(token)

    jwtContext.getJwtClaims.getExpirationTime.isAfter(NumericDate.now())
  }

  override def getUserEmail(token: String): String = {
    def jwtContext: JwtContext = jwtConsumer.process(token)

    jwtContext.getJwtClaims.getClaimValue(Claims.USER_EMAIL).toString
  }

  override def getUserHashedAgent(token: String): String = {
    def jwtContext: JwtContext = jwtConsumer.process(token)

    jwtContext.getJwtClaims.getClaimValue(Claims.USER_HASHAGENT).toString
  }

  override def getUserRole(token: String): String = {
    def jwtContext: JwtContext = jwtConsumer.process(token)

    jwtContext.getJwtClaims.getClaimValue(Claims.USER_ROLE).toString
  }

  override def getUserSiteId(token: String): String = {
    def jwtContext: JwtContext = jwtConsumer.process(token)

    jwtContext.getJwtClaims.getClaimValue(Claims.USER_SITEID).toString
  }

  override def getUserUserId(token: String): String = {
    def jwtContext: JwtContext = jwtConsumer.process(token)

    jwtContext.getJwtClaims.getClaimValue(Claims.USER_USERID).toString
  }

}
