package com.example.tuyfood

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

object OrderManager {

    private const val PREFS_NAME = "tuy_food_order_history"
    private const val KEY_ORDERS = "orders"
    private val gson = Gson()
    private var preferences: SharedPreferences? = null
    private val orderList = mutableListOf<Order>()
    private var nextOrderId: Int = 1001

    var currentOrder: Order? = null
        private set

    fun initialize(context: Context) {
        if (preferences != null) return
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        loadOrders()
    }

    fun getOrders(): List<Order> {
        return orderList.sortedByDescending { it.createdAt }
    }

    fun findById(orderId: Int): Order? {
        return orderList.find { it.orderId == orderId }
    }

    fun createOrder(): Order? {
        if (CartManager.items.isEmpty()) return null

        val orderItems = CartManager.items.map { item -> item.copy() }
        val subtotal = CartManager.getTotal()
        val deliveryFee = 20000
        val finalTotal = CartManager.getFinalTotal()

        val order = Order(
            orderId = nextOrderId,
            items = orderItems,
            subtotal = subtotal.toInt(),
            deliveryFee = deliveryFee,
            total = finalTotal.toInt(),
            status = ORDER_PLACED
        )

        nextOrderId++
        orderList.add(0, order)
        currentOrder = order
        saveOrders()
        CartManager.clearCart()

        return order
    }

    fun updateStatus(status: Int) {
        val order = currentOrder ?: return
        updateStatus(orderId = order.orderId, status = status)
    }

    fun updateStatus(orderId: Int, status: Int): Boolean {
        val order = findById(orderId) ?: return false
        if (order.status == ORDER_CANCELLED) return false
        if (status !in ORDER_PLACED..ORDER_COMPLETED) return false
        order.status = status
        currentOrder = order
        saveOrders()
        return true
    }

    fun cancelOrder(orderId: Int): Boolean {
        val order = findById(orderId) ?: return false
        if (order.status != ORDER_PLACED) return false
        order.status = ORDER_CANCELLED
        saveOrders()
        return true
    }

    fun reorder(orderId: Int): Int {
        val order = findById(orderId) ?: return 0
        order.items.forEach { CartManager.addItem(it.copy()) }
        return order.items.size
    }

    fun rateOrder(orderId: Int, rating: Int): Boolean {
        val order = findById(orderId) ?: return false
        if (order.status != ORDER_COMPLETED) return false
        if (rating !in 1..5) return false
        order.rating = rating
        saveOrders()
        return true
    }

    private fun saveOrders() {
        val json = gson.toJson(orderList)
        preferences?.edit()?.putString(KEY_ORDERS, json)?.apply()
    }

    private fun loadOrders() {
        val json = preferences?.getString(KEY_ORDERS, null) ?: return
        try {
            val savedOrders = gson.fromJson(json, Array<Order>::class.java)
            orderList.clear()
            orderList.addAll(savedOrders.toList())
            nextOrderId = (orderList.maxOfOrNull { it.orderId } ?: 1000) + 1
            currentOrder = orderList.maxByOrNull { it.createdAt }
        } catch (_: Exception) {
            orderList.clear()
            currentOrder = null
            nextOrderId = 1001
        }
    }
}