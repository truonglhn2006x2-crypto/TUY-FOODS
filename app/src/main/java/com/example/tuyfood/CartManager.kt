package com.example.tuyfood

object CartManager {

    val items = mutableListOf<FoodItem>()

    fun addItem(item: FoodItem) {
        items.add(item)
    }

    fun clearCart() {
        items.clear()
    }

    fun getTotal(): Int {
        var total = 0

        for (item in items) {
            total += item.price
        }

        return total
    }
}