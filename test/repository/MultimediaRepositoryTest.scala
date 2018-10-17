package repository

import java.time.LocalDateTime

import domain.multimedia.Multimedia
import org.scalatest.FunSuite
import repository.multimedia.MultimediaRepository

import scala.concurrent.Await
import scala.concurrent.duration._

class MultimediaRepositoryTest extends FunSuite{


  val dateCreated: LocalDateTime=LocalDateTime.now
  val multimedia=Multimedia("1","video","Foundation","http://motsepefoundation.org/",dateCreated)
  val repository=MultimediaRepository



  test("createMultimedia"){
    val result = Await.result(repository.apply.saveEntity(multimedia), 2.minutes)
    assert(result)
  }
  test("readMultimedia"){
    val result = Await.result(repository.apply.getEntity(multimedia.multimediaId), 2.minutes)
    assert( result.get.multimediaType=="video")

  }

  test("MultimediaUpdate"){
    val result = Await.result(repository.apply.getEntity(multimedia.multimediaId), 2.minutes)
    val updateMultimedia= result.get.copy(multimediaType = "Image")
    val savedResult = Await.result(repository.apply.saveEntity(updateMultimedia), 2.minutes)
    val newRequest = Await.result(repository.apply.getEntity(updateMultimedia.multimediaId), 2.minutes)
    assert( newRequest.get.multimediaType=="Image")
  }

  test("readAllMultimedia"){
    val result = Await.result(repository.apply.getEntities, 2.minutes)
    assert(result.size>0)
  }

  test("deleteMultimedia"){
    val result = Await.result(repository.apply.deleteEntity(multimedia), 2.minutes)
    assert(result)
  }

}
