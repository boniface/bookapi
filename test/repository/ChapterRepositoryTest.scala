package repository

import domain.books.Chapter
import org.scalatest.FunSuite
import repository.books.ChapterRepository

import scala.concurrent.Await
import scala.concurrent.duration._

class ChapterRepositoryTest  extends FunSuite{

  val chapter=Chapter("18","emergencies",List("1","2","3","4","5","6","7","8"))
  val repository= ChapterRepository



  test("createChapter"){
    val result = Await.result(repository.apply.saveEntity(chapter), 2.minutes)
    assert(result)
  }
  test("readChapter"){
    val result = Await.result(repository.apply.getEntity(chapter.id), 2.minutes)

    assert( result.get.title=="emergencies")

  }

  test("ChapterUpdate"){
    val result = Await.result(repository.apply.getEntity(chapter.id), 2.minutes)
    val updateChapter= result.get.copy(title = "Health")
    val savedResult = Await.result(repository.apply.saveEntity(updateChapter), 2.minutes)
    val newRequest = Await.result(repository.apply.getEntity(updateChapter.id), 2.minutes)
    assert( newRequest.get.title=="Health")
  }

  test("readAllChapter"){
    val result = Await.result(repository.apply.getEntities, 2.minutes)
    assert(result.size>0)
  }

  test("deleteChapter"){
    val result = Await.result(repository.apply.deleteEntity(chapter), 2.minutes)
    assert(result)
  }


}
