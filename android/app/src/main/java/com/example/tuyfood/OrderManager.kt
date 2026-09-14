package com.example.tuyfood

object OrderManager {

    private var nextOrderId: Int = 1001

    var currentOrder: Order? = null
        private set

    fun createOrder(): Order? {

        // Không tạo đơn nếu giỏ hàng trống
        if (CartManager.items.isEmpty()) {
            return null
        }

        /*
         * Sao chép danh sách món trước khi
         * CartManager.clearCart() được gọi.
         */
        val orderItems =
            CartManager.items.map { item ->
                item.copy()
            }

        /*
         * Lấy tổng cuối cùng:
         * tiền món
         * - voucher đơn hàng
         * + phí vận chuyển
         * - voucher vận chuyển
         */
        val finalTotal =
            CartManager.getFinalTotal()

        val order = Order(
            orderId = nextOrderId,
            items = orderItems,
            total = finalTotal,
            status = 1
        )

        nextOrderId++

        currentOrder = order

        // Xóa giỏ và voucher sau khi đặt thành công
        CartManager.clearCart()

        return order
    }

    fun updateStatus(status: Int) {
        currentOrder?.status = status
    }
}