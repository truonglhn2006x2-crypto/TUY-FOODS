package com.example.tuyfood

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val prefs = getSharedPreferences("TuyFoods", 0)
        val userId = prefs.getLong("userId", -1)

        if (savedInstanceState == null) {
            if (userId != -1L) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, HomeFragment())
                    .commit()
            } else {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, LoginFragment())
                    .commit()
            }
        }

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, HomeFragment())
                        .commit()
                    true
                }
                R.id.nav_menu -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, MenuFragment())
                        .commit()
                    true
                }
                R.id.nav_orders -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, OrderTrackingFragment())
                        .commit()
                    true
                }
                R.id.nav_account -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, LoginFragment())
                        .commit()
                    true
                }
                R.id.nav_minigame -> {
                    // Đang Phát triển
                    true
                }
                else -> false
            }
        }
    }
}