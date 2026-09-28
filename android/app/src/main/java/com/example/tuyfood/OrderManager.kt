package com.example.tuyfood

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import java.time.LocalDateTime
import java.time.ZoneId

object OrderManager {

    private const val PREFS_NAME = "tuy_food_order_history"
    private const val KEY_ORDERS = "orders"

    private val gson = Gson()
    private var preferences: SharedPreferences? = null
    private val orderList = mutableListOf<Order>()

    var currentOrder: Order? = null
        private set

    fun initialize(context: Context) {

        if (preferences != null) return

        preferences =
            context.getSharedPreferences(
                PREFS_NAME,
                Context.MODE_PRIVATE
            )

        loadOrders()
    }

    fun getOrders(): List<Order> {

        return orderList
            .sortedByDescending {
                it.createdAt
            }
    }

    fun findById(orderId: Int): Order? {

        return orderList.find {
            it.orderId == orderId
        }
    }

    fun replaceOrdersFromServer(
        serverOrders: List<OrderApiResponse>
    ) {

        orderList.clear()

        serverOrders.forEach { serverOrder ->

            val items =
                serverOrder.items.map { item ->

                    // Lấy thông tin ảnh từ FoodData local
                    // giống cách MenuFragment đang làm
                    val localFood =
                        FoodData.allFoods.find {
                            it.name == item.productName
                        }

                    FoodItem(
                        id = item.product.id.toInt(),

                        name = item.productName,

                        price = item.price.toInt(),

                        emoji =
                            localFood?.emoji
                                ?: "🍽️",

                        imageRes =
                            localFood?.imageRes
                                ?: 0,

                        description =
                            localFood?.description
                                ?: "",

                        category =
                            localFood?.category
                                ?: "",

                        rating =
                            localFood?.rating
                                ?: 0.0,

                        quantity =
                            item.quantity
                    )
                }

            val subtotal =
                items.sumOf {
                    it.price * it.quantity
                }

            val createdAt =
                try {

                    LocalDateTime
                        .parse(serverOrder.createdAt)
                        .atZone(
                            ZoneId.systemDefault()
                        )
                        .toInstant()
                        .toEpochMilli()

                } catch (_: Exception) {

                    System.currentTimeMillis()
                }

            val status =
                when (serverOrder.status) {

                    "PENDING" ->
                        ORDER_PLACED

                    "CONFIRMED" ->
                        ORDER_CONFIRMED

                    "PREPARING" ->
                        ORDER_PREPARING

                    "DELIVERING" ->
                        ORDER_DELIVERING

                    "COMPLETED" ->
                        ORDER_COMPLETED

                    "CANCELLED" ->
                        ORDER_CANCELLED

                    else ->
                        ORDER_PLACED
                }

            val order =
                Order(
                    orderId =
                        serverOrder.id.toInt(),

                    items =
                        items,

                    subtotal =
                        subtotal,

                    deliveryFee =
                        0,

                    total =
                        serverOrder.totalAmount.toInt(),

                    orderDiscount =
                        serverOrder.discountAmount.toInt(),

                    status =
                        status,

                    createdAt =
                        createdAt
                )

            orderList.add(order)
        }

        orderList.sortByDescending {
            it.createdAt
        }

        currentOrder =
            orderList.firstOrNull()

        saveOrders()
    }

    fun createOrder(
        serverOrderId: Long
    ): Order? {

        if (CartManager.items.isEmpty()) {
            return null
        }

        val orderItems =
            CartManager.items.map {
                it.copy()
            }

        val subtotal =
            CartManager.getTotal()

        val deliveryFee =
            20_000

        val finalTotal =
            CartManager.getFinalTotal()

        val order =
            Order(

                orderId =
                    serverOrderId.toInt(),

                items =
                    orderItems,

                subtotal =
                    subtotal.toInt(),

                deliveryFee =
                    deliveryFee,

                total =
                    finalTotal.toInt(),

                status =
                    ORDER_PLACED
            )

        orderList.add(
            0,
            order
        )

        currentOrder =
            order

        saveOrders()

        CartManager.clearCart()

        return order
    }

    suspend fun updateStatus(
        status: Int
    ): Boolean {

        val order =
            currentOrder
                ?: return false

        return updateStatus(
            orderId =
                order.orderId,

            status =
                status
        )
    }

    suspend fun updateStatus(
        orderId: Int,
        status: Int
    ): Boolean {

        val order =
            findById(orderId)
                ?: return false

        if (
            order.status ==
            ORDER_CANCELLED
        ) {
            return false
        }

        if (
            status !in
            ORDER_PLACED..ORDER_COMPLETED
        ) {
            return false
        }

        return try {

            val serverStatus =
                when (status) {

                    ORDER_PLACED ->
                        "PENDING"

                    ORDER_CONFIRMED ->
                        "CONFIRMED"

                    ORDER_PREPARING ->
                        "PREPARING"

                    ORDER_DELIVERING ->
                        "DELIVERING"

                    ORDER_COMPLETED ->
                        "COMPLETED"

                    else ->
                        return false
                }

            RetrofitClient
                .instance
                .updateOrderStatus(

                    orderId =
                        orderId.toLong(),

                    status =
                        serverStatus
                )

            order.status =
                status

            currentOrder =
                order

            saveOrders()

            true

        } catch (_: Exception) {

            false
        }
    }

    suspend fun cancelOrder(
        orderId: Int
    ): Boolean {

        val order =
            findById(orderId)
                ?: return false

        if (
            order.status !=
            ORDER_PLACED
        ) {
            return false
        }

        return try {

            RetrofitClient
                .instance
                .updateOrderStatus(

                    orderId =
                        orderId.toLong(),

                    status =
                        "CANCELLED"
                )

            order.status =
                ORDER_CANCELLED

            saveOrders()

            true

        } catch (_: Exception) {

            false
        }
    }

    fun reorder(
        orderId: Int
    ): Int {

        val order =
            findById(orderId)
                ?: return 0

        order.items.forEach {

            CartManager.addItem(
                it.copy()
            )
        }

        return order.items.size
    }

    fun rateOrder(
        orderId: Int,
        rating: Int
    ): Boolean {

        val order =
            findById(orderId)
                ?: return false

        if (
            order.status !=
            ORDER_COMPLETED
        ) {
            return false
        }

        if (
            rating !in 1..5
        ) {
            return false
        }

        order.rating =
            rating

        saveOrders()

        return true
    }

    private fun saveOrders() {

        val json =
            gson.toJson(
                orderList
            )

        preferences
            ?.edit()
            ?.putString(
                KEY_ORDERS,
                json
            )
            ?.apply()
    }

    private fun loadOrders() {

        val json =
            preferences?.getString(
                KEY_ORDERS,
                null
            )
                ?: return

        try {

            val savedOrders =
                gson.fromJson(
                    json,
                    Array<Order>::class.java
                )

            orderList.clear()

            orderList.addAll(
                savedOrders.toList()
            )

            currentOrder =
                orderList.maxByOrNull {
                    it.createdAt
                }

        } catch (_: Exception) {

            orderList.clear()

            currentOrder =
                null
        }
    }
}