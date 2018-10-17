/*
 * Copyright (c) 2018/09/29 3:15 PM.
 * Author: caniksea
 * Project: bookapi
 * Last Modified: 2018/09/24 3:09 PM
 */

package services.books.impl

import domain.books.Chapter
import repository.books.ChapterRepository
import services.books.ChapterService

import scala.concurrent.Future

class ChapterServiceImpl extends ChapterService{
  override def saveEntity(entity: Chapter): Future[Boolean] = ChapterRepository.apply.saveEntity(entity)

  override def getEntities: Future[Seq[Chapter]] = ChapterRepository.apply.getEntities

  override def getBookChapters(bookId: String): Future[Seq[Chapter]] = ChapterRepository.apply.getBookChapters(bookId)

  override def getEntity(chapterId: String): Future[Option[Chapter]] = ChapterRepository.apply.getEntity(chapterId)

  override def deleteEntity(entity: Chapter): Future[Boolean] = ChapterRepository.apply.deleteEntity(entity)

  override def createTable: Future[Boolean] = ChapterRepository.apply.createTable
}
