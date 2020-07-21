package extractors

object Email extends ((String, String) => String) {
  // injection method - optional
  def apply(user: String, domain: String) = user + "@" + domain

  // extraction method - mandatory
  def unapply(str: String) = {
    val parts = str split "@"
    if (parts.length == 2) Some(parts(0), parts(1)) else None
  }
}

object Twice extends (String => String) {
  def apply(s: String): String = s + s

  def unapply(s: String): Option[String] = {
    val length = s.length / 2
    val half = s.substring(0, length)
    if (half == s.substring(length)) Some(half) else None
  }
}