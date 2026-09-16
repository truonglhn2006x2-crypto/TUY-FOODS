package com.example.tuyfood

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MenuFragment : Fragment() {

    private lateinit var foodAdapter: FoodAdapter
    private lateinit var recyclerFood: RecyclerView
    private lateinit var txtCategoryTitle: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return inflater.inflate(
            R.layout.fragment_menu,
            container,
            false
        )
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        // ==============================
        // ÁNH XẠ VIEW
        // ==============================

        recyclerFood =
            view.findViewById(R.id.recyclerFood)

        txtCategoryTitle =
            view.findViewById(R.id.txtCategoryTitle)

        val btnAll =
            view.findViewById<Button>(R.id.btnAll)

        val btnGrill =
            view.findViewById<Button>(R.id.btnGrill)

        val btnFried =
            view.findViewById<Button>(R.id.btnFried)

        val btnSpicy =
            view.findViewById<Button>(R.id.btnSpicy)

        val btnStarch =
            view.findViewById<Button>(R.id.btnStarch)

        val btnSmoothie =
            view.findViewById<Button>(R.id.btnSmoothie)

        val btnTea =
            view.findViewById<Button>(R.id.btnTea)


        // ==============================
        // RECYCLERVIEW
        // ==============================

        recyclerFood.layoutManager =
            LinearLayoutManager(requireContext())

        foodAdapter = FoodAdapter(
            FoodData.allFoods
        ) { food ->

            addFoodToCart(food)
        }

        recyclerFood.adapter = foodAdapter


        // ==============================
        // TẤT CẢ MÓN
        // ==============================

        btnAll.setOnClickListener {

            showAllFoods()
        }


        // ==============================
        // MÓN NƯỚNG & XIÊN QUE
        // ==============================

        btnGrill.setOnClickListener {

            showCategory(
                FoodData.CAT_NUONG,
                "Món nướng & Xiên que"
            )
        }


        // ==============================
        // MÓN CHIÊN & RÁN
        // ==============================

        btnFried.setOnClickListener {

            showCategory(
                FoodData.CAT_CHIEN,
                "Món chiên & Rán giòn"
            )
        }


        // ==============================
        // MÓN TRỘN & CHUA CAY
        // ==============================

        btnSpicy.setOnClickListener {

            showCategory(
                FoodData.CAT_TRON,
                "Món trộn & Chua cay"
            )
        }


        // ==============================
        // MÓN NO NHẸ
        // ==============================

        btnStarch.setOnClickListener {

            showCategory(
                FoodData.CAT_NO_NHE,
                "Món no nhẹ & Tinh bột"
            )
        }


        // ==============================
        // SINH TỐ & NƯỚC MÁT
        // ==============================

        btnSmoothie.setOnClickListener {

            showCategory(
                FoodData.CAT_SINH_TO,
                "Sinh tố & Nước mát"
            )
        }


        // ==============================
        // TRÀ & ĐÁ XAY
        // ==============================

        btnTea.setOnClickListener {

            showCategory(
                FoodData.CAT_TRA,
                "Trà & Đá xay"
            )
        }


        // ==============================
        // NHẬN CATEGORY TỪ HOME
        // ==============================

        val category =
            arguments?.getString("category")

        if (!category.isNullOrEmpty()) {

            showCategory(
                category,
                category
            )

        } else {

            showAllFoods()
        }
    }


    // ==========================================
    // HIỂN THỊ TẤT CẢ MÓN
    // ==========================================

    private fun showAllFoods() {

        txtCategoryTitle.text =
            "Tất cả món ăn"

        foodAdapter.updateData(
            FoodData.allFoods
        )
    }


    // ==========================================
    // LỌC THEO DANH MỤC
    // ==========================================

    private fun showCategory(
        category: String,
        title: String
    ) {

        txtCategoryTitle.text =
            title

        val filteredFoods =
            FoodData.allFoods.filter {

                it.category == category
            }

        foodAdapter.updateData(
            filteredFoods
        )
    }


    // ==========================================
    // THÊM MÓN VÀO GIỎ HÀNG
    // ==========================================

    private fun addFoodToCart(
        food: FoodItem
    ) {

        // Luôn thêm 1 món mỗi lần bấm +
        CartManager.addItem(
            food.copy(
                quantity = 1
            )
        )

        Toast.makeText(
            requireContext(),
            "${food.name} đã được thêm vào giỏ hàng",
            Toast.LENGTH_SHORT
        ).show()
    }
}