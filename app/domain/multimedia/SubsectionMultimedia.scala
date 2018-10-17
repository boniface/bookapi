package domain.multimedia

import java.time.LocalDateTime

import play.api.libs.json.Json

/**
  *
  * @param subsectionId
  * @param multimediaId
  * @param dateCreated
  */
case class SubsectionMultimedia(
                               subsectionId: String,
                               multimediaId: String,
                               dateCreated: LocalDateTime = LocalDateTime.now
                               )

object SubsectionMultimedia {
  implicit val subsectionMultimediaFormat = Json.format[SubsectionMultimedia]
}
