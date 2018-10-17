package domain.multimedia

import java.time.LocalDateTime

import play.api.libs.json.Json

/**
  *
  * @param sectionId
  * @param multimediaId
  * @param dateCreated
  */
case class SectionMultimedia(
                            sectionId: String,
                            multimediaId: String,
                            dateCreated: LocalDateTime = LocalDateTime.now
                            )

object SectionMultimedia {
  implicit val sectionMultimediaFormat = Json.format[SectionMultimedia]
}