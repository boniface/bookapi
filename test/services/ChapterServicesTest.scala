package services

import domain.books.Chapter
import org.scalatest.FunSuite
import services.books.ChapterService

import scala.concurrent.Await
import scala.concurrent.duration._


class ChapterServicesTest extends FunSuite {
  val chapter=Chapter("18","emergencies",List("1","2","3","4","5","6","7","8"))
  val services= ChapterService



  test("createChapter"){
    val result = Await.result(services.apply.saveEntity(chapter), 2.minutes)
    assert(result)
  }
  test("readChapter"){
    val result = Await.result(services.apply.getEntity(chapter.id), 2.minutes)

    assert( result.get.title=="emergencies")

  }

  test("ChapterUpdate"){
    val result = Await.result(services.apply.getEntity(chapter.id), 2.minutes)
    val updateChapter= result.get.copy(title = "Health")
    val savedResult = Await.result(services.apply.saveEntity(updateChapter), 2.minutes)
    val newRequest = Await.result(services.apply.getEntity(updateChapter.id), 2.minutes)
    assert( newRequest.get.title=="Health")
  }

  test("readAllChapter"){
    val result = Await.result(services.apply.getEntities, 2.minutes)
    assert(result.size>0)
  }

  test("deleteChapter"){
    val result = Await.result(services.apply.deleteEntity(chapter), 2.minutes)
    assert(result)
  }

}
