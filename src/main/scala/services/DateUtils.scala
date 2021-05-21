package services

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DateUtils {
  val defaultFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

  def isBetweenInterval(targetDateTime: LocalDateTime, start: LocalDateTime, end: LocalDateTime): Boolean = {
    (targetDateTime.isAfter(start) || targetDateTime.isEqual(start)) && (targetDateTime.isBefore(end) || targetDateTime.isEqual(end))
  }

  def isBetweenMonths(targetDateTime: LocalDateTime, startMonth: Int, endMonth: Int, referenceDate: LocalDateTime): Boolean = {
    val refDate = if (referenceDate == null) LocalDateTime.now else referenceDate

    val from = refDate.minusMonths(startMonth)
    val to = refDate.minusMonths(endMonth)
    targetDateTime.isBefore(from) && targetDateTime.isAfter(to)
  }

  def isAfterMonths(targetDateTime: LocalDateTime, month: Int, referenceDate: LocalDateTime): Boolean = {
    val refDate = if (referenceDate == null) LocalDateTime.now else referenceDate

    val goal = refDate.minusMonths(month)
    targetDateTime.isAfter(goal)
  }

  def isBeforeMonths(targetDateTime: LocalDateTime, month: Int, referenceDate: LocalDateTime): Boolean = {
    val refDate = if (referenceDate == null) LocalDateTime.now else referenceDate

    val goal = refDate.minusMonths(month)
    targetDateTime.isBefore(goal)
  }
}
