/*
 * Copyright (c) 2018/09/29 3:02 PM.
 * Author: caniksea
 * Project: bookapi
 * Last Modified: 2018/09/25 9:07 AM
 */

package domain.books

import java.time.LocalDateTime

import play.api.libs.json.Json

/**
  *
  * @param chapterId
  * @param sectionId
  * @param sectionTitle
  * @param sectionDescription
  * @param story
  * @param dateCreated
  */
case class Section(
                    chapterId: String,
                    sectionId: String,
                    sectionTitle: String,
                    sectionDescription: Option[String] = None,
                    story: Option[String] = None,
                    dateCreated: LocalDateTime = LocalDateTime.now
                  )

object Section {
  implicit val sectionFormat = Json.format[Section]
}
