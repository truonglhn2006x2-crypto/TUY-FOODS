package com.example.tuyfood

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MenuFragment : Fragment() {

    // Danh sách món ăn lấy từ nguồn dữ liệu chung FoodData (36 món, 6 danh mục)
    private val allFoods = FoodData.allFoods

    private lateinit var adapter: FoodAdapter
    private lateinit var txtCategoryTitle: TextView
    private lateinit var edtSearchMenu: EditText

    // Danh mục đang chọn (mặc định "Tất cả"), dùng khi ô tìm kiếm đang trống
    private var currentCategory: String = "Tất cả"

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

        txtCategoryTitle = view.findViewById(R.id.txtCategoryTitle)
        edtSearchMenu = view.findViewById(R.id.edtSearchMenu)

        val recyclerFood = view.findViewById<RecyclerView>(R.id.recyclerFood)
        recyclerFood.layoutManager = LinearLayoutManager(requireContext())

        adapter = FoodAdapter(allFoods) { food ->
            onAddToCart(food)
        }
        recyclerFood.adapter = adapter

        // Lấy category được truyền từ HomeFragment (nếu có)
        val category = arguments?.getString("category")
        if (category != null) {
            currentCategory = category
        }

        // Lấy từ khoá tìm kiếm được truyền từ HomeFragment (nếu có, ví dụ gõ ở trang chủ)
        val initialQuery = arguments?.getString("query")
        if (!initialQuery.isNullOrBlank()) {
            edtSearchMenu.setText(initialQuery)
        } else {
            filterByCategory(currentCategory)
        }

        // =========================
        // TÌM KIẾM THEO TÊN MÓN (gõ tới đâu lọc tới đó)
        // =========================
        edtSearchMenu.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val keyword = s?.toString().orEmpty()
                if (keyword.isBlank()) {
                    filterByCategory(currentCategory)
                } else {
                    filterBySearch(keyword)
                }
            }
        })

        // =========================
        // BẢNG XẾP HẠNG BÁN CHẠY
        // =========================
        view.findViewById<View>(R.id.btnRanking).setOnClickListener {
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, RankingFragment())
                .addToBackStack(null)
                .commit()
        }

        // =========================
        // CATEGORY BUTTONS
        // =========================

        view.findViewById<View>(R.id.btnAll).setOnClickListener {
            edtSearchMenu.text.clear()
            currentCategory = "Tất cả"
            filterByCategory(currentCategory)
        }

        view.findViewById<View>(R.id.btnCatNuong).setOnClickListener {
            edtSearchMenu.text.clear()
            currentCategory = FoodData.CAT_NUONG
            filterByCategory(currentCategory)
        }

        view.findViewById<View>(R.id.btnCatChien).setOnClickListener {
            edtSearchMenu.text.clear()
            currentCategory = FoodData.CAT_CHIEN
            filterByCategory(currentCategory)
        }

        view.findViewById<View>(R.id.btnCatTron).setOnClickListener {
            edtSearchMenu.text.clear()
            currentCategory = FoodData.CAT_TRON
            filterByCategory(currentCategory)
        }

        view.findViewById<View>(R.id.btnCatNoNhe).setOnClickListener {
            edtSearchMenu.text.clear()
            currentCategory = FoodData.CAT_NO_NHE
            filterByCategory(currentCategory)
        }

        view.findViewById<View>(R.id.btnCatSinhTo).setOnClickListener {
            edtSearchMenu.text.clear()
            currentCategory = FoodData.CAT_SINH_TO
            filterByCategory(currentCategory)
        }

        view.findViewById<View>(R.id.btnCatTra).setOnClickListener {
            edtSearchMenu.text.clear()
            currentCategory = FoodData.CAT_TRA
            filterByCategory(currentCategory)
        }

        return view
    }

    // =========================
    // FILTER CATEGORY
    // =========================

    private fun filterByCategory(category: String) {
        if (category == "Tất cả") {
            txtCategoryTitle.text = "Tất cả món ăn"
            adapter.updateData(allFoods)
        } else {
            txtCategoryTitle.text = category
            adapter.updateData(allFoods.filter { it.category == category })
        }
    }

    // =========================
    // FILTER SEARCH (theo tên món, không phân biệt hoa/thường)
    // =========================

    private fun filterBySearch(keyword: String) {
        val result = allFoods.filter { it.name.contains(keyword, ignoreCase = true) }
        txtCategoryTitle.text = "Kết quả cho \"$keyword\""
        adapter.updateData(result)
    }

    // =========================
    // ADD TO CART
    // =========================

    private fun onAddToCart(food: FoodItem) {
        CartManager.addItem(food)

        Toast.makeText(
            requireContext(),
            "Đã thêm ${food.name} vào giỏ hàng",
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
}