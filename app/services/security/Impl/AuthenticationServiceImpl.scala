package services.security.Impl

import org.apache.commons.lang3.RandomStringUtils
import org.mindrot.jbcrypt.BCrypt
import services.security.AuthenticationService

class AuthenticationServiceImpl extends AuthenticationService{

  override def getHashedPassword(password: String): String = {
    BCrypt.hashpw(password, BCrypt.gensalt(12))
  }

  override def generateRandomPassword(): String = {
    val length: Int = 8
    val useLetters: Boolean = true
    val useNumbers: Boolean = true
    RandomStringUtils.random(length, useLetters, useNumbers)
  }

  override def checkPassword(candidate: String, hashed: String): Boolean = {
    BCrypt.checkpw(candidate, hashed)
  }

}
