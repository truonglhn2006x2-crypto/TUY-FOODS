package com.example.tuyfood

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import java.util.Locale
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class CartFragment : Fragment() {

    private lateinit var cartContainer: LinearLayout
    private lateinit var txtItemCount: TextView
    private lateinit var txtSubtotal: TextView
    private lateinit var txtOrderDiscount: TextView
    private lateinit var txtDeliveryFee: TextView
    private lateinit var txtShippingDiscount: TextView
    private lateinit var txtTotal: TextView

    private lateinit var edtOrderVoucher: EditText
    private lateinit var edtShippingVoucher: EditText

    private lateinit var btnClearCart: Button
    private lateinit var btnApplyOrderVoucher: Button
    private lateinit var btnApplyShippingVoucher: Button
    private lateinit var btnCheckout: Button

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

        cartContainer =
            view.findViewById(R.id.cartContainer)

        txtItemCount =
            view.findViewById(R.id.txtItemCount)

        txtSubtotal =
            view.findViewById(R.id.txtSubtotal)

        txtOrderDiscount =
            view.findViewById(R.id.txtOrderDiscount)

        txtDeliveryFee =
            view.findViewById(R.id.txtDeliveryFee)

        txtShippingDiscount =
            view.findViewById(R.id.txtShippingDiscount)

        txtTotal =
            view.findViewById(R.id.txtTotal)

        edtOrderVoucher =
            view.findViewById(R.id.edtOrderVoucher)

        edtShippingVoucher =
            view.findViewById(R.id.edtShippingVoucher)

        btnClearCart =
            view.findViewById(R.id.btnClearCart)

        btnApplyOrderVoucher =
            view.findViewById(R.id.btnApplyOrderVoucher)

        btnApplyShippingVoucher =
            view.findViewById(R.id.btnApplyShippingVoucher)

        btnCheckout =
            view.findViewById(R.id.btnCheckout)

        btnApplyOrderVoucher.setOnClickListener {

            val result =
                CartManager.applyOrderVoucher(
                    edtOrderVoucher.text.toString()
                )

            Toast.makeText(
                requireContext(),
                result.message,
                Toast.LENGTH_SHORT
            ).show()

            renderCart()
        }

        btnApplyShippingVoucher.setOnClickListener {

            val result =
                CartManager.applyShippingVoucher(
                    edtShippingVoucher.text.toString()
                )

            Toast.makeText(
                requireContext(),
                result.message,
                Toast.LENGTH_SHORT
            ).show()

            renderCart()
        }

        btnClearCart.setOnClickListener {

            AlertDialog.Builder(requireContext())
                .setTitle("Xóa giỏ hàng")
                .setMessage(
                    "Bạn có chắc muốn xóa toàn bộ món?"
                )
                .setNegativeButton("Không", null)
                .setPositiveButton("Xóa") { _, _ ->

                    CartManager.clearCart()
                    renderCart()
                }
                .show()
        }

        btnCheckout.setOnClickListener {
            if (CartManager.items.isEmpty()) {
                Toast.makeText(requireContext(), "Giỏ hàng đang trống", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val prefs = requireContext().getSharedPreferences("TuyFoods", 0)
            val userId = prefs.getLong("userId", -1L)

            if (userId == -1L) {
                Toast.makeText(requireContext(), "Vui lòng đăng nhập!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                try {
                    val orderItems = CartManager.items.map {
                        OrderItemRequest(
                            productId = it.id.toLong(),
                            quantity = it.quantity
                        )
                    }

                    val response = RetrofitClient.instance.createOrder(
                        OrderRequest(
                            userId = userId,
                            deliveryAddress = "Địa chỉ mặc định",
                            phone = "",
                            items = orderItems
                        )
                    )

                    OrderManager.createOrder()

                    Toast.makeText(
                        requireContext(),
                        "Đặt hàng thành công! Mã đơn #${response.orderId}",
                        Toast.LENGTH_SHORT
                    ).show()

                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, OrderTrackingFragment())
                        .addToBackStack(null)
                        .commit()

                } catch (e: Exception) {
                    Toast.makeText(requireContext(), "Lỗi kết nối server!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        renderCart()

        return view
    }

    override fun onResume() {
        super.onResume()

        if (::cartContainer.isInitialized) {
            renderCart()
        }
    }

    private fun renderCart() {

        cartContainer.removeAllViews()

        val items =
            CartManager.items.toList()

        val isEmpty =
            items.isEmpty()

        txtItemCount.text =
            "${CartManager.getItemCount()} món"

        btnCheckout.isEnabled = !isEmpty
        btnClearCart.isEnabled = !isEmpty
        btnApplyOrderVoucher.isEnabled = !isEmpty
        btnApplyShippingVoucher.isEnabled = !isEmpty

        if (isEmpty) {

            val emptyText =
                TextView(requireContext()).apply {

                    text =
                        "🛒\n\nGiỏ hàng đang trống\n" +
                                "Hãy thêm món ăn vào giỏ!"

                    textSize = 17f
                    setTextColor(Color.GRAY)
                    gravity = Gravity.CENTER
                    setPadding(
                        dp(10),
                        dp(60),
                        dp(10),
                        dp(60)
                    )
                }

            cartContainer.addView(emptyText)
        } else {

            items.forEach { item ->
                cartContainer.addView(
                    createItemView(item)
                )
            }
        }

        val subtotal =
            CartManager.getSubtotal()

        val orderDiscount =
            CartManager.getOrderDiscount()

        val deliveryFee =
            CartManager.getDeliveryFee()

        val shippingDiscount =
            CartManager.getShippingDiscount()

        val finalTotal =
            CartManager.getFinalTotal()

        txtSubtotal.text =
            formatMoney(subtotal)

        txtDeliveryFee.text =
            formatMoney(deliveryFee)

        txtTotal.text =
            formatMoney(finalTotal)

        val orderCode =
            CartManager.appliedOrderVoucherCode

        txtOrderDiscount.text =
            if (orderCode == null) {
                "Giảm giá đơn hàng: -0đ"
            } else {
                "$orderCode: -${formatMoney(orderDiscount)}"
            }

        val shippingCode =
            CartManager.appliedShippingVoucherCode

        txtShippingDiscount.text =
            if (shippingCode == null) {
                "Giảm phí vận chuyển: -0đ"
            } else {
                "$shippingCode: -${formatMoney(shippingDiscount)}"
            }
    }

    private fun createItemView(
        item: FoodItem
    ): View {

        val itemLayout =
            LinearLayout(requireContext()).apply {

                orientation = LinearLayout.VERTICAL

                setPadding(
                    dp(14),
                    dp(14),
                    dp(14),
                    dp(14)
                )

                background =
                    GradientDrawable().apply {

                        setColor(Color.WHITE)
                        cornerRadius =
                            dp(12).toFloat()

                        setStroke(
                            dp(1),
                            Color.rgb(
                                225,
                                225,
                                225
                            )
                        )
                    }

                layoutParams =
                    LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    ).apply {
                        bottomMargin = dp(10)
                    }
            }

        val topRow =
            LinearLayout(requireContext()).apply {

                orientation =
                    LinearLayout.HORIZONTAL

                gravity =
                    Gravity.CENTER_VERTICAL
            }

        val emoji =
            TextView(requireContext()).apply {

                text = item.emoji
                textSize = 34f
                gravity = Gravity.CENTER
            }

        topRow.addView(
            emoji,
            LinearLayout.LayoutParams(
                dp(55),
                dp(60)
            )
        )

        val information =
            LinearLayout(requireContext()).apply {

                orientation =
                    LinearLayout.VERTICAL
            }

        val name =
            TextView(requireContext()).apply {

                text = item.name
                textSize = 16f
                setTextColor(Color.DKGRAY)
                setTypeface(null, Typeface.BOLD)
            }

        val unitPrice =
            TextView(requireContext()).apply {

                text =
                    "${formatMoney(item.price)} / món"

                textSize = 14f
                setTextColor(
                    Color.rgb(232, 25, 44)
                )
            }

        information.addView(name)
        information.addView(unitPrice)

        topRow.addView(
            information,
            LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f
            )
        )

        val btnDelete =
            createSmallButton("XÓA")

        btnDelete.setOnClickListener {

            CartManager.removeItem(item)

            Toast.makeText(
                requireContext(),
                "Đã xóa ${item.name}",
                Toast.LENGTH_SHORT
            ).show()

            renderCart()
        }

        topRow.addView(btnDelete)

        itemLayout.addView(topRow)

        val quantityRow =
            LinearLayout(requireContext()).apply {

                orientation =
                    LinearLayout.HORIZONTAL

                gravity =
                    Gravity.CENTER_VERTICAL or
                            Gravity.END
            }

        val btnMinus =
            createSmallButton("−")

        val txtQuantity =
            TextView(requireContext()).apply {

                text =
                    item.quantity.toString()

                textSize = 17f
                gravity = Gravity.CENTER
                setTypeface(null, Typeface.BOLD)
            }

        val btnPlus =
            createSmallButton("+")

        val lineTotal =
            TextView(requireContext()).apply {

                text =
                    formatMoney(
                        item.price *
                                item.quantity
                    )

                textSize = 16f
                setTextColor(
                    Color.rgb(232, 25, 44)
                )
                setTypeface(null, Typeface.BOLD)
            }

        quantityRow.addView(
            lineTotal,
            LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f
            )
        )

        quantityRow.addView(btnMinus)

        quantityRow.addView(
            txtQuantity,
            LinearLayout.LayoutParams(
                dp(45),
                dp(45)
            )
        )

        quantityRow.addView(btnPlus)

        btnMinus.isEnabled =
            item.quantity > 1

        btnMinus.setOnClickListener {

            CartManager.decreaseQuantity(item)
            renderCart()
        }

        btnPlus.setOnClickListener {

            CartManager.increaseQuantity(item)
            renderCart()
        }

        itemLayout.addView(quantityRow)

        return itemLayout
    }

    private fun createSmallButton(
        label: String
    ): Button {

        return Button(requireContext()).apply {

            text = label
            textSize = 12f
            isAllCaps = false

            minWidth = 0
            minimumWidth = 0
            minHeight = 0
            minimumHeight = 0

            layoutParams =
                LinearLayout.LayoutParams(
                    dp(60),
                    dp(45)
                )
        }
    }

    private fun formatMoney(
        money: Int
    ): String {

        return String.format(
            Locale.US,
            "%,dđ",
            money
        ).replace(",", ".")
    }

    private fun dp(value: Int): Int {

        return (
                value *
                        resources.displayMetrics.density
                ).toInt()
    }
}