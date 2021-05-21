package models

class Item( val id: String,
            val cost: Double,
            val shippingFee: Double,
            val taxAmount: Double,
            val productId: String,
            val product: Product) {}

object Item {
  def apply(id: String, cost: Double, shippingFee: Double, taxAmount: Double, productId: String, product: Product): Item = new Item(id, cost, shippingFee, taxAmount, productId, product)
}