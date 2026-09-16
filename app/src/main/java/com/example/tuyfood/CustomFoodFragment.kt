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

        // =========================
        // ÁNH XẠ VIEW
        // =========================

        val rgFood =
            view.findViewById<RadioGroup>(R.id.rgFood)

        val rgSize =
            view.findViewById<RadioGroup>(R.id.rgSize)

        val rbBurger =
            view.findViewById<RadioButton>(R.id.rbBurger)

        val rbPizza =
            view.findViewById<RadioButton>(R.id.rbPizza)

        val rbSmall =
            view.findViewById<RadioButton>(R.id.rbSmall)

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


        // =========================
        // TÍNH TỔNG TIỀN
        // =========================

        fun calculateTotal() {

            var price = 0

            // Món
            if (rbBurger.isChecked) {
                price += 59000
            }

            if (rbPizza.isChecked) {
                price += 99000
            }

            // Size
            if (rbMedium.isChecked) {
                price += 10000
            }

            if (rbLarge.isChecked) {
                price += 20000
            }

            // Topping
            if (cbCheese.isChecked) {
                price += 10000
            }

            if (cbBacon.isChecked) {
                price += 15000
            }

            if (cbSauce.isChecked) {
                price += 5000
            }

            // Số lượng
            var quantity =
                edtQuantity.text.toString().toIntOrNull() ?: 1

            if (quantity < 1) {
                quantity = 1
            }

            val total = price * quantity

            txtTotal.text =
                "Tổng tiền: ${total}đ"
        }


        // =========================
        // CẬP NHẬT GIÁ KHI CHỌN
        // =========================

        rgFood.setOnCheckedChangeListener { _, _ ->
            calculateTotal()
        }

        rgSize.setOnCheckedChangeListener { _, _ ->
            calculateTotal()
        }

        cbCheese.setOnCheckedChangeListener { _, _ ->
            calculateTotal()
        }

        cbBacon.setOnCheckedChangeListener { _, _ ->
            calculateTotal()
        }

        cbSauce.setOnCheckedChangeListener { _, _ ->
            calculateTotal()
        }


        // =========================
        // CẬP NHẬT KHI ĐỔI SỐ LƯỢNG
        // =========================

        edtQuantity.addTextChangedListener(
            object : TextWatcher {

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    calculateTotal()
                }

                override fun afterTextChanged(
                    s: Editable?
                ) {
                }
            }
        )


        // =========================
        // THÊM MÓN CUSTOM
        // =========================

        btnAddCustom.setOnClickListener {

            // Kiểm tra món
            if (!rbBurger.isChecked && !rbPizza.isChecked) {

                Toast.makeText(
                    requireContext(),
                    "Vui lòng chọn món ăn",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }


            // Tên món
            val foodName: String

            var price: Int

            val emoji: String

            if (rbBurger.isChecked) {

                foodName = "Burger Custom"
                price = 59000
                emoji = "🍔"

            } else {

                foodName = "Pizza Custom"
                price = 99000
                emoji = "🍕"
            }


            // Chi tiết món
            var detail = foodName


            // Size
            if (rbMedium.isChecked) {

                price += 10000
                detail += " - Size M"

            } else if (rbLarge.isChecked) {

                price += 20000
                detail += " - Size L"

            } else {

                detail += " - Size S"
            }


            // Phô mai
            if (cbCheese.isChecked) {

                price += 10000
                detail += " + Phô mai"
            }


            // Bacon
            if (cbBacon.isChecked) {

                price += 15000
                detail += " + Bacon"
            }


            // Sốt
            if (cbSauce.isChecked) {

                price += 5000
                detail += " + Sốt"
            }


            // Số lượng
            val inputQuantity =
                edtQuantity.text.toString().toIntOrNull() ?: 1

            val quantity =
                if (inputQuantity < 1) 1 else inputQuantity


            // Tổng tiền
            val finalPrice =
                price * quantity


            // Tạo FoodItem
            val customFood = FoodItem(
                detail,
                finalPrice,
                emoji
            )


            // Thêm vào CartManager
            CartManager.addItem(customFood)


            // Thông báo
            Toast.makeText(
                requireContext(),
                "Đã thêm món custom vào giỏ hàng",
                Toast.LENGTH_SHORT
            ).show()


            // Chuyển sang giỏ hàng
            parentFragmentManager
                .beginTransaction()
                .replace(
                    R.id.fragmentContainer,
                    CartFragment()
                )
                .addToBackStack(null)
                .commit()
        }


        // =========================
        // TÍNH GIÁ BAN ĐẦU
        // =========================

        calculateTotal()


        return view
    }
}