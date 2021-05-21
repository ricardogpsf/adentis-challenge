package services

import models.Order

import java.time.LocalDateTime
import scala.collection.mutable.ListBuffer

object OrderService {
  def ordersByInterval(orders: List[Order], start: LocalDateTime, end: LocalDateTime): List[Order] = {
    orders.filter(order => DateUtils.isBetweenInterval(order.createdAt, start, end))
  }

  def groupsOrdersByProductAge(orders: List[Order], productAgeComparator: ProductAgeComparator): List[Order] = {
    orders.filter(order => order.items.exists(item => productAgeComparator.apply(item.product.createdAt)))
  }

  def generateOrdersByIntervalReport(orders: List[Order], from: LocalDateTime, to: LocalDateTime, comparators: ListBuffer[ProductAgeComparator]): scala.collection.mutable.Map[String, Int] = {
    val mapResult = scala.collection.mutable.Map[String, Int]()
    val ordersByInterval = OrderService.ordersByInterval(orders, from, to)
    comparators.foreach(comparator => {
      val count = OrderService.groupsOrdersByProductAge(ordersByInterval, comparator).length
      mapResult(comparator.toMonthsString) = count
    })

    mapResult
  }
}
