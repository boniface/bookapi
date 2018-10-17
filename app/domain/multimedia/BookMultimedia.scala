package domain.multimedia

import java.time.LocalDateTime

import play.api.libs.json.Json

/**
  *
  * @param bookId
  * @param multimediaId
  * @param dateCreated
  */
case class BookMultimedia(
                           bookId: String,
                           multimediaId: String,
                           dateCreated: LocalDateTime = LocalDateTime.now
                         )

object BookMultimedia {
  implicit val bookMultimediaFormat = Json.format[BookMultimedia]
}
