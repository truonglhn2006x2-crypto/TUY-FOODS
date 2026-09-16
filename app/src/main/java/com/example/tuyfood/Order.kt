package com.example.tuyfood

data class Order(
    val orderId: Int,
    val items: List<FoodItem>,
    val total: Int,
    var status: Int
)