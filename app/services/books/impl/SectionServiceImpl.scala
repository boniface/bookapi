/*
 * Copyright (c) 2018/09/29 3:15 PM.
 * Author: caniksea
 * Project: bookapi
 * Last Modified: 2018/09/24 3:33 PM
 */

package services.books.impl

import domain.books.Section
import repository.books.SectionRepository
import services.books.SectionService

import scala.concurrent.Future

class SectionServiceImpl extends SectionService{
  override def saveEntity(entity: Section): Future[Boolean] = SectionRepository.apply.saveEntity(entity)

  override def getEntities: Future[Seq[Section]] = SectionRepository.apply.getEntities

  override def getChapterSections(chapterId: String): Future[Seq[Section]] = SectionRepository.apply.getChapterSections(chapterId)

  override def getEntity(sectionId: String): Future[Option[Section]] = SectionRepository.apply.getEntity(sectionId)

  override def deleteEntity(entity: Section): Future[Boolean] = SectionRepository.apply.deleteEntity(entity)

  override def createTable: Future[Boolean] = SectionRepository.apply.createTable
}
