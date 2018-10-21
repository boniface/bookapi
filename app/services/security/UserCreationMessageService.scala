package services.security

import domain.mail.EmailMessage
import domain.users.User
import services.security.Impl.UserCreationMessageServiceImpl

trait UserCreationMessageService {

  def accountCreatedMessage(user:User,generatedPassword:String ): EmailMessage

  def customUserMessage(subject:String, user: User, message:String):EmailMessage

  def passwordResetMessage(user:User, password:String): EmailMessage

}
object UserCreationMessageService{

  def apply: UserCreationMessageService = new UserCreationMessageServiceImpl()
}