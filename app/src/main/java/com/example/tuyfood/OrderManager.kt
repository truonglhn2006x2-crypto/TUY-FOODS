package com.example.tuyfood

object OrderManager {

    private var nextOrderId: Int = 1001

    var currentOrder: Order? = null
        private set

    fun createOrder(): Order? {

        if (CartManager.items.isEmpty()) {
            return null
        }

        val order = Order(
            orderId = nextOrderId,
            items = CartManager.items.toList(),
            total = CartManager.getTotal(),
            status = 1
        )

        nextOrderId++

        currentOrder = order

        CartManager.clearCart()

        return order
    }

    fun updateStatus(status: Int) {
        currentOrder?.status = status
    }
}