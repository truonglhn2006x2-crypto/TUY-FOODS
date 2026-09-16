package com.example.tuyfood

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.HapticFeedbackConstants
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.Button
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class MysteryBoxFragment : Fragment() {

    private lateinit var rewardScroll:
            HorizontalScrollView

    private lateinit var rewardTrack:
            LinearLayout

    private lateinit var txtKeyAmount:
            TextView

    private lateinit var txtRewardResult:
            TextView

    private lateinit var txtRewardInventory:
            TextView

    private lateinit var btnOpenCase:
            Button

    private lateinit var btnAddDemoKey:
            Button

    private var opening = false

    private var currentAnimator:
            ObjectAnimator? = null

    private val cardWidthDp = 110
    private val cardMarginDp = 4
    private val winnerPosition = 48
    private val reelSize = 55

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(
            R.layout.fragment_mystery_box,
            container,
            false
        )

        MysteryBoxManager.initialize(
            requireContext().applicationContext
        )

        rewardScroll =
            view.findViewById(R.id.rewardScroll)

        rewardTrack =
            view.findViewById(R.id.rewardTrack)

        txtKeyAmount =
            view.findViewById(R.id.txtKeyAmount)

        txtRewardResult =
            view.findViewById(R.id.txtRewardResult)

        txtRewardInventory =
            view.findViewById(R.id.txtRewardInventory)

        btnOpenCase =
            view.findViewById(R.id.btnOpenCase)

        btnAddDemoKey =
            view.findViewById(R.id.btnAddDemoKey)

        btnOpenCase.setOnClickListener {
            openCase()
        }

        /*
         * Nút này dành cho quá trình chạy thử.
         * Có thể xóa khi hoàn thiện ứng dụng.
         */
        btnAddDemoKey.setOnClickListener {

            val preferences =
                requireContext()
                    .getSharedPreferences("TuyFoods", 0)

            val userId =
                preferences.getLong("userId", -1L)

            if (userId == -1L) {

                Toast.makeText(
                    requireContext(),
                    "Không tìm thấy tài khoản đăng nhập",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            lifecycleScope.launch {

                try {

                    val user =
                        RetrofitClient.instance
                            .addMiniGameKey(userId)

                    txtKeyAmount.text =
                        "🔑 Chìa khóa: ${user.miniGameKeys}"

                    btnOpenCase.isEnabled =
                        user.miniGameKeys > 0

                    Toast.makeText(
                        requireContext(),
                        "Đã thêm một chìa khóa demo",
                        Toast.LENGTH_SHORT
                    ).show()

                } catch (e: Exception) {

                    Toast.makeText(
                        requireContext(),
                        "Không thể thêm chìa khóa",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        showPreview()
        updateInformation()

        return view
    }

    private fun openCase() {

        if (opening) {
            return
        }

        val preferences =
            requireContext()
                .getSharedPreferences("TuyFoods", 0)

        val userId =
            preferences.getLong("userId", -1L)

        if (userId == -1L) {

            Toast.makeText(
                requireContext(),
                "Không tìm thấy tài khoản đăng nhập",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        opening = true

        btnOpenCase.isEnabled = false
        btnAddDemoKey.isEnabled = false

        txtRewardResult.text =
            "Đang kiểm tra chìa khóa..."

        txtRewardResult.setTextColor(
            Color.WHITE
        )

        lifecycleScope.launch {

            try {

                // Kiểm tra số chìa khóa từ DB
                val user =
                    RetrofitClient.instance
                        .getUser(userId)

                if (user.miniGameKeys <= 0) {

                    Toast.makeText(
                        requireContext(),
                        "Bạn đã hết chìa khóa!",
                        Toast.LENGTH_SHORT
                    ).show()

                    opening = false

                    btnOpenCase.isEnabled = false
                    btnAddDemoKey.isEnabled = true

                    txtRewardResult.text =
                        "Bạn đã hết chìa khóa."

                    return@launch
                }

                // Còn chìa khóa → gọi API mở game
                txtRewardResult.text =
                    "Đang mở hòm..."

                val result =
                    RetrofitClient.instance.playMiniGame(
                        userId = userId,
                        gameId = 1L
                    )

                val reward =
                    RewardCatalog.findById(
                        result.rewardCode ?: "nothing"
                    )

                if (reward != null) {

                    createReelAndAnimate(
                        reward
                    )
                }

                updateKeyText()

            } catch (e: Exception) {

                opening = false

                btnOpenCase.isEnabled = false
                btnAddDemoKey.isEnabled = true

                Toast.makeText(
                    requireContext(),
                    "Không thể mở hòm: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()

                updateKeyText()
            }
        }
    }

    private fun createReelAndAnimate(
        winner: MiniGameReward
    ) {

        rewardTrack.removeAllViews()

        val reel =
            MutableList(reelSize) {
                RewardCatalog.randomReward()
            }

        // Đặt phần thưởng thắng ở vị trí cố định
        reel[winnerPosition] = winner

        reel.forEach { reward ->
            rewardTrack.addView(
                createRewardCard(reward)
            )
        }

        rewardScroll.post {

            val cardWidth =
                dp(cardWidthDp)

            val margin =
                dp(cardMarginDp)

            val cardStep =
                cardWidth + margin * 2

            /*
             * Padding giúp thẻ đầu và thẻ cuối
             * có thể nằm chính giữa màn hình.
             */
            val sidePadding =
                (
                        rewardScroll.width / 2 -
                                cardWidth / 2
                        ).coerceAtLeast(0)

            rewardTrack.setPadding(
                sidePadding,
                0,
                sidePadding,
                0
            )

            rewardScroll.scrollTo(0, 0)

            val targetScroll =
                winnerPosition * cardStep +
                        margin

            startAnimation(
                targetScroll = targetScroll,
                cardStep = cardStep,
                winner = winner
            )
        }
    }

    private fun startAnimation(
        targetScroll: Int,
        cardStep: Int,
        winner: MiniGameReward
    ) {

        var previousCard = -1

        currentAnimator =
            ObjectAnimator.ofInt(
                rewardScroll,
                "scrollX",
                0,
                targetScroll
            ).apply {

                duration = 6500L

                interpolator =
                    DecelerateInterpolator(2.2f)

                addUpdateListener {

                    val currentCard =
                        rewardScroll.scrollX /
                                cardStep

                    if (currentCard != previousCard) {

                        previousCard =
                            currentCard

                        rewardScroll
                            .performHapticFeedback(
                                HapticFeedbackConstants
                                    .CLOCK_TICK
                            )
                    }
                }

                addListener(
                    object :
                        AnimatorListenerAdapter() {

                        override fun onAnimationEnd(
                            animation: Animator
                        ) {
                            if (!isAdded) return

                            opening = false

                            showWinner(winner)
                            updateInformation()
                        }
                    }
                )

                start()
            }
    }

    private fun showWinner(
        winner: MiniGameReward
    ) {

        txtRewardResult.setTextColor(
            Color.parseColor(
                winner.rarity.color
            )
        )

        txtRewardResult.text =
            if (winner.id == "nothing") {

                "${winner.emoji} ${winner.name}"

            } else if (
                winner.voucherCode.isNotEmpty()
            ) {

                "${winner.emoji} Bạn nhận được " +
                        "${winner.name}\n" +
                        "Mã: ${winner.voucherCode}"

            } else {

                "${winner.emoji} Bạn nhận được " +
                        winner.name
            }

        Toast.makeText(
            requireContext(),
            winner.name,
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showPreview() {

        rewardTrack.removeAllViews()

        repeat(15) { index ->

            val reward =
                RewardCatalog.rewards[
                    index %
                            RewardCatalog.rewards.size
                ]

            rewardTrack.addView(
                createRewardCard(reward)
            )
        }
    }

    private fun createRewardCard(
        reward: MiniGameReward
    ): View {

        return TextView(requireContext()).apply {

            text =
                "${reward.emoji}\n" +
                        "${reward.name}\n" +
                        reward.rarity.displayName

            textSize = 14f
            gravity = Gravity.CENTER
            setTextColor(Color.WHITE)
            setTypeface(null, Typeface.BOLD)

            setPadding(
                dp(6),
                dp(8),
                dp(6),
                dp(8)
            )

            background =
                GradientDrawable().apply {

                    setColor(
                        Color.rgb(
                            32,
                            40,
                            55
                        )
                    )

                    cornerRadius =
                        dp(8).toFloat()

                    setStroke(
                        dp(3),
                        Color.parseColor(
                            reward.rarity.color
                        )
                    )
                }

            layoutParams =
                LinearLayout.LayoutParams(
                    dp(cardWidthDp),
                    dp(120)
                ).apply {

                    marginStart =
                        dp(cardMarginDp)

                    marginEnd =
                        dp(cardMarginDp)
                }
        }
    }

    private fun updateInformation() {

        val preferences =
            requireContext()
                .getSharedPreferences("TuyFoods", 0)

        val userId =
            preferences.getLong("userId", -1L)

        if (userId == -1L) {
            return
        }

        lifecycleScope.launch {

            try {

                val user =
                    RetrofitClient.instance
                        .getUser(userId)

                val results =
                    RetrofitClient.instance
                        .getMiniGameResults(userId)

                txtKeyAmount.text =
                    "🔑 Chìa khóa: ${user.miniGameKeys}"

                val rewardCounts =
                    results
                        .filter {
                            !it.rewardCode.isNullOrEmpty() &&
                                    it.rewardCode != "nothing"
                        }
                        .groupingBy {
                            it.rewardCode!!
                        }
                        .eachCount()

                val inventoryText =
                    rewardCounts
                        .mapNotNull { (rewardCode, amount) ->

                            val reward =
                                RewardCatalog.findById(
                                    rewardCode
                                )

                            if (reward != null) {
                                "${reward.emoji} ${reward.name} × $amount"
                            } else {
                                null
                            }
                        }

                txtRewardInventory.text =
                    if (inventoryText.isEmpty()) {
                        "Bạn chưa có phần thưởng nào."
                    } else {
                        inventoryText.joinToString("\n")
                    }

                btnOpenCase.isEnabled =
                    !opening &&
                            user.miniGameKeys > 0

                btnAddDemoKey.isEnabled =
                    !opening

            } catch (e: Exception) {

                Toast.makeText(
                    requireContext(),
                    "Không thể tải thông tin mini game",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun updateKeyText() {

        val preferences =
            requireContext()
                .getSharedPreferences("TuyFoods", 0)

        val userId =
            preferences.getLong("userId", -1L)

        if (userId == -1L) {
            txtKeyAmount.text = "🔑 Chìa khóa: 0"
            return
        }

        lifecycleScope.launch {

            try {

                val user =
                    RetrofitClient.instance
                        .getUser(userId)

                txtKeyAmount.text =
                    "🔑 Chìa khóa: ${user.miniGameKeys}"

            } catch (e: Exception) {

                Toast.makeText(
                    requireContext(),
                    "Không thể lấy số chìa khóa",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun dp(value: Int): Int {

        return (
                value *
                        resources.displayMetrics.density
                ).toInt()
    }

    override fun onDestroyView() {

        currentAnimator?.cancel()
        currentAnimator = null
        opening = false

        super.onDestroyView()
    }
}