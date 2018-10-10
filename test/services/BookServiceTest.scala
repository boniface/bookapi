package services

import java.time.LocalDateTime

import domain.books.Book
import org.scalatest.FunSuite
import services.books.BookService

import scala.concurrent.duration._

import scala.concurrent.Await

class BookServiceTest  extends FunSuite{
  val datePublished:LocalDateTime=LocalDateTime.now()
  val book=Book("11","0101","little black book","978-1-928333-02-9","978-1-928333-03-6","womens's unit","Motsepe Foundation",datePublished,List("1","2","3","4","5"))
  val services= BookService

  test("createBook"){
    val result = Await.result(services.apply.saveEntity(book), 2.minutes)
    assert(result)
  }
  test("readBook"){
    val result = Await.result(services.apply.getEntity(book.id), 2.minutes)

    assert( result.get.title=="little black book")

  }

  test("BookUpdate"){
    val result = Await.result(services.apply.getEntity(book.id), 2.minutes)
    val updatedBook = result.get.copy(title = "little Book")
    val savedResult = Await.result(services.apply.saveEntity(updatedBook), 2.minutes)
    val newRequest = Await.result(services.apply.getEntity(updatedBook.id), 2.minutes)
    assert( newRequest.get.title=="little Book")
  }

  test("readAllBook"){
    val result = Await.result(services.apply.getEntities, 2.minutes)
    assert(result.size>0)
  }

  test("deleteBook"){
    val result = Await.result(services.apply.deleteEntity(book), 2.minutes)
    assert(result)
  }




}
