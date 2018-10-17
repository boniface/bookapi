/*
 * Copyright (c) 2018/09/29 3:14 PM.
 * Author: caniksea
 * Project: bookapi
 * Last Modified: 2018/09/21 8:34 AM
 */

package repository.books

import domain.books.Book
import repository.Repository
import repository.books.impl.cassandra.BookRepositoryImpl

import scala.concurrent.Future

trait BookRepository extends Repository[Book]{

  def getSiteEntities(siteId: String): Future[Seq[Book]]

  def getBookCountForSite(siteId: String): Future[Option[Long]]

}

object BookRepository {
  def apply: BookRepository = new BookRepositoryImpl()
}
