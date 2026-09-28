package com.example.tuyfood

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class MenuFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(
            R.layout.fragment_menu,
            container,
            false
        )

        val category = arguments?.getString("category")

        val txtCategoryTitle =
            view.findViewById<TextView>(R.id.txtCategoryTitle)

        val recyclerMenu =
            view.findViewById<RecyclerView>(R.id.recyclerMenu)

        val btnAll =
            view.findViewById<View>(R.id.btnAll)

        val btnGrillCategory =
            view.findViewById<View>(R.id.btnGrillCategory)

        val btnFriedCategory =
            view.findViewById<View>(R.id.btnFriedCategory)

        val btnSpicyCategory =
            view.findViewById<View>(R.id.btnSpicyCategory)

        val btnStarchCategory =
            view.findViewById<View>(R.id.btnStarchCategory)

        val btnSmoothieCategory =
            view.findViewById<View>(R.id.btnSmoothieCategory)

        val btnTeaCategory =
            view.findViewById<View>(R.id.btnTeaCategory)

        recyclerMenu.layoutManager =
            LinearLayoutManager(requireContext())

        val adapter = FoodAdapter(emptyList()) { food ->
            CartManager.addItem(food)

            Toast.makeText(
                requireContext(),
                "Đã thêm ${food.name} vào giỏ hàng",
                Toast.LENGTH_SHORT
            ).show()
        }

        recyclerMenu.adapter = adapter

        // Danh sách món lấy từ API
        var foodsFromApi = emptyList<FoodItem>()

        // Lọc danh mục
        fun showCategory(selectedCategory: String?) {

            val filteredFoods = if (selectedCategory == null) {
                foodsFromApi
            } else {
                foodsFromApi.filter {
                    it.category == selectedCategory
                }
            }

            adapter.updateData(filteredFoods)

            txtCategoryTitle.text =
                selectedCategory ?: "Tất cả món ăn"
        }

        // Lấy món ăn từ API
        viewLifecycleOwner.lifecycleScope.launch {
            try {

                val products =
                    RetrofitClient.instance.getAllProducts()

                val foods = products.map { product ->

                    val localFood = FoodData.allFoods.find {
                        it.name == product.name
                    }

                    FoodItem(
                        id = product.id.toInt(),
                        name = product.name,
                        price = product.price.toInt(),
                        emoji = localFood?.emoji ?: "🍽️",
                        imageRes = localFood?.imageRes ?: 0,
                        description =
                            product.description
                                ?: localFood?.description
                                ?: "",
                        category =
                            localFood?.category
                                ?: product.category?.name
                                ?: "",
                        rating = product.rating ?: 0.0,
                        quantity = 1,
                        available = product.available

                    )
                }

                // Lưu danh sách API
                foodsFromApi = foods

                // Hiển thị dữ liệu
                adapter.updateData(foodsFromApi)

                // Nếu đi từ Home vào một danh mục
                showCategory(category)

            } catch (e: Exception) {

                Toast.makeText(
                    requireContext(),
                    "Không thể tải món ăn từ server",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        // TẤT CẢ
        btnAll.setOnClickListener {
            showCategory(null)
        }

        // MÓN NƯỚNG
        btnGrillCategory.setOnClickListener {
            showCategory("Món nướng & Xiên que")
        }

        // MÓN CHIÊN
        btnFriedCategory.setOnClickListener {
            showCategory("Món chiên & Rán giòn")
        }

        // MÓN TRỘN
        btnSpicyCategory.setOnClickListener {
            showCategory("Món trộn & Chua cay giải ngấy")
        }

        // MÓN NO NHẸ
        btnStarchCategory.setOnClickListener {
            showCategory("Món no nhẹ & Tinh bột")
        }

        // SINH TỐ
        btnSmoothieCategory.setOnClickListener {
            showCategory("Đồ uống - Sinh tố & Nước mát")
        }

        // TRÀ
        btnTeaCategory.setOnClickListener {
            showCategory("Đồ uống - Trà & Đá xay")
        }

        return view
    }
}