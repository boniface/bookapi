package services

import java.time.LocalDateTime

import domain.books.Multimedia
import org.scalatest.FunSuite
import services.books.MultimediaService

import scala.concurrent.Await
import scala.concurrent.duration._

class MultimediaServicesTest extends FunSuite{

  val dateCreated: LocalDateTime=LocalDateTime.now
  val multimedia=Multimedia("1","video","Foundation","http://motsepefoundation.org/",dateCreated)
  val services=MultimediaService



  test("createMultimedia"){
    val result = Await.result(services.apply.saveEntity(multimedia), 2.minutes)
    assert(result)
  }
  test("readMultimedia"){
    val result = Await.result(services.apply.getEntity(multimedia.multimediaId), 2.minutes)
    assert( result.get.multimediaType=="video")

  }

  test("MultimediaUpdate"){
    val result = Await.result(services.apply.getEntity(multimedia.multimediaId), 2.minutes)
    val updateMultimedia= result.get.copy(multimediaType = "Image")
    val savedResult = Await.result(services.apply.saveEntity(updateMultimedia), 2.minutes)
    val newRequest = Await.result(services.apply.getEntity(updateMultimedia.multimediaId), 2.minutes)
    assert( newRequest.get.multimediaType=="Image")
  }

  test("readAllMultimedia"){
    val result = Await.result(services.apply.getEntities, 2.minutes)
    assert(result.size>0)
  }

  test("deleteMultimedia"){
    val result = Await.result(services.apply.deleteEntity(multimedia), 2.minutes)
    assert(result)
  }

}
