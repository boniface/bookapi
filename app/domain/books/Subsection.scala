/*
 * Copyright (c) 2018/09/29 3:02 PM.
 * Author: caniksea
 * Project: bookapi
 * Last Modified: 2018/09/23 4:13 PM
 */

package domain.books

import java.time.LocalDateTime

import play.api.libs.json.Json

/**
  *
  * @param sectionId
  * @param subsectionId
  * @param subsectionTitle
  * @param subsectionDescription
  * @param story
  * @param dateCreated
  */
case class Subsection(
                       sectionId: String,
                       subsectionId: String,
                       subsectionTitle: String,
                       subsectionDescription: Option[String] = None,
                       story: Option[String] = None,
                       dateCreated: LocalDateTime = LocalDateTime.now
                     )

object Subsection {
  implicit val subsectionFormat = Json.format[Subsection]
}
