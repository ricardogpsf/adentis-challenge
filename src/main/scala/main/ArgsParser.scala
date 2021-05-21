package main

import exceptions.InvalidParametersException
import services.{DateUtils, ProductAgeAfterMonthsComparator, ProductAgeBeforeMonthsComparator, ProductAgeBetweenMonthsComparator, ProductAgeComparator}

import java.time.LocalDateTime
import java.time.format.DateTimeParseException

object ArgsParser {
  val ADDITIONAL_ARGS_ERROR_MESSAGE = "Additional args related to interval of months must be something like: \"1-3\", \">12\" or \"<12\""

  def parseDataTime(dateTime: String): LocalDateTime = {
    try {
      LocalDateTime.parse(dateTime, DateUtils.DEFAULT_FORMATTER)
    } catch {
      case _: DateTimeParseException => throw new InvalidParametersException(s"Date ${dateTime} should have a valid format. Eg.: 2019-12-31 00:00:00")
    }
  }

  def parseAdditionalArg(value: String): ProductAgeComparator = {
    if (!value.matches("(^[0-9]*[><-][0-9]+)")) {
      throw new InvalidParametersException(ADDITIONAL_ARGS_ERROR_MESSAGE)
    }

    var splited = value.split("-")
    if (splited.length == 2) {
      val month1 = parseArgToInt(splited(0))
      val month2 = parseArgToInt(splited(1))
      return new ProductAgeBetweenMonthsComparator(month1, month2, null)
    }

    splited = value.split("<")
    if (splited.length == 2 && splited(0) == "") {
      val month = parseArgToInt(splited(1))
      return new ProductAgeAfterMonthsComparator(month, null)
    }

    splited = value.split(">")
    if (splited.length == 2 && splited(0) == "") {
      val month = parseArgToInt(splited(1))
      return new ProductAgeBeforeMonthsComparator(month, null)
    }

    throw new InvalidParametersException(ADDITIONAL_ARGS_ERROR_MESSAGE)
  }

  def parseArgToInt(value: String): Int = {
    try {
      value.toInt
    } catch {
      case _: NumberFormatException => throw new InvalidParametersException(ADDITIONAL_ARGS_ERROR_MESSAGE)
    }
  }
}
