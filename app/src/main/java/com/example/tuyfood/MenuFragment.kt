package com.example.tuyfood

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
<<<<<<< HEAD
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

=======
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class MenuFragment : Fragment() {

>>>>>>> d31ed13d44c2f34ceb4e4c95166b592a82ce5f7c
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

<<<<<<< HEAD
        return inflater.inflate(
=======
        val view = inflater.inflate(
>>>>>>> d31ed13d44c2f34ceb4e4c95166b592a82ce5f7c
            R.layout.fragment_menu,
            container,
            false
        )
<<<<<<< HEAD
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
=======

        // Lấy category được truyền từ HomeFragment
        val category = arguments?.getString("category")

        val txtCategoryTitle =
            view.findViewById<TextView>(R.id.txtCategoryTitle)

        val cardBurger =
            view.findViewById<View>(R.id.cardBurger)

        val cardPizza =
            view.findViewById<View>(R.id.cardPizza)

        val cardChicken =
            view.findViewById<View>(R.id.cardChicken)

        val cardDrink =
            view.findViewById<View>(R.id.cardDrink)

        // Hiển thị category ban đầu
        if (category != null) {
            txtCategoryTitle.text = category

            showOnlyCategory(
                category,
                cardBurger,
                cardPizza,
                cardChicken,
                cardDrink
            )
        }

        // =========================
        // CATEGORY BUTTONS
        // =========================

        val btnAll =
            view.findViewById<View>(R.id.btnAll)

        val btnBurgerCategory =
            view.findViewById<View>(R.id.btnBurgerCategory)

        val btnPizzaCategory =
            view.findViewById<View>(R.id.btnPizzaCategory)

        val btnChickenCategory =
            view.findViewById<View>(R.id.btnChickenCategory)

        val btnDrinkCategory =
            view.findViewById<View>(R.id.btnDrinkCategory)


        btnAll.setOnClickListener {
            txtCategoryTitle.text = "Tất cả món ăn"

            cardBurger.visibility = View.VISIBLE
            cardPizza.visibility = View.VISIBLE
            cardChicken.visibility = View.VISIBLE
            cardDrink.visibility = View.VISIBLE
        }

        btnBurgerCategory.setOnClickListener {
            txtCategoryTitle.text = "Burger"

            showOnlyCategory(
                "Burger",
                cardBurger,
                cardPizza,
                cardChicken,
                cardDrink
            )
        }

        btnPizzaCategory.setOnClickListener {
            txtCategoryTitle.text = "Pizza"

            showOnlyCategory(
                "Pizza",
                cardBurger,
                cardPizza,
                cardChicken,
                cardDrink
            )
        }

        btnChickenCategory.setOnClickListener {
            txtCategoryTitle.text = "Gà"

            showOnlyCategory(
                "Gà",
                cardBurger,
                cardPizza,
                cardChicken,
                cardDrink
            )
        }

        btnDrinkCategory.setOnClickListener {
            txtCategoryTitle.text = "Đồ uống"

            showOnlyCategory(
                "Đồ uống",
                cardBurger,
                cardPizza,
                cardChicken,
                cardDrink
>>>>>>> d31ed13d44c2f34ceb4e4c95166b592a82ce5f7c
            )
        }


<<<<<<< HEAD
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
=======
        // =========================
        // ADD BURGER
        // =========================

        val btnAddBurger =
            view.findViewById<View>(R.id.btnAddBurger)

        btnAddBurger.setOnClickListener {

            val burger = FoodItem(
                name = "Burger bò phô mai",
                price = 59000,
                emoji = "🍔"
            )

            CartManager.addItem(burger)

            Toast.makeText(
                requireContext(),
                "Đã thêm Burger vào giỏ hàng",
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


        // =========================
        // ADD PIZZA
        // =========================

        val btnAddPizza =
            view.findViewById<View>(R.id.btnAddPizza)

        btnAddPizza.setOnClickListener {

            val pizza = FoodItem(
                name ="Pizza Hải Sản",
                price =129000,
                emoji ="🍕"
            )

            CartManager.addItem(pizza)

            Toast.makeText(
                requireContext(),
                "Đã thêm Pizza vào giỏ hàng",
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

        return view
    }


    // =========================
    // FILTER CATEGORY
    // =========================

    private fun showOnlyCategory(
        category: String,
        burger: View,
        pizza: View,
        chicken: View,
        drink: View
    ) {

        burger.visibility = View.GONE
        pizza.visibility = View.GONE
        chicken.visibility = View.GONE
        drink.visibility = View.GONE

        when (category) {

            "Burger" -> {
                burger.visibility = View.VISIBLE
            }

            "Pizza" -> {
                pizza.visibility = View.VISIBLE
            }

            "Gà" -> {
                chicken.visibility = View.VISIBLE
            }

            "Đồ uống" -> {
                drink.visibility = View.VISIBLE
            }
        }
>>>>>>> d31ed13d44c2f34ceb4e4c95166b592a82ce5f7c
    }
}