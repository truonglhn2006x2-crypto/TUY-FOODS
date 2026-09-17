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
<<<<<<< HEAD
import android.widget.ImageView
=======
>>>>>>> d31ed13d44c2f34ceb4e4c95166b592a82ce5f7c
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
<<<<<<< HEAD
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import java.util.Locale
=======
import java.util.Locale
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
>>>>>>> d31ed13d44c2f34ceb4e4c95166b592a82ce5f7c

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

<<<<<<< HEAD
        cartContainer = view.findViewById(R.id.cartContainer)
        txtItemCount = view.findViewById(R.id.txtItemCount)
        txtSubtotal = view.findViewById(R.id.txtSubtotal)
        txtOrderDiscount = view.findViewById(R.id.txtOrderDiscount)
        txtDeliveryFee = view.findViewById(R.id.txtDeliveryFee)
        txtShippingDiscount = view.findViewById(R.id.txtShippingDiscount)
        txtTotal = view.findViewById(R.id.txtTotal)

        edtOrderVoucher = view.findViewById(R.id.edtOrderVoucher)
        edtShippingVoucher = view.findViewById(R.id.edtShippingVoucher)

        btnClearCart = view.findViewById(R.id.btnClearCart)
        btnApplyOrderVoucher = view.findViewById(R.id.btnApplyOrderVoucher)
        btnApplyShippingVoucher = view.findViewById(R.id.btnApplyShippingVoucher)
        btnCheckout = view.findViewById(R.id.btnCheckout)

        // =========================
        // VOUCHER ĐƠN HÀNG
        // =========================

        btnApplyOrderVoucher.setOnClickListener {

            val result = CartManager.applyOrderVoucher(
                edtOrderVoucher.text.toString()
            )
=======
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
>>>>>>> d31ed13d44c2f34ceb4e4c95166b592a82ce5f7c

            Toast.makeText(
                requireContext(),
                result.message,
                Toast.LENGTH_SHORT
            ).show()

            renderCart()
        }

<<<<<<< HEAD
        // =========================
        // VOUCHER VẬN CHUYỂN
        // =========================

        btnApplyShippingVoucher.setOnClickListener {

            val result = CartManager.applyShippingVoucher(
                edtShippingVoucher.text.toString()
            )
=======
        btnApplyShippingVoucher.setOnClickListener {

            val result =
                CartManager.applyShippingVoucher(
                    edtShippingVoucher.text.toString()
                )
>>>>>>> d31ed13d44c2f34ceb4e4c95166b592a82ce5f7c

            Toast.makeText(
                requireContext(),
                result.message,
                Toast.LENGTH_SHORT
            ).show()

            renderCart()
        }

<<<<<<< HEAD
        // =========================
        // XÓA TOÀN BỘ GIỎ
        // =========================

=======
>>>>>>> d31ed13d44c2f34ceb4e4c95166b592a82ce5f7c
        btnClearCart.setOnClickListener {

            AlertDialog.Builder(requireContext())
                .setTitle("Xóa giỏ hàng")
<<<<<<< HEAD
                .setMessage("Bạn có chắc muốn xóa toàn bộ món?")
=======
                .setMessage(
                    "Bạn có chắc muốn xóa toàn bộ món?"
                )
>>>>>>> d31ed13d44c2f34ceb4e4c95166b592a82ce5f7c
                .setNegativeButton("Không", null)
                .setPositiveButton("Xóa") { _, _ ->

                    CartManager.clearCart()
                    renderCart()
                }
                .show()
        }

<<<<<<< HEAD
        // =========================
        // ĐẶT HÀNG
        // =========================

        btnCheckout.setOnClickListener {

            if (CartManager.items.isEmpty()) {

                Toast.makeText(
                    requireContext(),
                    "Giỏ hàng đang trống",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            val prefs =
                requireContext().getSharedPreferences(
                    "TuyFoods",
                    0
                )

            val userId =
                prefs.getLong("userId", -1L)

            if (userId == -1L) {

                Toast.makeText(
                    requireContext(),
                    "Vui lòng đăng nhập!",
                    Toast.LENGTH_SHORT
                ).show()

=======
        btnCheckout.setOnClickListener {
            if (CartManager.items.isEmpty()) {
                Toast.makeText(requireContext(), "Giỏ hàng đang trống", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val prefs = requireContext().getSharedPreferences("TuyFoods", 0)
            val userId = prefs.getLong("userId", -1L)

            if (userId == -1L) {
                Toast.makeText(requireContext(), "Vui lòng đăng nhập!", Toast.LENGTH_SHORT).show()
>>>>>>> d31ed13d44c2f34ceb4e4c95166b592a82ce5f7c
                return@setOnClickListener
            }

            lifecycleScope.launch {
<<<<<<< HEAD

                try {

                    val orderItems =
                        CartManager.items.map {

                            OrderItemRequest(
                                productId = it.id.toLong(),
                                quantity = it.quantity
                            )
                        }

                    val response =
                        RetrofitClient.instance.createOrder(

                            OrderRequest(
                                userId = userId,
                                deliveryAddress = "Địa chỉ mặc định",
                                phone = "",
                                items = orderItems
                            )
                        )
=======
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
>>>>>>> d31ed13d44c2f34ceb4e4c95166b592a82ce5f7c

                    OrderManager.createOrder()

                    Toast.makeText(
                        requireContext(),
                        "Đặt hàng thành công! Mã đơn #${response.orderId}",
                        Toast.LENGTH_SHORT
                    ).show()

<<<<<<< HEAD
                    parentFragmentManager
                        .beginTransaction()
                        .replace(
                            R.id.fragmentContainer,
                            OrderTrackingFragment()
                        )
=======
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, OrderTrackingFragment())
>>>>>>> d31ed13d44c2f34ceb4e4c95166b592a82ce5f7c
                        .addToBackStack(null)
                        .commit()

                } catch (e: Exception) {
<<<<<<< HEAD

                    Toast.makeText(
                        requireContext(),
                        "Lỗi kết nối server!",
                        Toast.LENGTH_SHORT
                    ).show()
=======
                    Toast.makeText(requireContext(), "Lỗi kết nối server!", Toast.LENGTH_SHORT).show()
>>>>>>> d31ed13d44c2f34ceb4e4c95166b592a82ce5f7c
                }
            }
        }

        renderCart()

        return view
    }

    override fun onResume() {
<<<<<<< HEAD

=======
>>>>>>> d31ed13d44c2f34ceb4e4c95166b592a82ce5f7c
        super.onResume()

        if (::cartContainer.isInitialized) {
            renderCart()
        }
    }

<<<<<<< HEAD
    // =========================================================
    // HIỂN THỊ GIỎ HÀNG
    // =========================================================

=======
>>>>>>> d31ed13d44c2f34ceb4e4c95166b592a82ce5f7c
    private fun renderCart() {

        cartContainer.removeAllViews()

<<<<<<< HEAD
        val items = CartManager.items.toList()

        val isEmpty = items.isEmpty()
=======
        val items =
            CartManager.items.toList()

        val isEmpty =
            items.isEmpty()
>>>>>>> d31ed13d44c2f34ceb4e4c95166b592a82ce5f7c

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
<<<<<<< HEAD

=======
>>>>>>> d31ed13d44c2f34ceb4e4c95166b592a82ce5f7c
                    setPadding(
                        dp(10),
                        dp(60),
                        dp(10),
                        dp(60)
                    )
                }

            cartContainer.addView(emptyText)
<<<<<<< HEAD

        } else {

            items.forEach { item ->

=======
        } else {

            items.forEach { item ->
>>>>>>> d31ed13d44c2f34ceb4e4c95166b592a82ce5f7c
                cartContainer.addView(
                    createItemView(item)
                )
            }
        }

<<<<<<< HEAD
        // =========================
        // TÍNH TIỀN
        // =========================

=======
>>>>>>> d31ed13d44c2f34ceb4e4c95166b592a82ce5f7c
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
<<<<<<< HEAD

                "Giảm giá đơn hàng: -0đ"

            } else {

=======
                "Giảm giá đơn hàng: -0đ"
            } else {
>>>>>>> d31ed13d44c2f34ceb4e4c95166b592a82ce5f7c
                "$orderCode: -${formatMoney(orderDiscount)}"
            }

        val shippingCode =
            CartManager.appliedShippingVoucherCode

        txtShippingDiscount.text =
            if (shippingCode == null) {
<<<<<<< HEAD

                "Giảm phí vận chuyển: -0đ"

            } else {

=======
                "Giảm phí vận chuyển: -0đ"
            } else {
>>>>>>> d31ed13d44c2f34ceb4e4c95166b592a82ce5f7c
                "$shippingCode: -${formatMoney(shippingDiscount)}"
            }
    }

<<<<<<< HEAD
    // =========================================================
    // TẠO ITEM TRONG GIỎ
    // =========================================================

=======
>>>>>>> d31ed13d44c2f34ceb4e4c95166b592a82ce5f7c
    private fun createItemView(
        item: FoodItem
    ): View {

        val itemLayout =
            LinearLayout(requireContext()).apply {

<<<<<<< HEAD
                orientation =
                    LinearLayout.VERTICAL

                setPadding(
                    dp(12),
                    dp(12),
                    dp(12),
                    dp(12)
=======
                orientation = LinearLayout.VERTICAL

                setPadding(
                    dp(14),
                    dp(14),
                    dp(14),
                    dp(14)
>>>>>>> d31ed13d44c2f34ceb4e4c95166b592a82ce5f7c
                )

                background =
                    GradientDrawable().apply {

                        setColor(Color.WHITE)
<<<<<<< HEAD

=======
>>>>>>> d31ed13d44c2f34ceb4e4c95166b592a82ce5f7c
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
<<<<<<< HEAD

                        bottomMargin =
                            dp(10)
                    }
            }

        // =====================================================
        // HÀNG TRÊN
        // =====================================================

=======
                        bottomMargin = dp(10)
                    }
            }

>>>>>>> d31ed13d44c2f34ceb4e4c95166b592a82ce5f7c
        val topRow =
            LinearLayout(requireContext()).apply {

                orientation =
                    LinearLayout.HORIZONTAL

                gravity =
                    Gravity.CENTER_VERTICAL
            }

<<<<<<< HEAD
        // =====================================================
        // ẢNH MÓN ĂN
        // =====================================================

        val imgFood =
            ImageView(requireContext()).apply {

                scaleType =
                    ImageView.ScaleType.CENTER_CROP

                background =
                    GradientDrawable().apply {

                        setColor(Color.LTGRAY)

                        cornerRadius =
                            dp(10).toFloat()
                    }

                if (item.imageRes != 0) {

                    setImageResource(
                        item.imageRes
                    )
                }
            }

        topRow.addView(
            imgFood,
            LinearLayout.LayoutParams(
                dp(75),
                dp(75)
            )
        )

        // =====================================================
        // THÔNG TIN MÓN
        // =====================================================

=======
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

>>>>>>> d31ed13d44c2f34ceb4e4c95166b592a82ce5f7c
        val information =
            LinearLayout(requireContext()).apply {

                orientation =
                    LinearLayout.VERTICAL
<<<<<<< HEAD

                gravity =
                    Gravity.CENTER_VERTICAL

                setPadding(
                    dp(12),
                    0,
                    dp(8),
                    0
                )
=======
>>>>>>> d31ed13d44c2f34ceb4e4c95166b592a82ce5f7c
            }

        val name =
            TextView(requireContext()).apply {

<<<<<<< HEAD
                text =
                    item.name

                textSize =
                    16f

                setTextColor(
                    Color.rgb(
                        40,
                        40,
                        40
                    )
                )

                setTypeface(
                    null,
                    Typeface.BOLD
                )
=======
                text = item.name
                textSize = 16f
                setTextColor(Color.DKGRAY)
                setTypeface(null, Typeface.BOLD)
>>>>>>> d31ed13d44c2f34ceb4e4c95166b592a82ce5f7c
            }

        val unitPrice =
            TextView(requireContext()).apply {

                text =
                    "${formatMoney(item.price)} / món"

<<<<<<< HEAD
                textSize =
                    14f

                setTextColor(
                    Color.rgb(
                        232,
                        25,
                        44
                    )
                )

                setPadding(
                    0,
                    dp(4),
                    0,
                    0
=======
                textSize = 14f
                setTextColor(
                    Color.rgb(232, 25, 44)
>>>>>>> d31ed13d44c2f34ceb4e4c95166b592a82ce5f7c
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

<<<<<<< HEAD
        // =====================================================
        // NÚT XÓA
        // =====================================================

=======
>>>>>>> d31ed13d44c2f34ceb4e4c95166b592a82ce5f7c
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

<<<<<<< HEAD
        // =====================================================
        // HÀNG SỐ LƯỢNG
        // =====================================================

=======
>>>>>>> d31ed13d44c2f34ceb4e4c95166b592a82ce5f7c
        val quantityRow =
            LinearLayout(requireContext()).apply {

                orientation =
                    LinearLayout.HORIZONTAL

                gravity =
<<<<<<< HEAD
                    Gravity.CENTER_VERTICAL
            }

        // Tổng tiền của món
=======
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

>>>>>>> d31ed13d44c2f34ceb4e4c95166b592a82ce5f7c
        val lineTotal =
            TextView(requireContext()).apply {

                text =
                    formatMoney(
                        item.price *
                                item.quantity
                    )

<<<<<<< HEAD
                textSize =
                    16f

                setTextColor(
                    Color.rgb(
                        232,
                        25,
                        44
                    )
                )

                setTypeface(
                    null,
                    Typeface.BOLD
                )
=======
                textSize = 16f
                setTextColor(
                    Color.rgb(232, 25, 44)
                )
                setTypeface(null, Typeface.BOLD)
>>>>>>> d31ed13d44c2f34ceb4e4c95166b592a82ce5f7c
            }

        quantityRow.addView(
            lineTotal,
            LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f
            )
        )

<<<<<<< HEAD
        // Nút -
        val btnMinus =
            createSmallButton("−")

        // Số lượng
        val txtQuantity =
            TextView(requireContext()).apply {

                text =
                    item.quantity.toString()

                textSize =
                    17f

                gravity =
                    Gravity.CENTER

                setTypeface(
                    null,
                    Typeface.BOLD
                )
            }

        // Nút +
        val btnPlus =
            createSmallButton("+")

=======
>>>>>>> d31ed13d44c2f34ceb4e4c95166b592a82ce5f7c
        quantityRow.addView(btnMinus)

        quantityRow.addView(
            txtQuantity,
            LinearLayout.LayoutParams(
                dp(45),
                dp(45)
            )
        )

        quantityRow.addView(btnPlus)

<<<<<<< HEAD
        // Không cho giảm nếu đang là 1
        btnMinus.isEnabled =
            item.quantity > 1

        // Giảm số lượng
        btnMinus.setOnClickListener {

            CartManager.decreaseQuantity(item)

            renderCart()
        }

        // Tăng số lượng
        btnPlus.setOnClickListener {

            CartManager.increaseQuantity(item)

            renderCart()
        }

        itemLayout.addView(
            quantityRow
        )
=======
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
>>>>>>> d31ed13d44c2f34ceb4e4c95166b592a82ce5f7c

        return itemLayout
    }

<<<<<<< HEAD
    // =========================================================
    // TẠO BUTTON NHỎ
    // =========================================================

=======
>>>>>>> d31ed13d44c2f34ceb4e4c95166b592a82ce5f7c
    private fun createSmallButton(
        label: String
    ): Button {

        return Button(requireContext()).apply {

<<<<<<< HEAD
            text =
                label

            textSize =
                12f

            isAllCaps =
                false

            minWidth =
                0

            minimumWidth =
                0

            minHeight =
                0

            minimumHeight =
                0

            layoutParams =
                LinearLayout.LayoutParams(
                    dp(55),
                    dp(42)
=======
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
>>>>>>> d31ed13d44c2f34ceb4e4c95166b592a82ce5f7c
                )
        }
    }

<<<<<<< HEAD
    // =========================================================
    // FORMAT TIỀN
    // =========================================================

=======
>>>>>>> d31ed13d44c2f34ceb4e4c95166b592a82ce5f7c
    private fun formatMoney(
        money: Int
    ): String {

        return String.format(
            Locale.US,
            "%,dđ",
            money
        ).replace(",", ".")
    }

<<<<<<< HEAD
    // =========================================================
    // DP
    // =========================================================

    private fun dp(
        value: Int
    ): Int {
=======
    private fun dp(value: Int): Int {
>>>>>>> d31ed13d44c2f34ceb4e4c95166b592a82ce5f7c

        return (
                value *
                        resources.displayMetrics.density
                ).toInt()
    }
}