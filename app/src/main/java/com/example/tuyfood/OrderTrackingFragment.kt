package com.example.tuyfood

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

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

        val order = OrderManager.currentOrder

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

        val btnNextStatus =
            view.findViewById<Button>(R.id.btnNextStatus)

        if (order == null) {

            txtOrderId.text = "Chưa có đơn hàng"

            txtCurrentStatus.text =
                "Bạn chưa có đơn hàng nào"

            txtEstimatedTime.text =
                "Hãy đặt món để bắt đầu theo dõi đơn hàng."

            btnNextStatus.isEnabled = false

            return view
        }

        currentStep = order.status

        txtOrderId.text = "#${order.orderId}"

        fun updateStatus() {

            val icons = listOf(
                iconStep1,
                iconStep2,
                iconStep3,
                iconStep4,
                iconStep5
            )

            val statusNames = listOf(
                "Đã đặt hàng",
                "Nhà hàng đã xác nhận",
                "Đang chuẩn bị món",
                "Tài xế đang giao",
                "Đã giao"
            )

            val timeTexts = listOf(
                "25 - 30 phút",
                "20 - 25 phút",
                "15 - 20 phút",
                "10 - 15 phút",
                "Đã giao thành công"
            )

            txtCurrentStatus.text =
                statusNames[currentStep - 1]

            txtEstimatedTime.text =
                timeTexts[currentStep - 1]

            for (i in icons.indices) {

                val icon = icons[i]

                if (i + 1 < currentStep) {

                    icon.text = "✓"

                    icon.setBackgroundColor(
                        Color.rgb(232, 25, 44)
                    )

                    icon.setTextColor(Color.WHITE)

                } else if (i + 1 == currentStep) {

                    icon.text = "●"

                    icon.setBackgroundColor(
                        Color.rgb(232, 25, 44)
                    )

                    icon.setTextColor(Color.WHITE)

                } else {

                    icon.text = "○"

                    icon.setBackgroundColor(
                        Color.rgb(229, 229, 229)
                    )

                    icon.setTextColor(
                        Color.rgb(153, 153, 153)
                    )
                }
            }

            if (currentStep < 5) {

                btnNextStatus.text =
                    "CẬP NHẬT TRẠNG THÁI"

                btnNextStatus.isEnabled = true

            } else {

                btnNextStatus.text =
                    "✓ ĐƠN HÀNG ĐÃ GIAO"

                btnNextStatus.isEnabled = false
            }
        }

        btnNextStatus.setOnClickListener {

            if (currentStep < 5) {

                currentStep++

                OrderManager.updateStatus(currentStep)

                updateStatus()

                Toast.makeText(
                    requireContext(),
                    "Đã cập nhật trạng thái đơn hàng",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        updateStatus()

        return view
    }
}