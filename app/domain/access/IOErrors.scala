package domain.access

sealed trait IOError

case class ApiError(error:io.circe.Error) extends IOError

