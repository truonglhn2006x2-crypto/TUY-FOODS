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

<<<<<<< HEAD
    // Lấy 5 món bán chạy nhất từ 36 món trong FoodData
    private val popularFoods = FoodData.getTopSelling(5)
=======
    // Danh sách món ăn nổi bật hiển thị ở trang chủ
    private val popularFoods = listOf(
        FoodItem(
            name = "Burger Bò Đặc Biệt",
            price = 89000,
            emoji = "🍔",
            id = 1,
            description = "Burger bò 150g juicy, phô mai cheddar tan chảy",
            category = "Burger",
            rating = 4.8,
            badge = "Bán chạy"
        ),
        FoodItem(
            name = "Gà Rán Giòn (2 miếng)",
            price = 65000,
            emoji = "🍗",
            id = 3,
            description = "Gà rán vàng giòn lớp vỏ nhiều tầng",
            category = "Gà",
            rating = 4.7,
            badge = "HOT"
        ),
        FoodItem(
            name = "Pizza Phô Mai 4 Loại",
            price = 149000,
            emoji = "🍕",
            id = 2,
            description = "Sự kết hợp của mozzarella, cheddar, parmesan",
            category = "Pizza",
            rating = 4.4,
            badge = ""
        ),
        FoodItem(
            name = "Gà Popcorn Cay",
            price = 49000,
            emoji = "🍗",
            id = 5,
            description = "Gà miếng nhỏ chiên giòn tẩm bột gia vị cay",
            category = "Gà",
            rating = 4.8,
            badge = "HOT"
        ),
        FoodItem(
            name = "Trà Đào Cam Sả",
            price = 42000,
            emoji = "🥤",
            id = 4,
            description = "Trà đào thơm ngọt kết hợp cam tươi và sả",
            category = "Đồ uống",
            rating = 4.9,
            badge = ""
        )
    )
>>>>>>> d31ed13d44c2f34ceb4e4c95166b592a82ce5f7c

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

<<<<<<< HEAD
        // =========================
        // SEARCH
        // =========================

=======
        // SEARCH BAR -> mở Menu
>>>>>>> d31ed13d44c2f34ceb4e4c95166b592a82ce5f7c
        view.findViewById<View>(R.id.searchBar).setOnClickListener {
            openMenu(null)
        }

<<<<<<< HEAD

        // =========================
        // CART
        // =========================

=======
        // CART ICON -> mở giỏ hàng
>>>>>>> d31ed13d44c2f34ceb4e4c95166b592a82ce5f7c
        view.findViewById<View>(R.id.btnCartIcon).setOnClickListener {
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, CartFragment())
                .addToBackStack(null)
                .commit()
        }

<<<<<<< HEAD

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
=======
        // DANH MỤC
        view.findViewById<View>(R.id.btnBurger).setOnClickListener {
            openMenu("Burger")
        }

        view.findViewById<View>(R.id.btnPizza).setOnClickListener {
            openMenu("Pizza")
        }

        view.findViewById<View>(R.id.btnChicken).setOnClickListener {
            openMenu("Gà")
        }

        view.findViewById<View>(R.id.btnDrink).setOnClickListener {
            openMenu("Đồ uống")
        }

        view.findViewById<View>(R.id.btnCustomFood).setOnClickListener {
            openCustomFood()
        }

        view.findViewById<View>(R.id.btnOrderTracking).setOnClickListener {
            openOrderTracking()
        }

>>>>>>> d31ed13d44c2f34ceb4e4c95166b592a82ce5f7c
        view.findViewById<View>(R.id.btnSeeAllCategory).setOnClickListener {
            openMenu(null)
        }

<<<<<<< HEAD

        // =========================
        // XEM TẤT CẢ MÓN
        // =========================

=======
>>>>>>> d31ed13d44c2f34ceb4e4c95166b592a82ce5f7c
        view.findViewById<View>(R.id.btnMenu).setOnClickListener {
            openMenu(null)
        }

<<<<<<< HEAD

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
=======
        view.findViewById<View>(R.id.fabChat).setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, ChatFragment())
>>>>>>> d31ed13d44c2f34ceb4e4c95166b592a82ce5f7c
                .addToBackStack(null)
                .commit()
        }

<<<<<<< HEAD

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

=======
        // MÓN PHỔ BIẾN
        val recyclerPopular = view.findViewById<RecyclerView>(R.id.recyclerPopular)
        recyclerPopular.layoutManager = LinearLayoutManager(requireContext())
        recyclerPopular.adapter = PopularFoodAdapter(popularFoods) { food ->
            onAddToCart(food)
        }
>>>>>>> d31ed13d44c2f34ceb4e4c95166b592a82ce5f7c

        return view
    }

<<<<<<< HEAD

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

=======
    private fun openMenu(category: String?) {
        val menuFragment = MenuFragment()

        if (category != null) {
            val bundle = Bundle()
            bundle.putString("category", category)
>>>>>>> d31ed13d44c2f34ceb4e4c95166b592a82ce5f7c
            menuFragment.arguments = bundle
        }

        parentFragmentManager
            .beginTransaction()
<<<<<<< HEAD
            .replace(
                R.id.fragmentContainer,
                menuFragment
            )
=======
            .replace(R.id.fragmentContainer, menuFragment)
>>>>>>> d31ed13d44c2f34ceb4e4c95166b592a82ce5f7c
            .addToBackStack(null)
            .commit()
    }

<<<<<<< HEAD

    // =========================
    // THÊM VÀO GIỎ HÀNG
    // =========================

    private fun onAddToCart(food: FoodItem) {

=======
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
>>>>>>> d31ed13d44c2f34ceb4e4c95166b592a82ce5f7c
        CartManager.addItem(food)

        Toast.makeText(
            requireContext(),
            "Đã thêm ${food.name} vào giỏ hàng",
            Toast.LENGTH_SHORT
        ).show()
    }
<<<<<<< HEAD
=======


>>>>>>> d31ed13d44c2f34ceb4e4c95166b592a82ce5f7c
}