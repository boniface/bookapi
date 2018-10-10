/*
 * Copyright (c) 2018/09/29 3:14 PM.
 * Author: caniksea
 * Project: bookapi
 * Last Modified: 2018/09/24 4:05 PM
 */

package repository.books

import domain.books.Subsection
import repository.Repository
import repository.books.impl.cassandra.SubsectionRepositoryImpl

import scala.concurrent.Future

trait SubsectionRepository extends Repository[Subsection] {
  def getEntitiesForIds(ids: List[String]): Future[Seq[Subsection]]
}

object SubsectionRepository {
  def apply: SubsectionRepository = new SubsectionRepositoryImpl()
}
