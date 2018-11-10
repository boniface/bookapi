package controllers.books

import domain.books.{Book, Chapter}
import javax.inject.Inject
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._
import services.books.{BookService, ChapterService}
import domain.security.TokenFailExcerption

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * @author caniksea
  * @param cc
  */
class ChapterController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

//  def populateEntity(book: Option[Book], entity: Chapter) = {
//    val b = book.getOrElse(null)
//    val bookTitle = b.bookTitle
//    val chapterTitle = entity.chapterTitle
//    val sublink = chapterTitle.split(" ").map(_.toLowerCase).mkString("-")
//    val chapterLink = "/" + bookTitle + "/" + sublink
//    entity.copy(chapterLink = chapterLink)
//  }

  def create: Action[JsValue] = Action.async(parse.json) {
    request =>
      val input = request.body
      val entity = Json.fromJson[Chapter](input).get
      val response = for {
        //        auth <- TokenCheckService.apply.getLoginStatus(request)
        results <- ChapterService.apply.saveEntity(entity)
      } yield results
      response.map(ans => Ok(Json.toJson(entity)))
        .recover {
          case tokenFailExcerption: TokenFailExcerption => Unauthorized
          case otherException: Exception => InternalServerError
        }
  }

  def update: Action[JsValue] = create

  def getEntity(id: String): Action[AnyContent] = Action.async {
    implicit request: Request[AnyContent] =>
      val input = request.body
      val response = for {
        //        auth <- TokenCheckService.apply.getLoginStatus(request)
        result <- ChapterService.apply.getEntity(id)
      } yield result
      response.map(ans => Ok(Json.toJson(ans)))
        .recover {
          case tokenFailExcerption: TokenFailExcerption => Unauthorized
          case otherException: Exception => InternalServerError
        }
  }

  def getEntities: Action[AnyContent] = Action.async {
    implicit request: Request[AnyContent] =>
      val input = request.body
      val response = for {
        //        auth <- TokenCheckService.apply.getLoginStatus(request)
        results <- ChapterService.apply.getEntities
      } yield results
      response.map(ans => Ok(Json.toJson(ans.sortBy(_.chapterNumber))))
        .recover {
          case tokenFailExcerption: TokenFailExcerption => Unauthorized
          case otherException: Exception => InternalServerError
        }
  }

  def getBookChapters(bookId: String): Action[AnyContent] = Action.async {
    implicit request: Request[AnyContent] =>
      val input = request.body
      val response = for {
        //        auth <- TokenCheckService.apply.getLoginStatus(request)
        results <- ChapterService.apply.getBookChapters(bookId)
      } yield results
      response.map(ans => Ok(Json.toJson(ans)))
        .recover {
          case tokenFailExcerption: TokenFailExcerption => Unauthorized
          case otherException: Exception => InternalServerError
        }
  }

  def delete: Action[JsValue] = Action.async(parse.json) {
    request =>
      val input = request.body
      val entity = Json.fromJson[Chapter](input).get
      val response = for {
        //        auth <- TokenCheckService.apply.getLoginStatus(request)
        result <- ChapterService.apply.deleteEntity(entity)
      } yield result
      response.map(ans => Ok(Json.toJson(ans)))
        .recover {
          case tokenFailExcerption: TokenFailExcerption => Unauthorized
          case otherException: Exception => InternalServerError
        }
  }

}
