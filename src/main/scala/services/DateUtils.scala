package services

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DateUtils {
  val DEFAULT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

  def isBetweenInterval(targetDateTime: LocalDateTime, start: LocalDateTime, end: LocalDateTime): Boolean = {
    (targetDateTime.isAfter(start) || targetDateTime.isEqual(start)) && (targetDateTime.isBefore(end) || targetDateTime.isEqual(end))
  }
}
