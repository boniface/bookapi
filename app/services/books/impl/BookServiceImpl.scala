/*
 * Copyright (c) 2018/09/29 3:15 PM.
 * Author: caniksea
 * Project: bookapi
 * Last Modified: 2018/09/21 10:13 AM
 */

package services.books.impl

import domain.books.Book
import repository.books.BookRepository
import services.books.BookService

import scala.concurrent.Future

class BookServiceImpl extends BookService{
  override def saveEntity(entity: Book): Future[Boolean] = BookRepository.apply.saveEntity(entity)

  override def getSiteEntities(siteId: String): Future[Seq[Book]] = BookRepository.apply.getSiteEntities(siteId)

  override def getEntities: Future[Seq[Book]] = BookRepository.apply.getEntities

  override def getEntity(id: String): Future[Option[Book]] = BookRepository.apply.getEntity(id)

  override def deleteEntity(entity: Book): Future[Boolean] = BookRepository.apply.deleteEntity(entity)

  override def createTable: Future[Boolean] = BookRepository.apply.createTable
}
