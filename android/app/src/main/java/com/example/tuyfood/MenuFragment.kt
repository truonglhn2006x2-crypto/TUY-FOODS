package com.example.tuyfood

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

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
            )
        }


        // =========================
        // ADD BURGER
        // =========================

        val btnAddBurger =
            view.findViewById<View>(R.id.btnAddBurger)

        btnAddBurger.setOnClickListener {

            val burger = FoodItem(
                "Burger bò phô mai",
                59000,
                "🍔"
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
                "Pizza Hải Sản",
                129000,
                "🍕"
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
    }
}