package com.example.tuyfood

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class AdminOrderFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(
            R.layout.fragment_admin_order,
            container,
            false
        )

        val orderContainer =
            view.findViewById<LinearLayout>(
                R.id.orderContainer
            )

        loadOrders(orderContainer)

        return view
    }

    private fun loadOrders(
        orderContainer: LinearLayout
    ) {

        lifecycleScope.launch {

            try {

                val orders =
                    RetrofitClient.instance.getAllOrders()

                orderContainer.removeAllViews()

                if (orders.isEmpty()) {

                    val emptyText = TextView(requireContext())

                    emptyText.text = "Chưa có đơn hàng"
                    emptyText.textSize = 16f

                    orderContainer.addView(emptyText)

                    return@launch
                }

                orders.forEach { order ->

                    val orderLayout =
                        LinearLayout(requireContext())

                    orderLayout.orientation =
                        LinearLayout.VERTICAL

                    orderLayout.setPadding(
                        12,
                        12,
                        12,
                        20
                    )

                    val orderInfo =
                        TextView(requireContext())

                    orderInfo.text =
                        "Đơn #${order.id}\n" +
                                "Tổng tiền: ${order.totalAmount.toInt()}đ\n" +
                                "Trạng thái hiện tại: ${order.status}"

                    orderInfo.textSize = 16f

                    val spinner =
                        Spinner(requireContext())

                    val statuses = listOf(
                        "PENDING",
                        "CONFIRMED",
                        "PREPARING",
                        "DELIVERING",
                        "COMPLETED",
                        "CANCELLED"
                    )

                    val adapter =
                        ArrayAdapter(
                            requireContext(),
                            android.R.layout.simple_spinner_item,
                            statuses
                        )

                    adapter.setDropDownViewResource(
                        android.R.layout.simple_spinner_dropdown_item
                    )

                    spinner.adapter = adapter

                    val currentIndex =
                        statuses.indexOf(order.status)

                    if (currentIndex >= 0) {
                        spinner.setSelection(currentIndex)
                    }

                    val updateButton =
                        Button(requireContext())

                    updateButton.text =
                        "CẬP NHẬT TRẠNG THÁI"

                    updateButton.setOnClickListener {

                        val newStatus =
                            spinner.selectedItem.toString()

                        lifecycleScope.launch {

                            try {

                                RetrofitClient.instance
                                    .updateOrderStatus(
                                        orderId = order.id,
                                        status = newStatus
                                    )

                                Toast.makeText(
                                    requireContext(),
                                    "Đã cập nhật đơn #${order.id}",
                                    Toast.LENGTH_SHORT
                                ).show()

                                loadOrders(orderContainer)

                            } catch (e: Exception) {

                                Toast.makeText(
                                    requireContext(),
                                    "Cập nhật thất bại",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }

                    orderLayout.addView(orderInfo)

                    orderLayout.addView(spinner)

                    orderLayout.addView(updateButton)

                    orderContainer.addView(orderLayout)
                }

            } catch (e: Exception) {

                Toast.makeText(
                    requireContext(),
                    "Không thể tải danh sách đơn",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}