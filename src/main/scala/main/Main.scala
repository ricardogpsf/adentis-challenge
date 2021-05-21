package main

import data.Resource
import exceptions.InvalidParametersException
import services.{OrderService, ProductAgeBeforeMonthsComparator, ProductAgeBetweenMonthsComparator, ProductAgeComparator}

import java.time.LocalDateTime
import scala.collection.mutable.ListBuffer

object Main {
  def main(args: Array[String]): Unit = {
    if (args.length < 2) {
      println("Invalid arguments. You should pass, at least, a date interval. Eg.: \"2018-01-01 00:00:00\" \"2019-01-01 00:00:00\"")
      return
    }

    var startTime: LocalDateTime = null
    var endTime: LocalDateTime = null
    var comparators: ListBuffer[ProductAgeComparator] = null
    try {
      startTime = ArgsParser.parseDateTime(args(0))
      endTime = ArgsParser.parseDateTime(args(0))

      // optional arguments for months interval
      if (args.length > 2) {
        comparators = ListBuffer()
        for (i <- 2 until args.length) {
          comparators += ArgsParser.parseAdditionalArg(args(i))
        }
      }
    } catch {
      case e: InvalidParametersException =>
        println(e.getMessage)
        return
    }

    if (comparators == null) {
      // default additional arguments
      comparators = ListBuffer(
        new ProductAgeBetweenMonthsComparator(1, 3, null),
        new ProductAgeBetweenMonthsComparator(4, 6, null),
        new ProductAgeBetweenMonthsComparator(7, 12, null),
        new ProductAgeBeforeMonthsComparator(12, null)
      )
    }

    val orders = Resource.loadOrders()
    val reportMap: scala.collection.mutable.Map[String, Int] = OrderService.generateOrdersByIntervalReport(orders, startTime, endTime, comparators)

    println("Result:")
    for ((monthsString, ordersCount) <- reportMap) {
      println(s"$monthsString months: $ordersCount orders")
    }
  }
}
