package main

import exceptions.InvalidParametersException
import org.scalatest.FunSuite
import services.{ProductAgeAfterMonthsComparator, ProductAgeBeforeMonthsComparator, ProductAgeBetweenMonthsComparator, ProductAgeComparator}

import java.time.LocalDateTime

class ArgsParserTest extends FunSuite {
  test("ArgsParserTest.parseDateTime") {
    val expected = LocalDateTime.of(2019, 12, 31, 0, 0, 0)
    val result = ArgsParser.parseDateTime("2019-12-31 00:00:00")
    assert(result.equals(expected))
  }

  test("ArgsParserTest.parseDateTime throwing exception") {
    val expectedMessage = "Invalid parameters - Date '2019/12/31 00:00:00' must have a valid format. Eg.: 2019-12-31 00:00:00"
    val caught = intercept[InvalidParametersException] {
      ArgsParser.parseDateTime("2019/12/31 00:00:00") // invalid format
    }
    assert(caught.getMessage.equals(expectedMessage))
  }

  test("ArgsParserTest.parseAdditionalArg") {
    var result = ArgsParser.parseAdditionalArg("1-3")
    assert(result.isInstanceOf[ProductAgeBetweenMonthsComparator])
    assert(result.toMonthsString === "1-3")

    result = ArgsParser.parseAdditionalArg(">12")
    assert(result.isInstanceOf[ProductAgeBeforeMonthsComparator])
    assert(result.toMonthsString === ">12")

    result = ArgsParser.parseAdditionalArg("<5")
    assert(result.isInstanceOf[ProductAgeAfterMonthsComparator])
    assert(result.toMonthsString === "<5")
  }

  test("ArgsParserTest.parseAdditionalArg throwing exception") {
    val expectedMessage = "Invalid parameters - " + ArgsParser.ADDITIONAL_ARGS_ERROR_MESSAGE
    var caught = intercept[InvalidParametersException] {
      ArgsParser.parseAdditionalArg("1-")
    }
    assert(caught.getMessage.equals(expectedMessage))

    caught = intercept[InvalidParametersException] {
      ArgsParser.parseAdditionalArg("1>")
    }
    assert(caught.getMessage.equals(expectedMessage))

    caught = intercept[InvalidParametersException] {
      ArgsParser.parseAdditionalArg("1>3")
    }
    assert(caught.getMessage.equals(expectedMessage))

    caught = intercept[InvalidParametersException] {
      ArgsParser.parseAdditionalArg(">")
    }
    assert(caught.getMessage.equals(expectedMessage))

    caught = intercept[InvalidParametersException] {
      ArgsParser.parseAdditionalArg("a-3")
    }
    assert(caught.getMessage.equals(expectedMessage))
  }
}
