package services

import models.{Item, Order, Product}
import org.scalatest.FunSuite

import java.time.LocalDateTime

class OrderServiceTest extends FunSuite {
  test("OrderService.ordersByInterval") {
    val start = LocalDateTime.parse("2020-01-01 00:00:00", DateUtils.DEFAULT_FORMATTER)
    val end = LocalDateTime.parse("2021-01-01 00:00:00", DateUtils.DEFAULT_FORMATTER)

    // should not be listed
    val orderDate1 = LocalDateTime.parse("2019-12-31 00:00:00", DateUtils.DEFAULT_FORMATTER)
    val orderDate2 = LocalDateTime.parse("2021-01-02 00:00:00", DateUtils.DEFAULT_FORMATTER)

    // should be listed
    val orderDate3 = LocalDateTime.parse("2020-02-01 00:00:00", DateUtils.DEFAULT_FORMATTER)
    val orderDate4 = LocalDateTime.parse("2020-01-02 00:00:00", DateUtils.DEFAULT_FORMATTER)
    val orderDate5 = LocalDateTime.parse("2020-01-01 00:00:00", DateUtils.DEFAULT_FORMATTER)
    val orderDate6 = LocalDateTime.parse("2021-01-01 00:00:00", DateUtils.DEFAULT_FORMATTER)

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

  test("OrderService.groupsOrdersByProductAge") {
    val currentDate = LocalDateTime.parse("2020-12-31 00:00:00", DateUtils.DEFAULT_FORMATTER)

    val productDate1 = LocalDateTime.parse("2020-11-01 00:00:00", DateUtils.DEFAULT_FORMATTER) // 1-3 months
    val productDate2 = LocalDateTime.parse("2020-08-01 00:00:00", DateUtils.DEFAULT_FORMATTER) // 4-6 months
    val productDate3 = LocalDateTime.parse("2020-05-01 00:00:00", DateUtils.DEFAULT_FORMATTER) // 7-12 months
    val productDate4 = LocalDateTime.parse("2019-11-01 00:00:00", DateUtils.DEFAULT_FORMATTER) // >12 months
    val productDate5 = LocalDateTime.parse("2019-10-01 00:00:00", DateUtils.DEFAULT_FORMATTER) // >12 months

    val product1 = Product("1", "", "", 0.0, 0.0, productDate1)
    val product2 = Product("2", "", "", 0.0, 0.0, productDate2)
    val product3 = Product("3", "", "", 0.0, 0.0, productDate3)
    val product4 = Product("4", "", "", 0.0, 0.0, productDate4)
    val product5 = Product("4", "", "", 0.0, 0.0, productDate5)

    val items1 = List(Item("1", 0.0, 0.0, 0.0, "", product1)) // 1-3 months
    val items2 = List(
      Item("2.0", 0.0, 0.0, 0.0, "", product2),
      Item("2.1", 0.0, 0.0, 0.0, "", product5)
    ) // 4-6 months and >12 months
    val items3 = List(Item("3", 0.0, 0.0, 0.0, "", product3)) // 7-12 months
    val items4 = List(Item("4", 0.0, 0.0, 0.0, "", product4)) // >12 months

    val orderDate1 = LocalDateTime.parse("2019-12-31 00:00:00", DateUtils.DEFAULT_FORMATTER)
    val orders = List(
      Order("1", "Joao", "12345", "Some street - Porto", 100.0, orderDate1, items1),
      Order("2", "Joao", "12345", "Some street - Porto", 100.0, orderDate1, items2),
      Order("3", "Joao", "12345", "Some street - Porto", 100.0, orderDate1, items3),
      Order("4", "Joao", "12345", "Some street - Porto", 100.0, orderDate1, items4),
    )

    var result = OrderService.groupsOrdersByProductAge(orders,
      new ProductAgeBetweenMonthsComparator(1, 3, currentDate)
    )
    assert(result.length === 1)
    assert(result.head.id === "1")

    result = OrderService.groupsOrdersByProductAge(orders,
      new ProductAgeBetweenMonthsComparator(4, 6, currentDate)
    )
    assert(result.length === 1)
    assert(result.head.id === "2")

    result = OrderService.groupsOrdersByProductAge(orders,
      new ProductAgeBetweenMonthsComparator(7, 12, currentDate)
    )
    assert(result.length === 1)
    assert(result.head.id === "3")

    result = OrderService.groupsOrdersByProductAge(orders,
      new ProductAgeBeforeMonthsComparator(12, currentDate)
    )
    assert(result.length === 2)
    assert(result.head.id === "2")
    assert(result(1).id === "4")

    result = OrderService.groupsOrdersByProductAge(orders,
      new ProductAgeAfterMonthsComparator(12, currentDate)
    )
    assert(result.length === 5)
  }
}
