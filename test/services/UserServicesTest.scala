package services

import java.time.LocalDateTime

import org.scalatest.FunSuite
import domain.users.User
import services.users.UserService
import scala.concurrent.duration._


import scala.concurrent.Await

class UserServicesTest extends FunSuite{
  val dateCreated:LocalDateTime=LocalDateTime.now()

  val user=User("10001","djannyngana@outlook.com","14",Some("john"), None, Some("Doe"),"dog",dateCreated)
  val services=UserService

  test("createUser")
  {
    val result = Await.result(services.apply.saveEntity(user), 2.minutes)
    assert(result)

  }

  test("readUser"){
    val result = Await.result(services.apply.getEntity(user.siteId), 2.minutes)

    assert( result.get.userId=="15")
  }

  test("UserUpdate"){
    val result = Await.result(services.apply.getEntity(user.siteId), 2.minutes)
    val updatedUser= result.get.copy(userId = "16")
    val savedResult = Await.result(services.apply.saveEntity(updatedUser), 2.minutes)
    val newRequest = Await.result(services.apply.getEntity(updatedUser.userId), 2.minutes)
    assert( newRequest.get.userId=="16")
  }

  test("readAllUser"){
    val result = Await.result(services.apply.getEntities, 2.minutes)
    assert(result.size>0)
  }

  test("deleteUser") {
    val result = Await.result(services.apply.deleteEntity(user), 2.minutes)
    assert(result)
  }




}
