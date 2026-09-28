package com.example.tuyfood

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class AdminProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(
            R.layout.fragment_admin_profile,
            container,
            false
        )

        val btnAdminLogout =
            view.findViewById<Button>(
                R.id.btnAdminLogout
            )

        btnAdminLogout.setOnClickListener {

            val preferences =
                requireContext().getSharedPreferences(
                    "TuyFoods",
                    0
                )

            preferences.edit()
                .clear()
                .apply()

            parentFragmentManager.beginTransaction()
                .replace(
                    R.id.fragmentContainer,
                    LoginFragment()
                )
                .commit()
        }

        return view
    }
}