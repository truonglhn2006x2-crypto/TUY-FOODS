package com.example.tuyfood

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton

class LoginFragment : Fragment() {

    private lateinit var databaseHelper: DatabaseHelper

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

        databaseHelper = DatabaseHelper(requireContext())

        // Lấy các thành phần từ XML
        val edtContact = view.findViewById<EditText>(R.id.edtContact)
        val edtPassword = view.findViewById<EditText>(R.id.edtPassword)

        val btnLogin = view.findViewById<MaterialButton>(R.id.btnLogin)
        val btnGoToRegister = view.findViewById<View>(R.id.btnGoToRegister)

        // ================= ĐĂNG NHẬP =================
        btnLogin.setOnClickListener {

            val email = edtContact.text.toString().trim()
            val password = edtPassword.text.toString()

            // Kiểm tra email
            if (email.isEmpty()) {
                edtContact.error = "Vui lòng nhập email"
                edtContact.requestFocus()
                return@setOnClickListener
            }

            // Kiểm tra mật khẩu
            if (password.isEmpty()) {
                edtPassword.error = "Vui lòng nhập mật khẩu"
                edtPassword.requestFocus()
                return@setOnClickListener
            }

            // Kiểm tra tài khoản trong SQLite
            val isValid = databaseHelper.checkUser(
                email,
                password
            )

            if (isValid) {

                // Đăng nhập thành công
                Toast.makeText(
                    requireContext(),
                    "Đăng nhập thành công!",
                    Toast.LENGTH_SHORT
                ).show()

                parentFragmentManager.beginTransaction()
                    .replace(
                        R.id.fragmentContainer,
                        HomeFragment()
                    )
                    .addToBackStack(null)
                    .commit()

            } else {

                // Sai email hoặc mật khẩu
                Toast.makeText(
                    requireContext(),
                    "Email hoặc mật khẩu không đúng!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        // ================= ĐI ĐẾN ĐĂNG KÝ =================
        btnGoToRegister.setOnClickListener {

            parentFragmentManager.beginTransaction()
                .replace(
                    R.id.fragmentContainer,
                    RegisterFragment()
                )
                .addToBackStack(null)
                .commit()
        }

        return view
    }
}