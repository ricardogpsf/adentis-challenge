package models

import java.time.LocalDateTime

class Product(val id: String,
              val name: String,
              val category: String,
              val weight: Double,
              val price: Double,
              val createdAt: LocalDateTime) {}

object Product {
  def apply(id: String, name: String, category: String, weight: Double, price: Double, createdAt: LocalDateTime): Product = new Product(id, name, category, weight, price, createdAt)
}