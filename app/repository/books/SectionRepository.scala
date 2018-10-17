/*
 * Copyright (c) 2018/09/29 3:14 PM.
 * Author: caniksea
 * Project: bookapi
 * Last Modified: 2018/09/24 3:12 PM
 */

package repository.books

import domain.books.Section
import repository.Repository
import repository.books.impl.cassandra.SectionRepositoryImpl

import scala.concurrent.Future

trait SectionRepository extends Repository[Section] {

  def getChapterSections(chapterId: String): Future[Seq[Section]]

}

object SectionRepository {
  def apply: SectionRepository = new SectionRepositoryImpl()
}
