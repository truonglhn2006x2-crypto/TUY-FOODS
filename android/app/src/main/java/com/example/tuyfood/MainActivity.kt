package com.example.tuyfood

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

            if (userId != -1L) {
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
                        /*
                         * Mở danh sách lịch sử
                         * thay vì chỉ mở đơn mới nhất.
                         */
                        openFragment(
                            OrderHistoryFragment()
                        )
                        true
                    }

                    R.id.nav_account -> {
                        openFragment(
                            LoginFragment()
                        )
                        true
                    }

                    R.id.nav_minigame -> {
                        // Đang phát triển
                        true
                    }

                    else -> false
                }
            }
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