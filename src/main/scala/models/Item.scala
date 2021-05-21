package models

class Item( val id: String,
            val cost: Double,
            val shippingFee: Double,
            val taxAmount: Double,
            val productId: String,
            val product: Product) {}