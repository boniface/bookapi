package services


import java.time.LocalDateTime

import org.scalatest.FunSuite
import services.security.ApiKeysService
import domain.security.ApiKeys

import scala.concurrent.duration._
import scala.concurrent.Await

class securityServicesTest extends FunSuite{

  val date:LocalDateTime=LocalDateTime.now()

  val apiKeys= ApiKeys("3","200","Available",date)

  val services= ApiKeysService

  test("createApiKeys")
  {
    val result = Await.result(services.apply.saveEntity(apiKeys), 2.minutes)
    assert(result)

  }

  test("readApiKeys"){
    val result = Await.result(services.apply.getEntity(apiKeys.id), 2.minutes)

    assert( result.get.value=="200")
  }

  test("ApiKeysUpdate"){
    val result = Await.result(services.apply.getEntity(apiKeys.id), 2.minutes)
    val updatedApiKeys = result.get.copy(value= "2001")
    val savedResult = Await.result(services.apply.saveEntity(updatedApiKeys), 2.minutes)
    val newRequest = Await.result(services.apply.getEntity(apiKeys.id), 2.minutes)
    assert( newRequest.get.value=="2001")
  }

  test("readAllApiKeys"){
    val result = Await.result(services.apply.getEntities, 2.minutes)
    assert(result.size>0)
  }

  test("deleteApiKeys"){
    val result = Await.result(services.apply.deleteEntity(apiKeys), 2.minutes)
    assert(result)
  }

}
