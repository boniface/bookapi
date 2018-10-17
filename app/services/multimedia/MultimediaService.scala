/*
 * Copyright (c) 2018/09/29 3:17 PM.
 * Author: caniksea
 * Project: bookapi
 * Last Modified: 2018/09/25 8:47 AM
 */

package services.multimedia

import domain.multimedia.Multimedia
import services.CrudService
import services.multimedia.impl.MultimediaServiceImpl

trait MultimediaService extends CrudService[Multimedia] {}

object MultimediaService {
  def apply: MultimediaService = new MultimediaServiceImpl()
}
