package services

import java.time.LocalDateTime

import org.scalatest.FunSuite
import services.security.UserTokenService
import domain.security.UserToken

import scala.concurrent.Await
import scala.concurrent.duration._

class UserTokenServicesTest extends FunSuite{

  val expiryDate:LocalDateTime=LocalDateTime.now
  val userToken=UserToken("100","001","120",expiryDate,"123token","valid Token")
  val services=UserTokenService


  test("createUserToken")
  {
    val result = Await.result(services.apply.saveEntity(userToken), 2.minutes)
    assert(result)

  }

  test("readUserToken"){
    val result = Await.result(services.apply.getEntity(userToken.id), 2.minutes)

    assert( result.get.tokenValue=="123token")
  }

  test("UserTokenUpdate"){
    val result = Await.result(services.apply.getEntity(userToken.id), 2.minutes)
    val updateUserToken = result.get.copy(tokenValue= "1234token")
    val savedResult = Await.result(services.apply.saveEntity(updateUserToken), 2.minutes)
    val newRequest = Await.result(services.apply.getEntity(updateUserToken.id), 2.minutes)
    assert( newRequest.get.tokenValue=="1234token")
  }

  test("readAllUserToken"){
    val result = Await.result(services.apply.getEntities, 2.minutes)
    assert(result.size>0)
  }

  test("deleteUserToken") {
    val result = Await.result(services.apply.deleteEntity(userToken), 2.minutes)
    assert(result)


  }


}
