package data

import models.{Item, Order, Product}
import services.DateUtils

import java.time.LocalDateTime
import java.util.UUID
import scala.collection.mutable.ListBuffer

object Resource {

  def loadOrders(): List[Order] = {
    val dates = List(
      LocalDateTime.parse("2018-02-02 00:00:00", DateUtils.DEFAULT_FORMATTER),
      LocalDateTime.parse("2019-11-02 00:00:00", DateUtils.DEFAULT_FORMATTER),
      LocalDateTime.parse("2019-02-02 00:00:00", DateUtils.DEFAULT_FORMATTER),
      LocalDateTime.parse("2020-11-02 00:00:00", DateUtils.DEFAULT_FORMATTER),
      LocalDateTime.parse("2021-02-02 00:00:00", DateUtils.DEFAULT_FORMATTER)
    )
    val items = ListBuffer[Item]()
    val bufferedSource = io.Source.fromFile(getClass.getClassLoader.getResource("amazon_com.csv").getPath)
    for (line <- bufferedSource.getLines) {
      val Array(name, price, category, weight, createdAt) = line.split(";").map(_.trim)
      val productId = UUID.randomUUID().toString
      val product = Product(productId, name, category, weight.toDouble, price.toDouble, LocalDateTime.parse(createdAt, DateUtils.DEFAULT_FORMATTER))
      val item = Item(UUID.randomUUID().toString, 5, 5, 5, productId, product)
      items.addOne(item)
    }
    bufferedSource.close

    val orders = ListBuffer[Order]()
    var fromItemIdx = 0
    var toItemIdx = 0
    for(i <- 0 to 1000) {
      val idxOfDate = i % dates.length
      toItemIdx = fromItemIdx + 2
      if (toItemIdx >= items.length) {
        fromItemIdx = 0
        toItemIdx = 4
      }
      val tempItems = items.slice(fromItemIdx, toItemIdx)
      orders.addOne(Order(UUID.randomUUID().toString, "Any name", "any contract", "Rua do Porto - Porto", 100, dates(idxOfDate), tempItems.toList))
      fromItemIdx += 1
    }

    orders.toList
  }
}
