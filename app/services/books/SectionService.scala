/*
 * Copyright (c) 2018/09/29 3:17 PM.
 * Author: caniksea
 * Project: bookapi
 * Last Modified: 2018/09/24 3:33 PM
 */

package services.books

import domain.books.Section
import services.CrudService
import services.books.impl.SectionServiceImpl

import scala.concurrent.Future

trait SectionService extends CrudService[Section] {

  def getChapterSections(chapterId: String): Future[Seq[Section]]

}

object SectionService {
  def apply: SectionService = new SectionServiceImpl()
}
