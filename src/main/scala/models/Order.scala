package models

import java.time.LocalDateTime

class Order(val id: String,
            val customerName: String,
            val contract: String,
            val shippingAddress: String,
            val grandTotal: Double,
            val createdAt: LocalDateTime,
            val items: List[Item]) {}

object Order {
  def apply(id: String, customerName: String, contract: String, shippingAddress: String, grandTotal: Double, createdAt: LocalDateTime, items: List[Item]): Order = new Order(id, customerName, contract, shippingAddress, grandTotal, createdAt, items)
}