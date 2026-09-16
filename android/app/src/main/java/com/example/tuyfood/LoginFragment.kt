package com.example.tuyfood

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(
            R.layout.fragment_login,
            container,
            false
        )

        val edtContact = view.findViewById<EditText>(R.id.edtContact)
        val edtPassword = view.findViewById<EditText>(R.id.edtPassword)
        val btnLogin = view.findViewById<MaterialButton>(R.id.btnLogin)
        val btnGoToRegister = view.findViewById<View>(R.id.btnGoToRegister)

        btnLogin.setOnClickListener {
            val email = edtContact.text.toString().trim()
            val password = edtPassword.text.toString()

            if (email.isEmpty()) {
                edtContact.error = "Vui lòng nhập email"
                edtContact.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                edtPassword.error = "Vui lòng nhập mật khẩu"
                edtPassword.requestFocus()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                try {
                    val response = RetrofitClient.instance.login(
                        mapOf("email" to email, "password" to password)
                    )

                    val prefs = requireContext().getSharedPreferences("TuyFoods", 0)
                    prefs.edit()
                        .putLong("userId", response.userId)
                        .putString("role", response.role)
                        .apply()

                    Toast.makeText(requireContext(), "Đăng nhập thành công!", Toast.LENGTH_SHORT).show()

                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, HomeFragment())
                        .addToBackStack(null)
                        .commit()

                } catch (e: Exception) {
                    Toast.makeText(requireContext(), "Email hoặc mật khẩu không đúng!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        btnGoToRegister.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, RegisterFragment())
                .addToBackStack(null)
                .commit()
        }

        return view
    }
}