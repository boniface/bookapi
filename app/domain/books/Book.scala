/*
 * Copyright (c) 2018/09/29 3:01 PM.
 * Author: caniksea
 * Project: bookapi
 * Last Modified: 2018/09/29 2:50 PM
 */
package domain.books

import java.time.LocalDateTime

import play.api.libs.json.Json

/**
  *
  * @param siteId
  * @param bookId
  * @param bookTitle
  * @param bookDescription
  * @param story
  * @param isbn
  * @param eisbn
  * @param author
  * @param publisher
  * @param datePublished
  * @param dateCreated
  */
case class Book(
                 siteId: String,
                 bookId: String,
                 bookTitle: String,
                 bookDescription: Option[String] = None,
                 story: Option[String] = None,
                 isbn: Option[String] = None,
                 eisbn: Option[String] = None,
                 author: String,
                 publisher: String,
                 datePublished: LocalDateTime,
                 dateCreated: LocalDateTime = LocalDateTime.now
               ) {}

object Book {
  implicit val bookFormat = Json.format[Book]
}

