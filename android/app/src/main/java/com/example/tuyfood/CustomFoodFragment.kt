package com.example.tuyfood

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import java.util.Locale

class CustomFoodFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(
            R.layout.fragment_custom_food,
            container,
            false
        )

        // Ánh xạ View
        val rgFood =
            view.findViewById<RadioGroup>(R.id.rgFood)

        val rgSize =
            view.findViewById<RadioGroup>(R.id.rgSize)

        val rbBurger =
            view.findViewById<RadioButton>(R.id.rbBurger)

        val rbPizza =
            view.findViewById<RadioButton>(R.id.rbPizza)

        val rbMedium =
            view.findViewById<RadioButton>(R.id.rbMedium)

        val rbLarge =
            view.findViewById<RadioButton>(R.id.rbLarge)

        val cbCheese =
            view.findViewById<CheckBox>(R.id.cbCheese)

        val cbBacon =
            view.findViewById<CheckBox>(R.id.cbBacon)

        val cbSauce =
            view.findViewById<CheckBox>(R.id.cbSauce)

        val edtQuantity =
            view.findViewById<EditText>(R.id.edtQuantity)

        val txtTotal =
            view.findViewById<TextView>(R.id.txtTotal)

        val btnAddCustom =
            view.findViewById<Button>(R.id.btnAddCustom)

        // Tính giá của một món
        fun getUnitPrice(): Int {

            var price = when {
                rbBurger.isChecked -> 59_000
                rbPizza.isChecked -> 99_000
                else -> 0
            }

            // Chưa chọn món thì giá bằng 0
            if (price == 0) {
                return 0
            }

            // Giá theo kích thước
            if (rbMedium.isChecked) {
                price += 10_000
            } else if (rbLarge.isChecked) {
                price += 20_000
            }

            // Giá topping
            if (cbCheese.isChecked) {
                price += 10_000
            }

            if (cbBacon.isChecked) {
                price += 15_000
            }

            if (cbSauce.isChecked) {
                price += 5_000
            }

            return price
        }

        // Lấy số lượng hợp lệ
        fun getQuantity(): Int {
            return (
                    edtQuantity.text
                        .toString()
                        .toIntOrNull()
                        ?: 1
                    ).coerceAtLeast(1)
        }

        // Hiển thị tổng tiền
        fun calculateTotal() {

            val unitPrice =
                getUnitPrice()

            val quantity =
                getQuantity()

            val total =
                unitPrice * quantity

            txtTotal.text =
                "Tổng tiền: ${formatMoney(total)}"
        }

        // Cập nhật khi chọn món và kích thước
        rgFood.setOnCheckedChangeListener { _, _ ->
            calculateTotal()
        }

        rgSize.setOnCheckedChangeListener { _, _ ->
            calculateTotal()
        }

        // Cập nhật khi chọn topping
        cbCheese.setOnCheckedChangeListener { _, _ ->
            calculateTotal()
        }

        cbBacon.setOnCheckedChangeListener { _, _ ->
            calculateTotal()
        }

        cbSauce.setOnCheckedChangeListener { _, _ ->
            calculateTotal()
        }

        // Cập nhật khi nhập số lượng
        edtQuantity.addTextChangedListener(
            object : TextWatcher {

                override fun beforeTextChanged(
                    text: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(
                    text: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    calculateTotal()
                }

                override fun afterTextChanged(
                    text: Editable?
                ) {
                }
            }
        )

        // Thêm món custom vào giỏ
        btnAddCustom.setOnClickListener {

            if (
                !rbBurger.isChecked &&
                !rbPizza.isChecked
            ) {
                Toast.makeText(
                    requireContext(),
                    "Vui lòng chọn món ăn",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            val foodName: String
            val emoji: String

            if (rbBurger.isChecked) {
                foodName = "Burger Custom"
                emoji = "🍔"
            } else {
                foodName = "Pizza Custom"
                emoji = "🍕"
            }

            var detail = foodName

            // Chi tiết kích thước
            detail += when {
                rbMedium.isChecked -> " - Size M"
                rbLarge.isChecked -> " - Size L"
                else -> " - Size S"
            }

            // Chi tiết topping
            if (cbCheese.isChecked) {
                detail += " + Phô mai"
            }

            if (cbBacon.isChecked) {
                detail += " + Bacon"
            }

            if (cbSauce.isChecked) {
                detail += " + Sốt"
            }

            val unitPrice =
                getUnitPrice()

            val quantity =
                getQuantity()

            /*
             * Quan trọng:
             * price chỉ là giá của một món.
             * quantity được lưu riêng.
             */
            val customFood = FoodItem(
                name = detail,
                price = unitPrice,
                emoji = emoji,
                quantity = quantity
            )

            CartManager.addItem(customFood)

            Toast.makeText(
                requireContext(),
                "Đã thêm $quantity món vào giỏ hàng",
                Toast.LENGTH_SHORT
            ).show()

            parentFragmentManager
                .beginTransaction()
                .replace(
                    R.id.fragmentContainer,
                    CartFragment()
                )
                .addToBackStack(null)
                .commit()
        }

        calculateTotal()

        return view
    }

    private fun formatMoney(money: Int): String {
        return String.format(
            Locale.US,
            "%,dđ",
            money
        ).replace(",", ".")
    }
}