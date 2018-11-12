/*
 * Copyright (c) 2018/09/29 3:01 PM.
 * Author: caniksea
 * Project: bookapi
 * Last Modified: 2018/09/25 9:07 AM
 */

package domain.books

import java.time.LocalDateTime

import play.api.libs.json.Json

/**
  *
  * @param bookId
  * @param chapterId
  * @param chapterNumber
  * @param chapterTitle
  * @param chapterDescription
  * @param story
  * @param chapterLink
  * @param dateCreated
  */
case class Chapter(
                    bookId: String,
                    chapterId: String,
                    chapterNumber: Int,
                    chapterTitle: String,
                    chapterDescription: Option[String] = None,
                    story: Option[String] = None,
                    chapterLink: String,
                    dateCreated: LocalDateTime = LocalDateTime.now
                  )

object Chapter {
  implicit val chapterFormat = Json.format[Chapter]
}