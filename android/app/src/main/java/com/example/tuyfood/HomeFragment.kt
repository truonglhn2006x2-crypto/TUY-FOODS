package com.example.tuyfood

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {

    // Danh sách món ăn nổi bật hiển thị ở trang chủ

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

        // SEARCH BAR -> mở Menu
        view.findViewById<View>(R.id.searchBar).setOnClickListener {
            openMenu(null)
        }

        // CART ICON -> mở giỏ hàng
        view.findViewById<View>(R.id.btnCartIcon).setOnClickListener {
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, CartFragment())
                .addToBackStack(null)
                .commit()
        }

        // AI CHAT
        view.findViewById<View>(R.id.fabChat).setOnClickListener {
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, ChatFragment())
                .addToBackStack(null)
                .commit()
        }

        // DANH MỤC
        view.findViewById<View>(R.id.btnGrill).setOnClickListener {
            openMenu("Món nướng & Xiên que")
        }

        view.findViewById<View>(R.id.btnFried).setOnClickListener {
            openMenu("Món chiên & Rán giòn")
        }

        view.findViewById<View>(R.id.btnSpicy).setOnClickListener {
            openMenu("Món trộn & Chua cay giải ngấy")
        }

        view.findViewById<View>(R.id.btnStarch).setOnClickListener {
            openMenu("Món no nhẹ & Tinh bột")
        }

        view.findViewById<View>(R.id.btnSmoothie).setOnClickListener {
            openMenu("Đồ uống - Sinh tố & Nước mát")
        }

        view.findViewById<View>(R.id.btnTea).setOnClickListener {
            openMenu("Đồ uống - Trà & Đá xay")
        }
        view.findViewById<View>(R.id.btnSeeAllCategory).setOnClickListener {
            openMenu(null)
        }

        // MÓN PHỔ BIẾN
        val recyclerPopular = view.findViewById<RecyclerView>(R.id.recyclerPopular)
        recyclerPopular.layoutManager = LinearLayoutManager(requireContext())

        loadPopularFoods(recyclerPopular)

        return view
    }

    private fun loadPopularFoods(recyclerPopular: RecyclerView) {

        viewLifecycleOwner.lifecycleScope.launch {

            try {
                val products = withContext(Dispatchers.IO) {
                    RetrofitClient.instance.getAllProducts()
                }

                val topFoods = FoodData.getTopSelling(5)

                val foods = topFoods.map { localFood ->

                    val product = products.find {
                        it.name == localFood.name
                    }

                    localFood.copy(
                        available = product?.available ?: true
                    )
                }

                recyclerPopular.adapter =
                    PopularFoodAdapter(foods) { food ->
                        onAddToCart(food)
                    }

            } catch (e: Exception) {

                Toast.makeText(
                    requireContext(),
                    "Không thể tải trạng thái món ăn",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun openMenu(category: String?) {
        val menuFragment = MenuFragment()

        if (category != null) {
            val bundle = Bundle()
            bundle.putString("category", category)
            menuFragment.arguments = bundle
        }

        parentFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, menuFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun openCustomFood() {
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, CustomFoodFragment())
            .addToBackStack(null)
            .commit()
    }

    private fun openOrderTracking() {
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, OrderTrackingFragment())
            .addToBackStack(null)
            .commit()
    }

    private fun onAddToCart(food: FoodItem) {
        CartManager.addItem(food)

        Toast.makeText(
            requireContext(),
            "Đã thêm ${food.name} vào giỏ hàng",
            Toast.LENGTH_SHORT
        ).show()
    }
}