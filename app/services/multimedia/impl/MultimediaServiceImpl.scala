/*
 * Copyright (c) 2018/09/29 3:15 PM.
 * Author: caniksea
 * Project: bookapi
 * Last Modified: 2018/09/25 8:49 AM
 */

package services.multimedia.impl

import domain.multimedia.Multimedia
import repository.multimedia.MultimediaRepository
import services.multimedia.MultimediaService

import scala.concurrent.Future

class MultimediaServiceImpl extends MultimediaService {
  override def saveEntity(entity: Multimedia): Future[Boolean] =
    MultimediaRepository.apply.saveEntity(entity)

  override def getEntities: Future[Seq[Multimedia]] = MultimediaRepository.apply.getEntities

  override def getEntity(id: String): Future[Option[Multimedia]] = MultimediaRepository.apply.getEntity(id)

  override def deleteEntity(entity: Multimedia): Future[Boolean] =
    MultimediaRepository.apply.deleteEntity(entity)

  override def createTable: Future[Boolean] = MultimediaRepository.apply.createTable
}
