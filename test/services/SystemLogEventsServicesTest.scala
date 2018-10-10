package services

import java.time.LocalDateTime

import org.scalatest.FunSuite
import domain.systemlogs.SystemLogEvents
import services.systemlogs.SystemLogEventsService
import scala.concurrent.duration._

import scala.concurrent.Await



class SystemLogEventsServicesTest extends FunSuite{

  val date:LocalDateTime=LocalDateTime.now()
  val systemLogEvents=SystemLogEvents("101","1","ButtonClick","action","click here",date)
  val services=SystemLogEventsService

  test("createSystemLogEvents")
  {

    val result = Await.result(services.apply.saveEntity(systemLogEvents), 2.minutes)
    assert(result)

  }



  test("readSystemLogEvents"){
    val result = Await.result(services.apply.getEntity(systemLogEvents.id), 2.minutes)

    assert( result.get.id=="1")
  }

  test("SystemLogEventsUpdate"){
    val result = Await.result(services.apply.getEntity(systemLogEvents.id), 2.minutes)
    val updateSystemLogEvent = result.get.copy(id= "2")
    val savedResult = Await.result(services.apply.saveEntity(updateSystemLogEvent), 2.minutes)
    val newRequest = Await.result(services.apply.getEntity(updateSystemLogEvent.id), 2.minutes)
    assert( newRequest.get.id=="2")
  }

  test("readAllSystemLogEvents"){
    val result = Await.result(services.apply.getEntities, 2.minutes)
    assert(result.size>0)
  }

  test("deleteSystemLogEvents"){
    val result = Await.result(services.apply.deleteEntity(systemLogEvents), 2.minutes)
    assert(result)
  }

}
