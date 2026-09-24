package com.example.tuyfood

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(
            R.layout.fragment_profile,
            container,
            false
        )
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(
            view,
            savedInstanceState
        )

        val txtProfileName =
            view.findViewById<TextView>(
                R.id.txtProfileName
            )

        val txtMemberLevel =
            view.findViewById<TextView>(
                R.id.txtMemberLevel
            )

        val txtTotalSpent =
            view.findViewById<TextView>(
                R.id.txtTotalSpent
            )

        val txtNextLevel =
            view.findViewById<TextView>(
                R.id.txtNextLevel
            )

        val progressMembership =
            view.findViewById<ProgressBar>(
                R.id.progressMembership
            )

        val txtProgressPercent =
            view.findViewById<TextView>(
                R.id.txtProgressPercent
            )

        val txtUnlockedBenefits =
            view.findViewById<TextView>(
                R.id.txtUnlockedBenefits
            )

        val txtLockedBenefits =
            view.findViewById<TextView>(
                R.id.txtLockedBenefits
            )

        val progressLoading =
            view.findViewById<ProgressBar>(
                R.id.progressLoading
            )

        val txtProfileError =
            view.findViewById<TextView>(
                R.id.txtProfileError
            )

        val btnRetry =
            view.findViewById<Button>(
                R.id.btnRetryProfile
            )

        val btnLogout =
            view.findViewById<Button>(
                R.id.btnLogout
            )

        val preferences =
            requireContext()
                .getSharedPreferences(
                    "TuyFoods",
                    Context.MODE_PRIVATE
                )

        val userId =
            preferences.getLong(
                "userId",
                -1L
            )

        if (userId == -1L) {
            openLogin()
            return
        }

        fun loadMembership() {

            progressLoading.visibility =
                View.VISIBLE

            txtProfileError.visibility =
                View.GONE

            btnRetry.visibility =
                View.GONE

            viewLifecycleOwner
                .lifecycleScope
                .launch {

                    try {
                        val membership =
                            RetrofitClient
                                .instance
                                .getMembership(userId)

                        txtProfileName.text =
                            membership.name

                        txtMemberLevel.text =
                            "Hạng: ${levelName(membership.level)}"

                        txtMemberLevel.setTextColor(
                            levelColor(membership.level)
                        )

                        txtTotalSpent.text =
                            "Tổng chi tiêu: ${
                                formatMoney(
                                    membership.totalCompletedSpend
                                )
                            }"

                        if (membership.nextLevel == null) {
                            txtNextLevel.text =
                                "Bạn đã đạt hạng cao nhất"
                        } else {
                            txtNextLevel.text =
                                "Còn ${
                                    formatMoney(
                                        membership.remainingAmount
                                    )
                                } để đạt hạng ${
                                    levelName(
                                        membership.nextLevel
                                    )
                                }"
                        }

                        progressMembership.progress =
                            membership.progressPercent
                                .coerceIn(0, 100)

                        txtProgressPercent.text =
                            "${membership.progressPercent}%"

                        txtUnlockedBenefits.text =
                            membership.unlockedFeatures
                                .joinToString("\n") {
                                    "✓ ${featureName(it)}"
                                }
                                .ifEmpty {
                                    "Chưa có quyền lợi"
                                }

                        txtLockedBenefits.text =
                            membership.lockedFeatures
                                .joinToString("\n") {
                                    "🔒 ${featureName(it)}"
                                }
                                .ifEmpty {
                                    "Bạn đã mở toàn bộ quyền lợi"
                                }

                    } catch (exception: Exception) {

                        txtProfileError.text =
                            "Không thể tải hồ sơ thành viên. " +
                                    "Hãy kiểm tra backend và kết nối mạng."

                        txtProfileError.visibility =
                            View.VISIBLE

                        btnRetry.visibility =
                            View.VISIBLE

                    } finally {
                        progressLoading.visibility =
                            View.GONE
                    }
                }
        }

        btnRetry.setOnClickListener {
            loadMembership()
        }

        btnLogout.setOnClickListener {

            preferences.edit()
                .remove("userId")
                .remove("role")
                .apply()

            openLogin()
        }

        loadMembership()
    }

    private fun openLogin() {

        parentFragmentManager
            .beginTransaction()
            .replace(
                R.id.fragmentContainer,
                LoginFragment()
            )
            .commit()
    }

    private fun levelName(
        level: String
    ): String {
        return when (level) {
            "POTENTIAL" -> "Tiềm năng"
            "VIP" -> "VIP"
            else -> "Bình thường"
        }
    }

    private fun levelColor(
        level: String
    ): Int {
        return when (level) {
            "POTENTIAL" ->
                Color.parseColor("#1976D2")

            "VIP" ->
                Color.parseColor("#D4A017")

            else ->
                Color.parseColor("#666666")
        }
    }

    private fun featureName(
        feature: String
    ): String {
        return when (feature) {
            "PROFILE" ->
                "Hồ sơ thành viên"

            "STANDARD_VOUCHERS" ->
                "Voucher đơn hàng và vận chuyển"

            "MEMBER_VOUCHER_WALLET" ->
                "Kho voucher thành viên"

            "POTENTIAL_VOUCHER" ->
                "Voucher dành cho hạng Tiềm năng"

            "VIP_VOUCHER" ->
                "Voucher độc quyền VIP"

            "MONTHLY_MINIGAME_KEY" ->
                "Chìa khóa mini game hàng tháng"

            else -> feature
        }
    }

    private fun formatMoney(
        amount: Double
    ): String {
        val formatter =
            NumberFormat.getCurrencyInstance(
                Locale("vi", "VN")
            )

        return formatter.format(amount)
    }
}