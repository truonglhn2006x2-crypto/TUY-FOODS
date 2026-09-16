package com.example.tuyfood

import android.app.AlertDialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class OrderHistoryFragment : Fragment() {

    private enum class FilterType {
        ALL,
        PROCESSING,
        DELIVERING,
        COMPLETED,
        CANCELLED
    }

    private lateinit var historyContainer:
            LinearLayout

    private lateinit var searchInput:
            EditText

    private lateinit var filterButtons:
            Map<FilterType, Button>

    private var currentFilter =
        FilterType.ALL

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(
            R.layout.fragment_order_history,
            container,
            false
        )

        historyContainer =
            view.findViewById(
                R.id.orderHistoryContainer
            )

        searchInput =
            view.findViewById(
                R.id.edtSearchOrder
            )

        filterButtons = mapOf(
            FilterType.ALL to
                    view.findViewById(
                        R.id.btnFilterAll
                    ),

            FilterType.PROCESSING to
                    view.findViewById(
                        R.id.btnFilterProcessing
                    ),

            FilterType.DELIVERING to
                    view.findViewById(
                        R.id.btnFilterDelivering
                    ),

            FilterType.COMPLETED to
                    view.findViewById(
                        R.id.btnFilterCompleted
                    ),

            FilterType.CANCELLED to
                    view.findViewById(
                        R.id.btnFilterCancelled
                    )
        )

        filterButtons.forEach {
                (filter, button) ->

            button.setOnClickListener {
                currentFilter = filter
                updateFilterButtons()
                renderOrders()
            }
        }

        searchInput.addTextChangedListener(
            object : TextWatcher {

                override fun beforeTextChanged(
                    text: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(
                    text: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    renderOrders()
                }

                override fun afterTextChanged(
                    text: Editable?
                ) {
                }
            }
        )

        updateFilterButtons()
        renderOrders()

        return view
    }

    override fun onResume() {
        super.onResume()

        if (::historyContainer.isInitialized) {
            renderOrders()
        }
    }

    private fun renderOrders() {

        historyContainer.removeAllViews()

        val keyword =
            searchInput.text
                .toString()
                .trim()
                .lowercase(Locale.getDefault())

        val filteredOrders =
            OrderManager
                .getOrders()
                .filter { order ->

                    val correctStatus =
                        when (currentFilter) {

                            FilterType.ALL -> true

                            FilterType.PROCESSING ->
                                order.status in
                                        ORDER_PLACED..
                                        ORDER_PREPARING

                            FilterType.DELIVERING ->
                                order.status ==
                                        ORDER_DELIVERING

                            FilterType.COMPLETED ->
                                order.status ==
                                        ORDER_COMPLETED

                            FilterType.CANCELLED ->
                                order.status ==
                                        ORDER_CANCELLED
                        }

                    val correctKeyword =
                        keyword.isEmpty() ||
                                order.orderId
                                    .toString()
                                    .contains(keyword) ||
                                order.items.any {
                                    it.name
                                        .lowercase(
                                            Locale.getDefault()
                                        )
                                        .contains(keyword)
                                }

                    correctStatus &&
                            correctKeyword
                }

        if (filteredOrders.isEmpty()) {

            val emptyText =
                TextView(requireContext()).apply {

                    text =
                        "📦\n\nKhông có đơn hàng phù hợp"

                    textSize = 17f
                    setTextColor(Color.GRAY)
                    gravity = Gravity.CENTER

                    setPadding(
                        dp(10),
                        dp(80),
                        dp(10),
                        dp(80)
                    )

                    layoutParams =
                        LinearLayout.LayoutParams(
                            LinearLayout
                                .LayoutParams
                                .MATCH_PARENT,

                            LinearLayout
                                .LayoutParams
                                .WRAP_CONTENT
                        )
                }

            historyContainer.addView(emptyText)
            return
        }

        filteredOrders.forEach { order ->

            historyContainer.addView(
                createOrderCard(order)
            )
        }
    }

    private fun createOrderCard(
        order: Order
    ): View {

        val card =
            LinearLayout(requireContext()).apply {

                orientation =
                    LinearLayout.VERTICAL

                setPadding(
                    dp(16),
                    dp(14),
                    dp(16),
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
                                220,
                                220,
                                220
                            )
                        )
                    }

                layoutParams =
                    LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    ).apply {
                        bottomMargin = dp(12)
                    }
            }

        card.addView(
            createText(
                "Đơn hàng #${order.orderId}",
                18f,
                Color.DKGRAY,
                true
            )
        )

        card.addView(
            createText(
                formatDate(order.createdAt),
                13f,
                Color.GRAY
            )
        )

        card.addView(
            createText(
                "${order.items.size} món • " +
                        formatMoney(order.total),
                15f,
                Color.DKGRAY,
                true
            )
        )

        card.addView(
            createText(
                orderStatusText(order.status),
                15f,
                getStatusColor(order.status),
                true
            )
        )

        if (order.rating > 0) {
            card.addView(
                createText(
                    "Đánh giá: ${
                        "★".repeat(order.rating)
                    }",
                    15f,
                    Color.rgb(255, 152, 0)
                )
            )
        }

        val buttonRow =
            LinearLayout(requireContext()).apply {

                orientation =
                    LinearLayout.HORIZONTAL

                gravity = Gravity.END
            }

        val detailButton =
            createActionButton("CHI TIẾT")

        detailButton.setOnClickListener {
            showOrderDetail(order)
        }

        buttonRow.addView(
            detailButton,
            buttonLayoutParams()
        )

        if (order.status == ORDER_PLACED) {

            val cancelButton =
                createActionButton("HỦY ĐƠN")

            cancelButton.setOnClickListener {
                showCancelDialog(order)
            }

            buttonRow.addView(
                cancelButton,
                buttonLayoutParams()
            )
        }

        if (
            order.status == ORDER_COMPLETED ||
            order.status == ORDER_CANCELLED
        ) {

            val reorderButton =
                createActionButton("ĐẶT LẠI")

            reorderButton.setOnClickListener {
                reorder(order)
            }

            buttonRow.addView(
                reorderButton,
                buttonLayoutParams()
            )
        }

        if (order.status == ORDER_COMPLETED) {

            val ratingButton =
                createActionButton("ĐÁNH GIÁ")

            ratingButton.setOnClickListener {
                showRatingDialog(order)
            }

            buttonRow.addView(
                ratingButton,
                buttonLayoutParams()
            )
        }

        card.addView(buttonRow)

        return card
    }

    private fun showOrderDetail(
        order: Order
    ) {

        val groupedItems =
            order.items.groupBy { item ->

                "${item.id}|" +
                        "${item.name}|" +
                        "${item.price}|" +
                        item.emoji
            }

        val foodDetails =
            groupedItems.values.joinToString(
                separator = "\n\n"
            ) { sameItems ->

                val item =
                    sameItems.first()

                val quantity =
                    sameItems.size

                "${item.emoji} ${item.name}\n" +
                        "${formatMoney(item.price)} " +
                        "× $quantity = " +
                        formatMoney(
                            item.price * quantity
                        )
            }

        val information =
            "Ngày đặt: ${
                formatDate(order.createdAt)
            }\n" +
                    "Trạng thái: ${
                        orderStatusText(order.status)
                    }\n\n" +
                    "Món đã đặt:\n$foodDetails\n\n" +
                    "Tạm tính: ${
                        formatMoney(order.subtotal)
                    }\n" +
                    "Phí vận chuyển: ${
                        formatMoney(order.deliveryFee)
                    }\n" +
                    "Giảm đơn hàng: -${
                        formatMoney(order.orderDiscount)
                    }\n" +
                    "Giảm vận chuyển: -${
                        formatMoney(order.shippingDiscount)
                    }\n\n" +
                    "TỔNG THANH TOÁN: ${
                        formatMoney(order.total)
                    }"

        AlertDialog.Builder(requireContext())
            .setTitle(
                "Chi tiết đơn #${order.orderId}"
            )
            .setMessage(information)
            .setPositiveButton("Đóng", null)
            .show()
    }

    private fun showCancelDialog(
        order: Order
    ) {

        AlertDialog.Builder(requireContext())
            .setTitle("Hủy đơn hàng")
            .setMessage(
                "Bạn có chắc muốn hủy " +
                        "đơn #${order.orderId} không?"
            )
            .setNegativeButton("Không", null)
            .setPositiveButton("Hủy đơn") {
                    _, _ ->

                val success =
                    OrderManager.cancelOrder(
                        order.orderId
                    )

                Toast.makeText(
                    requireContext(),
                    if (success) {
                        "Đã hủy đơn hàng"
                    } else {
                        "Đơn hàng không thể hủy"
                    },
                    Toast.LENGTH_SHORT
                ).show()

                renderOrders()
            }
            .show()
    }

    private fun reorder(
        order: Order
    ) {

        val added =
            OrderManager.reorder(
                order.orderId
            )

        Toast.makeText(
            requireContext(),
            "Đã thêm $added món vào giỏ hàng",
            Toast.LENGTH_SHORT
        ).show()

        parentFragmentManager
            .beginTransaction()
            .replace(
                R.id.fragmentContainer,
                CartFragment()
            )
            .addToBackStack(null)
            .commit()
    }

    private fun showRatingDialog(
        order: Order
    ) {

        val ratingBar =
            RatingBar(requireContext()).apply {

                numStars = 5
                stepSize = 1f

                rating =
                    if (order.rating > 0) {
                        order.rating.toFloat()
                    } else {
                        5f
                    }
            }

        val ratingContainer =
            LinearLayout(requireContext()).apply {

                orientation =
                    LinearLayout.VERTICAL

                gravity = Gravity.CENTER

                setPadding(
                    dp(24),
                    dp(12),
                    dp(24),
                    dp(12)
                )

                addView(ratingBar)
            }

        AlertDialog.Builder(requireContext())
            .setTitle(
                "Đánh giá đơn #${order.orderId}"
            )
            .setView(ratingContainer)
            .setNegativeButton("Hủy", null)
            .setPositiveButton("Lưu") {
                    _, _ ->

                val rating =
                    ratingBar.rating.toInt()

                val success =
                    OrderManager.rateOrder(
                        order.orderId,
                        rating
                    )

                Toast.makeText(
                    requireContext(),
                    if (success) {
                        "Đã lưu đánh giá"
                    } else {
                        "Không thể đánh giá"
                    },
                    Toast.LENGTH_SHORT
                ).show()

                renderOrders()
            }
            .show()
    }

    private fun updateFilterButtons() {

        filterButtons.forEach {
                (filter, button) ->

            val selected =
                filter == currentFilter

            button.backgroundTintList =
                ColorStateList.valueOf(
                    if (selected) {
                        Color.rgb(232, 25, 44)
                    } else {
                        Color.rgb(225, 225, 225)
                    }
                )

            button.setTextColor(
                if (selected) {
                    Color.WHITE
                } else {
                    Color.DKGRAY
                }
            )
        }
    }

    private fun createText(
        value: String,
        size: Float,
        color: Int,
        bold: Boolean = false
    ): TextView {

        return TextView(requireContext()).apply {

            text = value
            textSize = size
            setTextColor(color)

            setPadding(
                0,
                dp(3),
                0,
                dp(3)
            )

            if (bold) {
                setTypeface(
                    null,
                    Typeface.BOLD
                )
            }
        }
    }

    private fun createActionButton(
        value: String
    ): Button {

        return Button(requireContext()).apply {

            text = value
            textSize = 11f
            isAllCaps = false

            minWidth = 0
            minimumWidth = 0
        }
    }

    private fun buttonLayoutParams():
            LinearLayout.LayoutParams {

        return LinearLayout.LayoutParams(
            0,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            1f
        ).apply {
            marginStart = dp(2)
            marginEnd = dp(2)
        }
    }

    private fun getStatusColor(
        status: Int
    ): Int {

        return when (status) {

            ORDER_CANCELLED ->
                Color.GRAY

            ORDER_COMPLETED ->
                Color.rgb(25, 150, 70)

            ORDER_DELIVERING ->
                Color.rgb(30, 100, 210)

            else ->
                Color.rgb(232, 25, 44)
        }
    }

    private fun formatDate(
        time: Long
    ): String {

        return SimpleDateFormat(
            "dd/MM/yyyy HH:mm",
            Locale("vi", "VN")
        ).format(Date(time))
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