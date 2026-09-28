package com.example.tuyfood

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import java.util.Locale

class OrderTrackingFragment : Fragment() {

    private var currentStep = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(
            R.layout.fragment_order_tracking,
            container,
            false
        )

        val btnBack =
            view.findViewById<TextView>(R.id.btnBack)

        btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        val orderId = arguments?.getInt("orderId") ?: -1
        val order = OrderManager.findById(orderId)

        val txtOrderId =
            view.findViewById<TextView>(R.id.txtOrderId)

        val txtCurrentStatus =
            view.findViewById<TextView>(R.id.txtCurrentStatus)

        val txtEstimatedTime =
            view.findViewById<TextView>(R.id.txtEstimatedTime)

        val iconStep1 =
            view.findViewById<TextView>(R.id.iconStep1)

        val iconStep2 =
            view.findViewById<TextView>(R.id.iconStep2)

        val iconStep3 =
            view.findViewById<TextView>(R.id.iconStep3)

        val iconStep4 =
            view.findViewById<TextView>(R.id.iconStep4)

        val iconStep5 =
            view.findViewById<TextView>(R.id.iconStep5)

        val foodListContainer =
            view.findViewById<LinearLayout>(
                R.id.foodListContainer
            )

        // =========================
        // KHÔNG CÓ ĐƠN HÀNG
        // =========================

        if (order == null) {

            txtOrderId.text =
                "Chưa có đơn hàng"

            txtCurrentStatus.text =
                "Bạn chưa có đơn hàng nào"

            txtEstimatedTime.text =
                "Hãy đặt món để bắt đầu theo dõi đơn hàng."

            return view
        }

        // =========================
        // THÔNG TIN ĐƠN
        // =========================

        currentStep = order.status

        txtOrderId.text =
            "#${order.orderId}"

        // =========================
        // HIỂN THỊ TẤT CẢ MÓN
        // =========================

        foodListContainer.removeAllViews()

        order.items.forEach { food ->

            val foodCard =
                LinearLayout(requireContext())

            foodCard.orientation =
                LinearLayout.HORIZONTAL

            foodCard.setPadding(
                16,
                16,
                16,
                16
            )

            foodCard.setBackgroundColor(
                Color.WHITE
            )

            val cardParams =
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )

            cardParams.setMargins(
                0,
                0,
                0,
                12
            )

            foodCard.layoutParams =
                cardParams

            // =========================
            // ẢNH MÓN
            // =========================

            val imgFood =
                ImageView(requireContext())

            val imageParams =
                LinearLayout.LayoutParams(
                    64,
                    64
                )

            imgFood.layoutParams =
                imageParams

            imgFood.scaleType =
                ImageView.ScaleType.CENTER_CROP

            if (food.imageRes != 0) {

                imgFood.setImageResource(
                    food.imageRes
                )

            } else {

                imgFood.setImageResource(
                    R.drawable.ic_launcher_foreground
                )
            }

            // =========================
            // THÔNG TIN MÓN
            // =========================

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

            infoParams.setMargins(
                14,
                0,
                0,
                0
            )

            infoLayout.layoutParams =
                infoParams

            val txtFoodName =
                TextView(requireContext())

            txtFoodName.text =
                food.name

            txtFoodName.textSize =
                17f

            txtFoodName.setTextColor(
                Color.rgb(24, 24, 24)
            )

            txtFoodName.setTypeface(
                null,
                android.graphics.Typeface.BOLD
            )

            val txtFoodPrice =
                TextView(requireContext())

            txtFoodPrice.text =
                formatMoney(food.price)

            txtFoodPrice.textSize =
                13f

            txtFoodPrice.setTextColor(
                Color.rgb(119, 119, 119)
            )

            val priceParams =
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )

            priceParams.setMargins(
                0,
                5,
                0,
                0
            )

            txtFoodPrice.layoutParams =
                priceParams

            val txtFoodQuantity =
                TextView(requireContext())

            txtFoodQuantity.text =
                "x${food.quantity}"

            txtFoodQuantity.textSize =
                13f

            txtFoodQuantity.setTextColor(
                Color.rgb(153, 153, 153)
            )

            val quantityParams =
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )

            quantityParams.setMargins(
                0,
                6,
                0,
                0
            )

            txtFoodQuantity.layoutParams =
                quantityParams

            infoLayout.addView(
                txtFoodName
            )

            infoLayout.addView(
                txtFoodPrice
            )

            infoLayout.addView(
                txtFoodQuantity
            )

            // =========================
            // THÀNH TIỀN
            // =========================

            val txtFoodTotal =
                TextView(requireContext())

            txtFoodTotal.text =
                formatMoney(
                    food.price * food.quantity
                )

            txtFoodTotal.textSize =
                15f

            txtFoodTotal.setTextColor(
                Color.rgb(24, 24, 24)
            )

            txtFoodTotal.setTypeface(
                null,
                android.graphics.Typeface.BOLD
            )

            foodCard.addView(
                imgFood
            )

            foodCard.addView(
                infoLayout
            )

            foodCard.addView(
                txtFoodTotal
            )

            foodListContainer.addView(
                foodCard
            )
        }

        // =========================
        // TRẠNG THÁI
        // =========================

        fun updateStatus() {

            val icons =
                listOf(
                    iconStep1,
                    iconStep2,
                    iconStep3,
                    iconStep4,
                    iconStep5
                )

            val statusNames =
                listOf(
                    "Đã đặt hàng",
                    "Nhà hàng đã xác nhận",
                    "Đang chuẩn bị món",
                    "Tài xế đang giao",
                    "Đã giao"
                )

            val timeTexts =
                listOf(
                    "25 - 30 phút",
                    "20 - 25 phút",
                    "15 - 20 phút",
                    "10 - 15 phút",
                    "Đã giao thành công"
                )

            currentStep =
                currentStep.coerceIn(1, 5)

            txtCurrentStatus.text =
                statusNames[currentStep - 1]

            txtEstimatedTime.text =
                timeTexts[currentStep - 1]

            for (i in icons.indices) {

                val icon =
                    icons[i]

                when {

                    i + 1 < currentStep -> {

                        icon.text =
                            "✓"

                        icon.setBackgroundColor(
                            Color.rgb(
                                232,
                                25,
                                44
                            )
                        )

                        icon.setTextColor(
                            Color.WHITE
                        )
                    }

                    i + 1 == currentStep -> {

                        icon.text =
                            "●"

                        icon.setBackgroundColor(
                            Color.rgb(
                                232,
                                25,
                                44
                            )
                        )

                        icon.setTextColor(
                            Color.WHITE
                        )
                    }

                    else -> {

                        icon.text =
                            "○"

                        icon.setBackgroundColor(
                            Color.rgb(
                                229,
                                229,
                                229
                            )
                        )

                        icon.setTextColor(
                            Color.rgb(
                                153,
                                153,
                                153
                            )
                        )
                    }
                }
            }
        }

        // Khách hàng chỉ xem trạng thái.
        // Shop/Admin là phía cập nhật trạng thái.

        updateStatus()

        return view
    }

    // =========================
    // FORMAT TIỀN
    // =========================

    private fun formatMoney(
        money: Int
    ): String {

        return String.format(
            Locale.US,
            "%,dđ",
            money
        ).replace(
            ",",
            "."
        )
    }
}