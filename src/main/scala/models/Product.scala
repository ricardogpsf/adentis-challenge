package models

import java.time.LocalDateTime

class Product(val id: String,
              val name: String,
              val category: String,
              val weight: Double,
              val price: Double,
              val createdAt: LocalDateTime) {}