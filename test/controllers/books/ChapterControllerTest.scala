package controllers.books

import domain.books.Chapter
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.libs.json.Json
import play.api.test.Helpers._
import play.api.test.{FakeRequest, Injecting}

class ChapterControllerTest extends PlaySpec with GuiceOneAppPerTest with Injecting {

  val entity = Chapter("1", "TEST")
  val token = "eyJsDbNTlcQag"


  "ChapterController " should {

//    "Create Entity " in {
//      val request = route(app, FakeRequest(POST, "/site/chapter/create")
//        .withJsonBody(Json.toJson(entity))
//        .withHeaders(AUTHORIZATION -> token)
//      ).get
//      status(request) mustBe OK
//      contentType(request) mustBe Some("application/json")
//      println(" The Content is: ", contentAsString(request))
//
//    }
//
//    "Read Entity " in {
//      val request = route(app, FakeRequest(GET, "/site/chapter/get/" + entity.id)
//        .withHeaders(AUTHORIZATION -> token)
//      ).get
//      status(request) mustBe OK
//      contentType(request) mustBe Some("application/json")
//      println(" The Content is: ", contentAsString(request))
//
//
//    }
//
//    "Read Entities" in {
//      val request = route(app, FakeRequest(GET, "/chapters/getall")
//        .withHeaders(AUTHORIZATION -> token)
//      ).get
//      status(request) mustBe OK
//      contentType(request) mustBe Some("application/json")
//      println(" The Content is: ", contentAsString(request))
//    }

    "Read Entities for Ids" in {
      val request = route(app, FakeRequest(GET, "/site/chapters/getforids/" + "1, 3, 4, , 5, 6")
          .withHeaders(AUTHORIZATION -> token)
      ).get
      status(request) mustBe OK
      contentType(request) mustBe Some("application/json")
      println("The content is: ", contentAsString(request))
    }

//    "Update Entity" in {
//      val updatedEntity = entity.copy(title = "Updated")
//      val request = route(app, FakeRequest(POST, "/site/chapter/update")
//        .withJsonBody(Json.toJson(updatedEntity))
//        .withHeaders(AUTHORIZATION -> token)
//      ).get
//      status(request) mustBe OK
//      contentType(request) mustBe Some("application/json")
//      println("The Content is: ", contentAsString(request))
//    }
//
//    "Delete Entities " in {
//      val request = route(app, FakeRequest(POST, "/site/chapter/delete" )
//        .withJsonBody(Json.toJson(entity))
//        .withHeaders(AUTHORIZATION -> token)
//      ).get
//      status(request) mustBe OK
//      contentType(request) mustBe Some("application/json")
//      println(" The Content is: ", contentAsString(request))
//
//    }
  }

}
