package com.example.tuyfood

import java.util.Locale

data class VoucherResult(
    val success: Boolean,
    val message: String
)

object CartManager {

    val items = mutableListOf<FoodItem>()

    private const val DELIVERY_FEE = 20_000

    var appliedOrderVoucherCode: String? = null
        private set

    var appliedShippingVoucherCode: String? = null
        private set

    fun addItem(item: FoodItem) {

        val quantityToAdd =
            item.quantity.coerceAtLeast(1)

        val existingItem = items.find {
            isSameFood(it, item)
        }

        if (existingItem != null) {
            existingItem.quantity += quantityToAdd
        } else {
            items.add(
                item.copy(quantity = quantityToAdd)
            )
        }

        validateCurrentVouchers()
    }

    fun increaseQuantity(item: FoodItem) {
        val target = findItem(item) ?: return

        target.quantity++
        validateCurrentVouchers()
    }

    fun decreaseQuantity(item: FoodItem) {
        val target = findItem(item) ?: return

        if (target.quantity > 1) {
            target.quantity--
        }

        validateCurrentVouchers()
    }

    fun removeItem(item: FoodItem) {
        val target = findItem(item) ?: return

        items.remove(target)
        validateCurrentVouchers()
    }

    fun clearCart() {
        items.clear()

        appliedOrderVoucherCode = null
        appliedShippingVoucherCode = null
    }

    fun getItemCount(): Int {
        return items.sumOf {
            it.quantity
        }
    }

    fun getSubtotal(): Int {
        return items.sumOf {
            it.price * it.quantity
        }
    }

    /*
     * Giữ tương thích với code cũ.
     * getTotal() là tổng tiền món.
     */
    fun getTotal(): Int {
        return getSubtotal()
    }

    fun getDeliveryFee(): Int {
        return if (items.isEmpty()) {
            0
        } else {
            DELIVERY_FEE
        }
    }

    fun applyOrderVoucher(
        inputCode: String
    ): VoucherResult {

        val code = inputCode
            .trim()
            .uppercase(Locale.ROOT)

        if (items.isEmpty()) {
            return VoucherResult(
                false,
                "Giỏ hàng đang trống"
            )
        }

        return when (code) {

            "TUYFOOD10" -> {
                if (getSubtotal() >= 100_000) {
                    appliedOrderVoucherCode = code

                    VoucherResult(
                        true,
                        "Đã áp dụng giảm 10%"
                    )
                } else {
                    appliedOrderVoucherCode = null

                    VoucherResult(
                        false,
                        "Đơn hàng phải từ 100.000đ"
                    )
                }
            }

            "GIAM20K" -> {
                if (getSubtotal() >= 100_000) {
                    appliedOrderVoucherCode = code

                    VoucherResult(
                        true,
                        "Đã giảm 20.000đ"
                    )
                } else {
                    appliedOrderVoucherCode = null

                    VoucherResult(
                        false,
                        "Đơn hàng phải từ 100.000đ"
                    )
                }
            }

            "" -> {
                appliedOrderVoucherCode = null

                VoucherResult(
                    false,
                    "Vui lòng nhập voucher đơn hàng"
                )
            }

            else -> {
                appliedOrderVoucherCode = null

                VoucherResult(
                    false,
                    "Voucher đơn hàng không hợp lệ"
                )
            }
        }
    }

    fun applyShippingVoucher(
        inputCode: String
    ): VoucherResult {

        val code = inputCode
            .trim()
            .uppercase(Locale.ROOT)

        if (items.isEmpty()) {
            return VoucherResult(
                false,
                "Giỏ hàng đang trống"
            )
        }

        return when (code) {

            "FREESHIP" -> {
                if (getSubtotal() >= 80_000) {
                    appliedShippingVoucherCode = code

                    VoucherResult(
                        true,
                        "Đã miễn phí vận chuyển"
                    )
                } else {
                    appliedShippingVoucherCode = null

                    VoucherResult(
                        false,
                        "Đơn hàng phải từ 80.000đ"
                    )
                }
            }

            "" -> {
                appliedShippingVoucherCode = null

                VoucherResult(
                    false,
                    "Vui lòng nhập voucher vận chuyển"
                )
            }

            else -> {
                appliedShippingVoucherCode = null

                VoucherResult(
                    false,
                    "Voucher vận chuyển không hợp lệ"
                )
            }
        }
    }

    fun getOrderDiscount(): Int {

        validateCurrentVouchers()

        return when (appliedOrderVoucherCode) {

            "TUYFOOD10" -> {
                /*
                 * Giảm 10%, tối đa 50.000đ
                 */
                (getSubtotal() * 10 / 100)
                    .coerceAtMost(50_000)
            }

            "GIAM20K" -> {
                20_000.coerceAtMost(
                    getSubtotal()
                )
            }

            else -> 0
        }
    }

    fun getShippingDiscount(): Int {

        validateCurrentVouchers()

        return when (appliedShippingVoucherCode) {
            "FREESHIP" -> getDeliveryFee()
            else -> 0
        }
    }

    fun getFinalTotal(): Int {

        val finalTotal =
            getSubtotal() -
                    getOrderDiscount() +
                    getDeliveryFee() -
                    getShippingDiscount()

        return finalTotal.coerceAtLeast(0)
    }

    private fun validateCurrentVouchers() {

        val subtotal = getSubtotal()

        if (
            appliedOrderVoucherCode == "TUYFOOD10" &&
            subtotal < 100_000
        ) {
            appliedOrderVoucherCode = null
        }

        if (
            appliedOrderVoucherCode == "GIAM20K" &&
            subtotal < 100_000
        ) {
            appliedOrderVoucherCode = null
        }

        if (
            appliedShippingVoucherCode == "FREESHIP" &&
            subtotal < 80_000
        ) {
            appliedShippingVoucherCode = null
        }
    }

    private fun findItem(
        item: FoodItem
    ): FoodItem? {

        return items.find {
            it === item
        } ?: items.find {
            isSameFood(it, item)
        }
    }

    private fun isSameFood(
        first: FoodItem,
        second: FoodItem
    ): Boolean {

        /*
         * Nếu món có ID thì so sánh bằng ID.
         */
        if (first.id != 0 && second.id != 0) {
            return first.id == second.id
        }

        /*
         * Món custom không có ID nên so sánh
         * tên, giá và emoji.
         */
        return first.name == second.name &&
                first.price == second.price &&
                first.emoji == second.emoji
    }
}