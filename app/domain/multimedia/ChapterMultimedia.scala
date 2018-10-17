package domain.multimedia

import java.time.LocalDateTime

import play.api.libs.json.Json

/**
  *
  * @param chapterId
  * @param multimediaId
  * @param dateCreated
  */
case class ChapterMultimedia(
                            chapterId: String,
                            multimediaId: String,
                            dateCreated: LocalDateTime = LocalDateTime.now
                            )

object ChapterMultimedia {
  implicit val chapterMultimediaFormat = Json.format[ChapterMultimedia]
}
