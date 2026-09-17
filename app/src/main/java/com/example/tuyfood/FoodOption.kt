package com.example.tuyfood

data class FoodOption(
    val name: String,
    val price: Int,
    val type: String = "topping"
)