package com.example.tuyfood

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class CartFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(
            R.layout.fragment_cart,
            container,
            false
        )

        val cartContainer =
            view.findViewById<LinearLayout>(R.id.cartContainer)

        val txtItemCount =
            view.findViewById<TextView>(R.id.txtItemCount)

        val txtSubtotal =
            view.findViewById<TextView>(R.id.txtSubtotal)

        val txtDeliveryFee =
            view.findViewById<TextView>(R.id.txtDeliveryFee)

        val txtTotal =
            view.findViewById<TextView>(R.id.txtTotal)

        val btnCheckout =
            view.findViewById<Button>(R.id.btnCheckout)

        val items = CartManager.items

        // ==============================
        // GIỎ HÀNG TRỐNG
        // ==============================

        if (items.isEmpty()) {

            val emptyText = TextView(requireContext())

            emptyText.text =
                "🛒\n\nGiỏ hàng đang trống\n\nHãy chọn món ăn và thêm vào giỏ nhé!"

            emptyText.textSize = 17f
            emptyText.setTextColor(Color.GRAY)
            emptyText.gravity = android.view.Gravity.CENTER
            emptyText.setPadding(20, 80, 20, 80)

            cartContainer.addView(emptyText)

            txtItemCount.text = "0 món"
            txtSubtotal.text = "0đ"
            txtDeliveryFee.text = "0đ"
            txtTotal.text = "0đ"

            btnCheckout.isEnabled = false

            return view
        }

        // ==============================
        // HIỂN THỊ SỐ MÓN
        // ==============================

        txtItemCount.text = "${items.size} món"

        // ==============================
        // HIỂN THỊ CÁC MÓN
        // ==============================

        for (item in items) {

            val itemLayout = LinearLayout(requireContext())

            itemLayout.orientation =
                LinearLayout.HORIZONTAL

            itemLayout.gravity =
                android.view.Gravity.CENTER_VERTICAL

            itemLayout.setPadding(
                16,
                16,
                16,
                16
            )

            itemLayout.setBackgroundColor(
                Color.WHITE
            )

            val emoji = TextView(requireContext())

            emoji.text = item.emoji
            emoji.textSize = 32f

            val emojiParams =
                LinearLayout.LayoutParams(
                    55,
                    70
                )

            itemLayout.addView(
                emoji,
                emojiParams
            )

            val infoLayout =
                LinearLayout(requireContext())

            infoLayout.orientation =
                LinearLayout.VERTICAL

            val infoParams =
                LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1f
                )

            val name =
                TextView(requireContext())

            name.text = item.name
            name.textSize = 16f
            name.setTextColor(
                Color.rgb(35, 35, 35)
            )
            name.setTypeface(
                null,
                android.graphics.Typeface.BOLD
            )

            val price =
                TextView(requireContext())

            price.text =
                formatMoney(item.price)

            price.textSize = 14f
            price.setTextColor(
                Color.rgb(232, 25, 44)
            )

            infoLayout.addView(name)
            infoLayout.addView(price)

            itemLayout.addView(
                infoLayout,
                infoParams
            )

            cartContainer.addView(
                itemLayout
            )

            // Khoảng cách giữa các món
            val space =
                View(requireContext())

            cartContainer.addView(
                space,
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    8
                )
            )
        }

        // ==============================
        // TÍNH TIỀN
        // ==============================

        val subtotal =
            CartManager.getTotal()

        val deliveryFee =
            20000

        val total =
            subtotal + deliveryFee

        txtSubtotal.text =
            formatMoney(subtotal)

        txtDeliveryFee.text =
            formatMoney(deliveryFee)

        txtTotal.text =
            formatMoney(total)

        // ==============================
        // NÚT ĐẶT HÀNG
        // ==============================

        btnCheckout.setOnClickListener {

            val order =
                OrderManager.createOrder()

            if (order == null) {

                Toast.makeText(
                    requireContext(),
                    "Giỏ hàng đang trống!",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            Toast.makeText(
                requireContext(),
                "Đặt hàng thành công! Mã đơn #${order.orderId}",
                Toast.LENGTH_SHORT
            ).show()

            parentFragmentManager
                .beginTransaction()
                .replace(
                    R.id.fragmentContainer,
                    OrderTrackingFragment()
                )
                .addToBackStack(null)
                .commit()
        }

        return view
    }

    private fun formatMoney(
        money: Int
    ): String {

        return String.format(
            "%,dđ",
            money
        ).replace(",", ".")
    }
}