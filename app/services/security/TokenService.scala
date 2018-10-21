package services.security

import domain.security.UserGeneratedToken
import domain.users.User
import org.jose4j.jwt.JwtClaims
import services.security.Impl.TokenServiceImpl

import scala.concurrent.Future

trait TokenService {

  def invalidateTokenString(token: String): Future[Boolean]

  def createClaims(email: String, orgCode: String, role: String, agent: String): JwtClaims

  def getTokenStringFromClaims(claims: JwtClaims): Future[String]

  def getJwtClaimsFromTokenString(token: String): Future[JwtClaims]

  def verifyClaimsWithAgent(claims: JwtClaims, agentValue: String): Boolean

  def verifyTokenString(token: String): Future[Boolean]

  def generatedNewUserToken(user: User, agent: String): Future[UserGeneratedToken]

  def getGeneratedToken(email: String): Future[Option[UserGeneratedToken]]

  def isTokenStringValid(token: String, agent: String): Future[Boolean]

  def getUserEmailFromTokenString(token: String): Future[String]

  def getUserRoleFromTokenString(token: String): Future[String]

  def getSiteIdFromTokenString(token: String): Future[String]

  def createAccessToken(id: String, subId: String): Future[String]

  def verifyAccessToken(id:String, token: String): Future[Boolean]

}

object TokenService {
  def apply: TokenService = new TokenServiceImpl()
}
