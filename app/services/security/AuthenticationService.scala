package services.security

import services.security.Impl.AuthenticationServiceImpl

trait AuthenticationService {

  def getHashedPassword(key: String): String

  def generateRandomPassword(): String

  def checkPassword(candidate: String, hash: String): Boolean

}

object AuthenticationService {
  def apply: AuthenticationService = new AuthenticationServiceImpl()
}


