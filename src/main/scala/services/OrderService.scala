package services

import models.Order

import java.time.LocalDateTime

object OrderService {
  def ordersByInterval(orders: List[Order], start: LocalDateTime, end: LocalDateTime): List[Order] = {
    orders.filter(order => DateUtils.isBetweenInterval(order.createdAt, start, end))
  }
}
