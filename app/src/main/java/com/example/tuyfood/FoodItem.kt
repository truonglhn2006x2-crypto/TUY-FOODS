package com.example.tuyfood

data class FoodItem(
    val id: Int = 0,
    val name: String,
    val price: Int,
<<<<<<< HEAD
    val emoji: String = "",
    val imageRes: Int = 0,
    val description: String = "",
    val category: String = "",
    val rating: Double = 0.0,
    val soldCount: Int = 0,
=======
    val emoji: String,
    val description: String = "",
    val category: String = "",
    val rating: Double = 0.0,
>>>>>>> d31ed13d44c2f34ceb4e4c95166b592a82ce5f7c
    val badge: String = "",
    var quantity: Int = 1
)