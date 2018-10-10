package controllers.books

import javax.inject.Inject
import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._

class BookApiRouter @Inject() (
                                bookController: BookController,
                                chapterController: ChapterController,
                                sectionController: SectionController,
                                subsectionController: SubsectionController
                              ) extends SimpleRouter {
  override def routes: Routes = {

    //For Books
    case GET(p"/getall") => bookController.getEntities // for super admin
    case GET(p"/site/getall/$siteId") => bookController.getSiteEntities(siteId)
    case GET(p"/site/book/get/$id") => bookController.getEntity(id)
    case POST(p"/site/book/create") => bookController.create
    case POST(p"/site/book/update") => bookController.update
    case POST(p"/site/book/delete") => bookController.delete

      // For Chapters
    case GET(p"/chapters/getall") => chapterController.getEntities // for super admin
    case GET(p"/site/chapters/getforids/$ids") => chapterController.getEntitiesForIds(ids)
    case GET(p"/site/chapter/get/$id") => chapterController.getEntity(id)
    case POST(p"/site/chapter/create") => chapterController.create
    case POST(p"/site/chapter/update") => chapterController.update
    case POST(p"/site/chapter/delete") => chapterController.delete

      // For Sections
    case GET(p"/site/sections/getforids/$ids") => sectionController.getEntitiesForIds(ids)
    case GET(p"/site/section/get/$id") => sectionController.getEntity(id)
    case POST(p"/site/section/create") => sectionController.create
    case POST(p"/site/section/update") => sectionController.update
    case POST(p"/site/section/delete") => sectionController.delete

      // For Subsections
    case GET(p"/site/subsections/getforids/$ids") => subsectionController.getEntitiesForIds(ids)
    case GET(p"/site/subsection/get/$id") => subsectionController.getEntity(id)
    case POST(p"/site/subsection/create") => subsectionController.create
    case POST(p"/site/subsection/update") => subsectionController.update
    case POST(p"/site/subsection/delete") => subsectionController.delete
  }
}
