package com.example.tuyfood

data class FoodItem(
    val id: Int = 0,
    val name: String,
    val price: Int,
    val emoji: String = "🍽️",
    val image: String? = null,
    val imageRes: Int = 0,
    val soldCount: Int = 0,
    val description: String = "",
    val category: String = "",
    val rating: Double = 0.0,
    val badge: String = "",
    var quantity: Int = 1,
    val available: Boolean = true
)
