package com.example.tuyfood

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FoodOptionBottomSheet(
    private val food: FoodItem,
    private val onAddToCart: (FoodItem) -> Unit
) : BottomSheetDialogFragment() {

    // =========================
    // VARIABLES
    // =========================

    private var quantity = 1
    private var extraPrice = 0

    private lateinit var txtFoodName: TextView
    private lateinit var txtBasePrice: TextView
    private lateinit var txtQuantity: TextView
    private lateinit var txtTotal: TextView

    private lateinit var optionsContainer: ViewGroup

    // =========================
    // CREATE VIEW
    // =========================

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return inflater.inflate(
            R.layout.dialog_food_options,
            container,
            false
        )
    }

    // =========================
    // VIEW CREATED
    // =========================

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {

        super.onViewCreated(view, savedInstanceState)

        // -------------------------
        // FIND VIEW
        // -------------------------

        txtFoodName =
            view.findViewById(R.id.txtOptionFoodName)

        txtBasePrice =
            view.findViewById(R.id.txtOptionBasePrice)

        txtQuantity =
            view.findViewById(R.id.txtOptionQuantity)

        txtTotal =
            view.findViewById(R.id.txtOptionTotal)

        optionsContainer =
            view.findViewById(R.id.optionsContainer)

        val btnMinus =
            view.findViewById<View>(R.id.btnOptionMinus)

        val btnPlus =
            view.findViewById<View>(R.id.btnOptionPlus)

        val btnAdd =
            view.findViewById<View>(R.id.btnAddOptionToCart)

        // -------------------------
        // FOOD INFORMATION
        // -------------------------

        txtFoodName.text = food.name

        txtBasePrice.text =
            formatPrice(food.price)

        txtQuantity.text = "1"

        // -------------------------
        // CREATE OPTIONS
        // -------------------------

        createOptions()

        // -------------------------
        // MINUS
        // -------------------------

        btnMinus.setOnClickListener {

            if (quantity > 1) {

                quantity--

                txtQuantity.text =
                    quantity.toString()

                updateTotal()
            }
        }

        // -------------------------
        // PLUS
        // -------------------------

        btnPlus.setOnClickListener {

            quantity++

            txtQuantity.text =
                quantity.toString()

            updateTotal()
        }

        // -------------------------
        // ADD TO CART
        // -------------------------

        btnAdd.setOnClickListener {

            val finalPrice =
                food.price + extraPrice

            /*
             * Tạo một FoodItem mới.
             *
             * Giá ở đây đã bao gồm topping.
             */
            val itemToCart =
                food.copy(
                    price = finalPrice,
                    quantity = quantity
                )

            // Gửi về nơi mở BottomSheet
            onAddToCart(itemToCart)

            Toast.makeText(
                requireContext(),
                "${food.name} đã được thêm vào giỏ",
                Toast.LENGTH_SHORT
            ).show()

            dismiss()
        }

        // -------------------------
        // INITIAL TOTAL
        // -------------------------

        updateTotal()
    }

    // =====================================================
    // CREATE FOOD OPTIONS
    // =====================================================

    private fun createOptions() {

        val options =
            FoodData.getOptions(food)

        // Nếu món không có option
        if (options.isEmpty()) {
            return
        }

        /*
         * Mỗi type sẽ có một RadioGroup riêng.
         *
         * spicy = mức cay
         * sauce = loại sốt
         * tea   = loại trà
         */

        val radioGroups =
            mutableMapOf<String, RadioGroup>()

        for (option in options) {

            when (option.type) {

                // =================================================
                // RADIO OPTION
                // =================================================

                "spicy",
                "sauce",
                "tea" -> {

                    // Nếu group chưa tồn tại thì tạo mới
                    val group =
                        radioGroups.getOrPut(option.type) {

                            val radioGroup =
                                RadioGroup(requireContext())

                            radioGroup.orientation =
                                RadioGroup.VERTICAL

                            optionsContainer.addView(
                                radioGroup
                            )

                            radioGroup
                        }

                    // -------------------------
                    // RADIO BUTTON
                    // -------------------------

                    val radioButton =
                        RadioButton(requireContext())

                    radioButton.tag =
                        option

                    radioButton.text =
                        if (option.price > 0) {

                            "${option.name}  +${formatPrice(option.price)}"

                        } else {

                            option.name
                        }

                    radioButton.textSize = 14f

                    radioButton.setPadding(
                        0,
                        8,
                        0,
                        8
                    )

                    // -------------------------
                    // CLICK
                    // -------------------------

                    radioButton.setOnClickListener {

                        updateExtraPrice()
                    }

                    group.addView(
                        radioButton
                    )
                }

                // =================================================
                // CHECKBOX TOPPING
                // =================================================

                else -> {

                    val checkBox =
                        CheckBox(requireContext())

                    checkBox.tag =
                        option

                    checkBox.text =
                        if (option.price > 0) {

                            "${option.name}  +${formatPrice(option.price)}"

                        } else {

                            option.name
                        }

                    checkBox.textSize = 14f

                    checkBox.setPadding(
                        0,
                        8,
                        0,
                        8
                    )

                    // -------------------------
                    // CHECK / UNCHECK
                    // -------------------------

                    checkBox.setOnCheckedChangeListener {
                            _,
                            _ ->

                        updateExtraPrice()
                    }

                    optionsContainer.addView(
                        checkBox
                    )
                }
            }
        }
    }

    // =====================================================
    // CALCULATE EXTRA PRICE
    // =====================================================

    private fun updateExtraPrice() {

        extraPrice = 0

        // Duyệt toàn bộ option
        for (i in 0 until optionsContainer.childCount) {

            val child =
                optionsContainer.getChildAt(i)

            when (child) {

                // =================================================
                // CHECKBOX
                // =================================================

                is CheckBox -> {

                    if (child.isChecked) {

                        val option =
                            child.tag as? FoodOption

                        if (option != null) {

                            extraPrice +=
                                option.price
                        }
                    }
                }

                // =================================================
                // RADIO GROUP
                // =================================================

                is RadioGroup -> {

                    val selectedId =
                        child.checkedRadioButtonId

                    if (selectedId != -1) {

                        val selected =
                            child.findViewById<RadioButton>(
                                selectedId
                            )

                        val option =
                            selected.tag as? FoodOption

                        if (option != null) {

                            extraPrice +=
                                option.price
                        }
                    }
                }
            }
        }

        updateTotal()
    }

    // =====================================================
    // UPDATE TOTAL
    // =====================================================

    private fun updateTotal() {

        val total =
            (food.price + extraPrice) * quantity

        txtTotal.text =
            formatPrice(total)
    }

    // =====================================================
    // FORMAT PRICE
    // =====================================================

    private fun formatPrice(
        price: Int
    ): String {

        return "%,dđ".format(price)
    }
}