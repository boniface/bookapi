/*
 * Copyright (c) 2018/09/29 3:14 PM.
 * Author: caniksea
 * Project: bookapi
 * Last Modified: 2018/09/25 8:25 AM
 */

package repository.multimedia

import domain.multimedia.Multimedia
import repository.Repository
import repository.multimedia.impl.cassandra.MultimediaRepositoryImpl

trait MultimediaRepository extends Repository[Multimedia] {}


object MultimediaRepository {
  def apply: MultimediaRepository = new MultimediaRepositoryImpl()
}