package domain.access.security

/**
  * Created by hashcode on 2017/02/19.
  */
case class TokenFailExcerption(message: String = "", cause: Throwable = null)
  extends Exception(message, cause)


