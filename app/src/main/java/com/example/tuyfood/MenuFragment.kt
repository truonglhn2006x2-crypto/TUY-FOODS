package com.example.tuyfood

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MenuFragment : Fragment(R.layout.fragment_menu) {

    private lateinit var recyclerFood: RecyclerView
    private lateinit var foodAdapter: FoodAdapter

    private lateinit var btnAll: TextView
    private lateinit var btnGrill: TextView
    private lateinit var btnFried: TextView
    private lateinit var btnSpicy: TextView
    private lateinit var btnStarch: TextView
    private lateinit var btnSmoothie: TextView
    private lateinit var btnTea: TextView

    private lateinit var txtCategoryTitle: TextView

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        // =========================
        // FIND VIEW
        // =========================

        recyclerFood = view.findViewById(R.id.recyclerFood)

        btnAll = view.findViewById(R.id.btnAll)
        btnGrill = view.findViewById(R.id.btnGrill)
        btnFried = view.findViewById(R.id.btnFried)
        btnSpicy = view.findViewById(R.id.btnSpicy)
        btnStarch = view.findViewById(R.id.btnStarch)
        btnSmoothie = view.findViewById(R.id.btnSmoothie)
        btnTea = view.findViewById(R.id.btnTea)

        txtCategoryTitle = view.findViewById(R.id.txtCategoryTitle)

        // =========================
        // RECYCLER VIEW
        // =========================

        foodAdapter = FoodAdapter(
            emptyList()
        ) { food ->

            openFoodOptions(food)

        }

        recyclerFood.layoutManager =
            LinearLayoutManager(requireContext())

        recyclerFood.adapter = foodAdapter

        // =========================
        // CATEGORY BUTTONS
        // =========================

        btnAll.setOnClickListener {

            showAllFoods()

        }

        btnGrill.setOnClickListener {

            showCategory(
                FoodData.CAT_NUONG,
                "Món nướng & Xiên que"
            )

        }

        btnFried.setOnClickListener {

            showCategory(
                FoodData.CAT_CHIEN,
                "Món chiên & Rán giòn"
            )

        }

        btnSpicy.setOnClickListener {

            showCategory(
                FoodData.CAT_TRON,
                "Món trộn & Chua cay"
            )

        }

        btnStarch.setOnClickListener {

            showCategory(
                FoodData.CAT_NO_NHE,
                "Món no nhẹ & Tinh bột"
            )

        }

        btnSmoothie.setOnClickListener {

            showCategory(
                FoodData.CAT_SINH_TO,
                "Sinh tố & Nước mát"
            )

        }

        btnTea.setOnClickListener {

            showCategory(
                FoodData.CAT_TRA,
                "Trà & Đá xay"
            )

        }

        // =========================
        // DEFAULT
        // =========================

        showAllFoods()
    }

    // ==================================================
    // SHOW ALL FOOD
    // ==================================================

    private fun showAllFoods() {

        txtCategoryTitle.text = "Tất cả món ăn"

        foodAdapter.updateData(
            FoodData.allFoods
        )

        resetCategoryColor()

        btnAll.setTextColor(
            android.graphics.Color.parseColor("#E8192C")
        )
    }

    // ==================================================
    // SHOW FOOD BY CATEGORY
    // ==================================================

    private fun showCategory(
        category: String,
        title: String
    ) {

        txtCategoryTitle.text = title

        val foods = FoodData.allFoods.filter {

            it.category == category

        }

        foodAdapter.updateData(foods)

        resetCategoryColor()

        when (category) {

            FoodData.CAT_NUONG -> btnGrill.setTextColor(
                android.graphics.Color.parseColor("#E8192C")
            )

            FoodData.CAT_CHIEN -> btnFried.setTextColor(
                android.graphics.Color.parseColor("#E8192C")
            )

            FoodData.CAT_TRON -> btnSpicy.setTextColor(
                android.graphics.Color.parseColor("#E8192C")
            )

            FoodData.CAT_NO_NHE -> btnStarch.setTextColor(
                android.graphics.Color.parseColor("#E8192C")
            )

            FoodData.CAT_SINH_TO -> btnSmoothie.setTextColor(
                android.graphics.Color.parseColor("#E8192C")
            )

            FoodData.CAT_TRA -> btnTea.setTextColor(
                android.graphics.Color.parseColor("#E8192C")
            )
        }
    }

    // ==================================================
    // RESET CATEGORY COLOR
    // ==================================================

    private fun resetCategoryColor() {

        val normalColor =
            android.graphics.Color.parseColor("#333333")

        btnAll.setTextColor(normalColor)
        btnGrill.setTextColor(normalColor)
        btnFried.setTextColor(normalColor)
        btnSpicy.setTextColor(normalColor)
        btnStarch.setTextColor(normalColor)
        btnSmoothie.setTextColor(normalColor)
        btnTea.setTextColor(normalColor)
    }

    // ==================================================
    // OPEN FOOD OPTIONS
    // ==================================================

    private fun openFoodOptions(food: FoodItem) {

        val options = FoodData.getOptions(food)

        // ------------------------------------------
        // MÓN KHÔNG CÓ OPTION
        // ------------------------------------------

        if (options.isEmpty()) {

            addFoodDirectlyToCart(food)

            return
        }

        // ------------------------------------------
        // MÓN CÓ OPTION
        // ------------------------------------------

        val bottomSheet = FoodOptionBottomSheet(
            food = food
        ) { selectedFood ->

            addFoodDirectlyToCart(selectedFood)

        }

        bottomSheet.show(
            parentFragmentManager,
            "FoodOptionBottomSheet"
        )
    }

    // ==================================================
    // ADD TO CART
    // ==================================================

    private fun addFoodDirectlyToCart(food: FoodItem) {

        CartManager.addItem(food)

        Toast.makeText(
            requireContext(),
            "${food.name} đã được thêm vào giỏ",
            Toast.LENGTH_SHORT
        ).show()
    }
}