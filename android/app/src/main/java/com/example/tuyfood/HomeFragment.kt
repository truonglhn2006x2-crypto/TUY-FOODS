package com.example.tuyfood

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment

class HomeFragment : Fragment() {

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
        // CUSTOM FOOD
        // =========================

        val btnCustomFood =
            view.findViewById<View>(R.id.btnCustomFood)

        btnCustomFood.setOnClickListener {
            openCustomFood()
        }


        // =========================
        // MENU
        // =========================

        val btnMenu =
            view.findViewById<View>(R.id.btnMenu)

        btnMenu.setOnClickListener {
            openMenu(null)
        }


        // =========================
        // CATEGORY
        // =========================

        val btnBurger =
            view.findViewById<View>(R.id.btnBurger)

        val btnPizza =
            view.findViewById<View>(R.id.btnPizza)

        val btnChicken =
            view.findViewById<View>(R.id.btnChicken)

        val btnDrink =
            view.findViewById<View>(R.id.btnDrink)


        btnBurger.setOnClickListener {

            Toast.makeText(
                requireContext(),
                "Đã bấm Burger",
                Toast.LENGTH_SHORT
            ).show()

            openMenu("Burger")
        }


        btnPizza.setOnClickListener {

            Toast.makeText(
                requireContext(),
                "Đã bấm Pizza",
                Toast.LENGTH_SHORT
            ).show()

            openMenu("Pizza")
        }


        btnChicken.setOnClickListener {

            Toast.makeText(
                requireContext(),
                "Đã bấm Gà",
                Toast.LENGTH_SHORT
            ).show()

            openMenu("Gà")
        }


        btnDrink.setOnClickListener {

            Toast.makeText(
                requireContext(),
                "Đã bấm Đồ uống",
                Toast.LENGTH_SHORT
            ).show()

            openMenu("Đồ uống")
        }


        // =========================
        // ORDER TRACKING
        // =========================

        val btnOrderTracking =
            view.findViewById<View>(R.id.btnOrderTracking)

        btnOrderTracking.setOnClickListener {
            openOrderTracking()
        }


        return view
    }


    // =========================
    // MỞ MENU
    // =========================

    private fun openMenu(category: String?) {

        val menuFragment =
            MenuFragment()

        if (category != null) {

            val bundle =
                Bundle()

            bundle.putString(
                "category",
                category
            )

            menuFragment.arguments =
                bundle
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
    // MỞ CUSTOM FOOD
    // =========================

    private fun openCustomFood() {

        parentFragmentManager
            .beginTransaction()
            .replace(
                R.id.fragmentContainer,
                CustomFoodFragment()
            )
            .addToBackStack(null)
            .commit()
    }


    // =========================
    // MỞ ORDER TRACKING
    // =========================

    private fun openOrderTracking() {

        parentFragmentManager
            .beginTransaction()
            .replace(
                R.id.fragmentContainer,
                OrderTrackingFragment()
            )
            .addToBackStack(null)
            .commit()
    }
}