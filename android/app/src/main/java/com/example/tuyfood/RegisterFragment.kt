package com.example.tuyfood

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment

class RegisterFragment : Fragment() {

    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(
            R.layout.fragment_register,
            container,
            false
        )

        databaseHelper = DatabaseHelper(requireContext())

        val edtName =
            view.findViewById<EditText>(R.id.edtName)

        val edtEmail =
            view.findViewById<EditText>(R.id.edtEmail)

        val edtPassword =
            view.findViewById<EditText>(R.id.edtPassword)

        val edtConfirmPassword =
            view.findViewById<EditText>(
                R.id.edtConfirmPassword
            )

        val btnRegister =
            view.findViewById<Button>(
                R.id.btnRegister
            )

        val btnGoToLogin =
            view.findViewById<View>(
                R.id.btnGoToLogin
            )


        btnRegister.setOnClickListener {

            val name =
                edtName.text.toString().trim()

            val email =
                edtEmail.text.toString().trim()

            val password =
                edtPassword.text.toString()

            val confirmPassword =
                edtConfirmPassword.text.toString()


            // 1. Kiểm tra họ tên

            if (name.isEmpty()) {

                edtName.error =
                    "Vui lòng nhập họ tên"

                edtName.requestFocus()

                return@setOnClickListener
            }


            // 2. Kiểm tra email

            if (email.isEmpty()) {

                edtEmail.error =
                    "Vui lòng nhập email"

                edtEmail.requestFocus()

                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS
                    .matcher(email)
                    .matches()
            ) {

                edtEmail.error =
                    "Email không hợp lệ"

                edtEmail.requestFocus()

                return@setOnClickListener
            }


            // 3. Kiểm tra mật khẩu

            if (password.isEmpty()) {

                edtPassword.error =
                    "Vui lòng nhập mật khẩu"

                edtPassword.requestFocus()

                return@setOnClickListener
            }

            if (password.length < 6) {

                edtPassword.error =
                    "Mật khẩu phải có ít nhất 6 ký tự"

                edtPassword.requestFocus()

                return@setOnClickListener
            }


            // 4. Kiểm tra xác nhận mật khẩu

            if (confirmPassword.isEmpty()) {

                edtConfirmPassword.error =
                    "Vui lòng xác nhận mật khẩu"

                edtConfirmPassword.requestFocus()

                return@setOnClickListener
            }

            if (password != confirmPassword) {

                edtConfirmPassword.error =
                    "Mật khẩu không khớp"

                edtConfirmPassword.requestFocus()

                return@setOnClickListener
            }


            // 5. Lưu vào SQLite

            val success =
                databaseHelper.registerUser(
                    name,
                    email,
                    password
                )


            if (success) {

                Toast.makeText(
                    requireContext(),
                    "Đăng ký thành công!",
                    Toast.LENGTH_SHORT
                ).show()

                parentFragmentManager.popBackStack()

            } else {

                Toast.makeText(
                    requireContext(),
                    "Email đã được đăng ký!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


        // Quay về Login

        btnGoToLogin.setOnClickListener {

            parentFragmentManager.popBackStack()
        }


        return view
    }
}