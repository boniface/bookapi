/*
 * Copyright (c) 2018/09/29 3:01 PM.
 * Author: caniksea
 * Project: bookapi
 * Last Modified: 2018/09/29 2:51 PM
 */

package domain.books

import java.time.{LocalDateTime}

abstract class BookBase {
  val id: String
  val title: String
  val description: Option[String] = None
  val story: Option[String] = None
  val multimedias: List[String] = List[String]()
  val dateCreated: LocalDateTime = LocalDateTime.now
}
