package services

import models.Order
import org.scalatest.FunSuite

import java.time.LocalDateTime

class OrderServiceTest extends FunSuite {
  test("OrderService.ordersByInterval") {
    val start = LocalDateTime.parse("2020-01-01 00:00:00", DateUtils.defaultFormatter)
    val end = LocalDateTime.parse("2021-01-01 00:00:00", DateUtils.defaultFormatter)

    // should not be listed
    val orderDate1 = LocalDateTime.parse("2019-12-31 00:00:00", DateUtils.defaultFormatter)
    val orderDate2 = LocalDateTime.parse("2021-01-02 00:00:00", DateUtils.defaultFormatter)

    // should be listed
    val orderDate3 = LocalDateTime.parse("2020-02-01 00:00:00", DateUtils.defaultFormatter)
    val orderDate4 = LocalDateTime.parse("2020-01-02 00:00:00", DateUtils.defaultFormatter)
    val orderDate5 = LocalDateTime.parse("2020-01-01 00:00:00", DateUtils.defaultFormatter)
    val orderDate6 = LocalDateTime.parse("2021-01-01 00:00:00", DateUtils.defaultFormatter)

    val orders = List(
      Order("1", "Joao", "12345", "Some street - Porto", 100.0, orderDate1, List()),
      Order("2", "Joao", "12345", "Some street - Porto", 100.0, orderDate2, List()),
      Order("3", "Joao", "12345", "Some street - Porto", 100.0, orderDate3, List()),
      Order("4", "Joao", "12345", "Some street - Porto", 100.0, orderDate4, List()),
      Order("5", "Joao", "12345", "Some street - Porto", 100.0, orderDate5, List()),
      Order("6", "Joao", "12345", "Some street - Porto", 100.0, orderDate6, List()),
    )

    val result = OrderService.ordersByInterval(orders, start, end)

    assert(result.length === 4)
    assert(result(0).id === "3")
    assert(result(1).id === "4")
    assert(result(2).id === "5")
    assert(result(3).id === "6")
  }
}
