package services.access.users

import domain.access.users.UserPassword
import services.access.CrudServiceAPI
import services.access.users.Impl.UserPasswordServiceImpl

trait UserPasswordService extends CrudServiceAPI[UserPassword]{

}

object UserPasswordService {
  def apply: UserPasswordService = new UserPasswordServiceImpl()
}
