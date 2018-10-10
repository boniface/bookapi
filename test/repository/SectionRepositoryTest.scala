package repository

import domain.books.Section
import org.scalatest.FunSuite
import repository.books.SectionRepository
import scala.concurrent.duration._


import scala.concurrent.Await

class SectionRepositoryTest extends FunSuite{


  val section=Section("19","how to do CPR",List("18","28","34"))
  val repository= SectionRepository

  test("createSection"){
    val result = Await.result(repository.apply.saveEntity(section), 2.minutes)
    assert(result)
  }
  test("readSection"){
    val result = Await.result(repository.apply.getEntity(section.id), 2.minutes)

    assert( result.get.title=="how to do CPR")

  }

  test("SectionUpdate"){
    val result = Await.result(repository.apply.getEntity(section.id), 2.minutes)
    val updateSection= result.get.copy(title = "health assessments")
    val savedResult = Await.result(repository.apply.saveEntity(updateSection), 2.minutes)
    val newRequest = Await.result(repository.apply.getEntity(updateSection.id), 2.minutes)
    assert( newRequest.get.title=="health assessments")
  }

  test("readAllSection"){
    val result = Await.result(repository.apply.getEntities, 2.minutes)
    assert(result.size>0)
  }

  test("deleteSection"){
    val result = Await.result(repository.apply.deleteEntity(section), 2.minutes)
    assert(result)
  }



}
