package services.security.Impl

import java.time.LocalDateTime

import com.typesafe.config.ConfigFactory
import configuration.util.{Events, HashcodeKeys}
import domain.mail.{EmailMessage, MessageResponse}
import domain.security._
import domain.users.{User, UserPassword}
import play.api.mvc.Request
import services.mail.MailService
import services.security._
import scala.concurrent.ExecutionContext.Implicits.global
import services.users.{UserPasswordService, UserService}

import scala.concurrent.Future

class  LoginServiceImpl extends LoginService{
  val config = ConfigFactory.load()
  val baseUrl = config.getString("base.url")

  override def resetPassword(token: String): Future[Option[Int]] = ???

  override def getUserAccounts(email: String): Future[Seq[User]] = ???

  override def isUserRegistered(entity: User): Future[Boolean] = ???

  override def getLoginToken(credential: LoginCredential, agent: String): Future[UserGeneratedToken] = {
    val createdToken = for {
      user <- UserService.apply.getEntity(credential.userId)
      userPassword <- UserPasswordService.apply.getEntity(credential.userId)
    } yield {
      val userProfile: User = user.get
      if (AuthenticationService.apply.checkPassword(credential.password, userPassword.get.password)
        && userProfile.siteId.equalsIgnoreCase(credential.siteId)) {
        TokenService.apply.generatedNewUserToken(userProfile, agent)
      } else {
        Future {
          UserGeneratedToken(Events.TOKENFAILED, Events.TOKENINVALID, Events.TOKENFAILMESSAGE, userProfile.email)
        }
      }
    }
    createdToken.flatten
  }


  override def forgotPassword(profile: Profile): Future[MessageResponse] = {
    val emailEmessage = EmailMessage("Password Change Request",profile.email,"")
    val resetTokenString = ApiKeysService.apply.generateResetToken()
    val resetToken = ResetToken(resetTokenString,profile.userId)
    for {
      user <- UserService.apply.getEntity(profile.userId)
      saveToken <- ResetTokenService.apply.saveEntity(resetToken)
      send: MessageResponse <- MailService.sendGrid.sendMail(emailEmessage.copy(content = SiteMessages.PASSWDRESET+baseUrl+"/security/reset/"+resetTokenString))
    } yield send
  }

  override def resetAccount(user: User): Future[MessageResponse] = {
    val generatedPassword = AuthenticationService.apply.generateRandomPassword()
    val newPassword = AuthenticationService.apply.getHashedPassword(generatedPassword)
    val userPassword = UserPassword(user.userId,LocalDateTime.now,newPassword)
    UserPasswordService.apply.saveEntity(userPassword)
    val emailMessage = UserCreationMessageService.apply.passwordResetMessage(user,generatedPassword)

    for {
      sent <- MailService.sendGrid.sendMail(emailMessage)
    } yield sent
  }

  override def getLoginStatus[A](request: Request[A]): Future[LoginStatus] =  {
    checkTokenStatus(
      request.headers.get(HashcodeKeys.AUTHORIZATION).getOrElse(""),
      request.headers.get(HashcodeKeys.BROWSER_AGENT).getOrElse(""))
  }

  private def checkTokenStatus(token: String, agent: String): Future[LoginStatus] = {
    if (ConfigFactory.load().getBoolean("token-security.enabled")) {
      TokenService.apply.isTokenStringValid(token, agent) map (isValid => {
        if (isValid) LoginStatus(Events.TOKENVALID) else throw TokenFailExcerption("Error")
      })
    } else Future.successful(LoginStatus(Events.TOKENVALID))
  }

  override def requestNewPassword(token: String): Future[MessageResponse] = {
    for{
      userId <- ResetTokenService.apply.getEntity(token)
      user <- UserService.apply.getEntity(userId.get.email)
      send <-  resetAccount(user.getOrElse(User.apply()))
    } yield send
  }

  override def changePassword(credentials: PasswordChangeCredentials): Future[Boolean] = {

    for{
      userPassword <- UserPasswordService.apply.getEntity(credentials.userId) if AuthenticationService.apply.checkPassword(credentials.oldPassword,userPassword.get.password)
      user <- UserPasswordService.apply.saveEntity(UserPassword(credentials.userId,LocalDateTime.now, AuthenticationService.apply.getHashedPassword(credentials.newPassword)))
    } yield user

  }
}
