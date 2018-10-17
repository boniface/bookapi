/*
 * Copyright (c) 2018/09/29 3:01 PM.
 * Author: caniksea
 * Project: bookapi
 * Last Modified: 2018/09/25 9:55 AM
 */

package domain.multimedia

import java.time.LocalDateTime

import play.api.libs.json.Json

/**
  *
  * @param multimediaId
  * @param multimediaType
  * @param multimediaName
  * @param multimediaLink
  * @param dateCreated
  */
case class Multimedia(
                     multimediaId: String,
                     multimediaType: String,
                     multimediaName: String,
                     multimediaLink: String,
                     dateCreated: LocalDateTime = LocalDateTime.now
                     )

object Multimedia {
  implicit val multimediaFormat = Json.format[Multimedia]
}
