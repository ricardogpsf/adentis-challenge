package services

import java.time.LocalDateTime

trait ProductAgeComparator {
  def apply(createdAt: LocalDateTime): Boolean
  def toMonthsString: String
}

class ProductAgeBetweenMonthsComparator(startMonth: Int, endMonth: Int, referenceDate: LocalDateTime)
  extends ProductAgeComparator {
  def apply(createdAt: LocalDateTime): Boolean = {
    val refDate = if (referenceDate == null) LocalDateTime.now else referenceDate

    val from = refDate.minusMonths(startMonth)
    val to = refDate.minusMonths(endMonth)
    createdAt.isBefore(from) && createdAt.isAfter(to)
  }

  def toMonthsString: String = s"$startMonth-$endMonth"
}

class ProductAgeBeforeMonthsComparator(month: Int, referenceDate: LocalDateTime)
  extends ProductAgeComparator {
  def apply(createdAt: LocalDateTime): Boolean = {
    val refDate = if (referenceDate == null) LocalDateTime.now else referenceDate

    val goal = refDate.minusMonths(month)
    createdAt.isBefore(goal)
  }

  def toMonthsString: String = s">$month"
}

class ProductAgeAfterMonthsComparator(month: Int, referenceDate: LocalDateTime)
  extends ProductAgeComparator {
  def apply(createdAt: LocalDateTime): Boolean = {
    val refDate = if (referenceDate == null) LocalDateTime.now else referenceDate

    val goal = refDate.minusMonths(month)
    createdAt.isAfter(goal)
  }

  def toMonthsString: String = s"<$month"
}
