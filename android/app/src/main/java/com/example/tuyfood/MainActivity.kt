package com.example.tuyfood

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)

        setContentView(
            R.layout.activity_main
        )

        // Khởi tạo và đọc lịch sử đơn hàng
        OrderManager.initialize(
            applicationContext
        )

        val preferences =
            getSharedPreferences(
                "TuyFoods",
                MODE_PRIVATE
            )

        val userId =
            preferences.getLong(
                "userId",
                -1L
            )


        if (savedInstanceState == null) {

            if (isLoggedIn()) {
                openFragment(
                    HomeFragment()
                )
            } else {
                openFragment(
                    LoginFragment()
                )
            }
        }

        val bottomNavigation =
            findViewById<BottomNavigationView>(
                R.id.bottomNav
            )

        bottomNavigation
            .setOnItemSelectedListener { menuItem ->

                when (menuItem.itemId) {

                    R.id.nav_home -> {
                        openFragment(
                            HomeFragment()
                        )
                        true
                    }

                    R.id.nav_menu -> {
                        openFragment(
                            MenuFragment()
                        )
                        true
                    }

                    R.id.nav_orders -> {

                        val role = preferences.getString(
                            "role",
                            "CUSTOMER"
                        )

                        if (role == "ADMIN") {

                            openFragment(
                                AdminOrderFragment()
                            )

                        } else {

                            openFragment(
                                OrderHistoryFragment()
                            )
                        }

                        true
                    }

                    R.id.nav_minigame -> {
                        openFragment(
                            MysteryBoxFragment()
                        )
                        true
                    }

                    R.id.nav_account -> {

                        if (isLoggedIn()) {

                            val role = preferences.getString(
                                "role",
                                "CUSTOMER"
                            )

                            if (role == "ADMIN") {

                                openFragment(
                                    AdminProfileFragment()
                                )

                            } else {

                                openFragment(
                                    ProfileFragment()
                                )
                            }

                        } else {

                            openFragment(
                                LoginFragment()
                            )
                        }

                        true
                    }

                    else -> false
                }
            }
    }

    private fun isLoggedIn(): Boolean {

        val preferences =
            getSharedPreferences(
                "TuyFoods",
                Context.MODE_PRIVATE
            )

        return preferences.getLong(
            "userId",
            -1L
        ) != -1L
    }

    private fun openFragment(
        fragment: Fragment
    ) {
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.fragmentContainer,
                fragment
            )
            .commit()
    }
}