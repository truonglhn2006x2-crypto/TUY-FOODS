package com.example.tuyfood

const val ORDER_CANCELLED = 0
const val ORDER_PLACED = 1
const val ORDER_CONFIRMED = 2
const val ORDER_PREPARING = 3
const val ORDER_DELIVERING = 4
const val ORDER_COMPLETED = 5

data class Order(
    val orderId: Int,
    val items: List<FoodItem>,

    val subtotal: Int,
    val deliveryFee: Int,
    val total: Int,

    val orderDiscount: Int = 0,
    val shippingDiscount: Int = 0,

    val orderVoucherCode: String = "",
    val shippingVoucherCode: String = "",

    val createdAt: Long =
        System.currentTimeMillis(),

    var status: Int = ORDER_PLACED,
    var rating: Int = 0
)

fun orderStatusText(status: Int): String {
    return when (status) {
        ORDER_CANCELLED -> "Đã hủy"
        ORDER_PLACED -> "Đã đặt hàng"
        ORDER_CONFIRMED -> "Nhà hàng đã xác nhận"
        ORDER_PREPARING -> "Đang chuẩn bị món"
        ORDER_DELIVERING -> "Đang giao hàng"
        ORDER_COMPLETED -> "Đã giao thành công"
        else -> "Không xác định"
    }
}