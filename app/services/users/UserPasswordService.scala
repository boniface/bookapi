package services.users

import domain.users.UserPassword
import services.CrudService
import services.users.Impl.UserPasswordServiceImpl

trait UserPasswordService extends CrudService[UserPassword]{

}

object UserPasswordService {
  def apply: UserPasswordService = new UserPasswordServiceImpl()
}
