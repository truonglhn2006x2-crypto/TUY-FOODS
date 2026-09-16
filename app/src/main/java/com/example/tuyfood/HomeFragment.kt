package com.example.tuyfood

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeFragment : Fragment() {

    // Lấy 5 món bán chạy nhất từ 36 món trong FoodData
    private val popularFoods = FoodData.getTopSelling(5)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(
            R.layout.fragment_home,
            container,
            false
        )

        // =========================
        // SEARCH
        // =========================

        view.findViewById<View>(R.id.searchBar).setOnClickListener {
            openMenu(null)
        }


        // =========================
        // CART
        // =========================

        view.findViewById<View>(R.id.btnCartIcon).setOnClickListener {
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, CartFragment())
                .addToBackStack(null)
                .commit()
        }


        // =========================
        // DANH MỤC
        // =========================

        // Món nướng & Xiên que
        view.findViewById<View>(R.id.btnGrill).setOnClickListener {
            openMenu(FoodData.CAT_NUONG)
        }

        // Món chiên & Rán giòn
        view.findViewById<View>(R.id.btnFried).setOnClickListener {
            openMenu(FoodData.CAT_CHIEN)
        }

        // Món trộn & Chua cay
        view.findViewById<View>(R.id.btnSpicy).setOnClickListener {
            openMenu(FoodData.CAT_TRON)
        }

        // Món no nhẹ & Tinh bột
        view.findViewById<View>(R.id.btnStarch).setOnClickListener {
            openMenu(FoodData.CAT_NO_NHE)
        }

        // Sinh tố & Nước mát
        view.findViewById<View>(R.id.btnSmoothie).setOnClickListener {
            openMenu(FoodData.CAT_SINH_TO)
        }

        // Trà & Đá xay
        view.findViewById<View>(R.id.btnTea).setOnClickListener {
            openMenu(FoodData.CAT_TRA)
        }

        // Xem tất cả danh mục
        view.findViewById<View>(R.id.btnSeeAllCategory).setOnClickListener {
            openMenu(null)
        }


        // =========================
        // XEM TẤT CẢ MÓN
        // =========================

        view.findViewById<View>(R.id.btnMenu).setOnClickListener {
            openMenu(null)
        }


        // =========================
        // CHAT
        // =========================

        view.findViewById<View>(R.id.fabChat).setOnClickListener {
            parentFragmentManager
                .beginTransaction()
                .replace(
                    R.id.fragmentContainer,
                    ChatFragment()
                )
                .addToBackStack(null)
                .commit()
        }


        // =========================
        // BẢNG XẾP HẠNG BÁN CHẠY
        // =========================

        val recyclerPopular =
            view.findViewById<RecyclerView>(R.id.recyclerPopular)

        recyclerPopular.layoutManager =
            LinearLayoutManager(requireContext())

        recyclerPopular.adapter =
            PopularFoodAdapter(popularFoods) { food ->
                onAddToCart(food)
            }


        return view
    }


    // =========================
    // MỞ MENU
    // =========================

    private fun openMenu(category: String?) {

        val menuFragment = MenuFragment()

        if (category != null) {

            val bundle = Bundle()

            bundle.putString(
                "category",
                category
            )

            menuFragment.arguments = bundle
        }

        parentFragmentManager
            .beginTransaction()
            .replace(
                R.id.fragmentContainer,
                menuFragment
            )
            .addToBackStack(null)
            .commit()
    }


    // =========================
    // THÊM VÀO GIỎ HÀNG
    // =========================

    private fun onAddToCart(food: FoodItem) {

        CartManager.addItem(food)

        Toast.makeText(
            requireContext(),
            "Đã thêm ${food.name} vào giỏ hàng",
            Toast.LENGTH_SHORT
        ).show()
    }
}