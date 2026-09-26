package com.example.tuyfood

import android.content.Context
import android.content.SharedPreferences

object MysteryBoxManager {

    private const val PREFS_NAME =
        "tuy_food_mystery_box"

    private const val KEY_AMOUNT =
        "key_amount"

    private const val DEFAULT_KEYS = 3

    private var preferences:
            SharedPreferences? = null

    fun initialize(context: Context) {

        if (preferences != null) {
            return
        }

        preferences =
            context.getSharedPreferences(
                PREFS_NAME,
                Context.MODE_PRIVATE
            )
    }

    fun getKeyAmount(): Int {

        return preferences?.getInt(
            KEY_AMOUNT,
            DEFAULT_KEYS
        ) ?: DEFAULT_KEYS
    }

    fun addKeys(amount: Int = 1) {

        if (amount <= 0) return

        val newAmount =
            getKeyAmount() + amount

        preferences
            ?.edit()
            ?.putInt(KEY_AMOUNT, newAmount)
            ?.apply()
    }

    fun useKey(): Boolean {

        val currentAmount =
            getKeyAmount()

        if (currentAmount <= 0) {
            return false
        }

        preferences
            ?.edit()
            ?.putInt(
                KEY_AMOUNT,
                currentAmount - 1
            )
            ?.apply()

        return true
    }

    fun saveReward(
        reward: MiniGameReward
    ) {

        /*
         * Không lưu phần thưởng
         * "Chúc may mắn lần sau".
         */
        if (reward.id == "nothing") {
            return
        }

        val storageKey =
            "reward_${reward.id}"

        val currentAmount =
            preferences?.getInt(
                storageKey,
                0
            ) ?: 0

        preferences
            ?.edit()
            ?.putInt(
                storageKey,
                currentAmount + 1
            )
            ?.apply()
    }

    fun getRewardAmount(
        rewardId: String
    ): Int {

        return preferences?.getInt(
            "reward_$rewardId",
            0
        ) ?: 0
    }

    fun getInventoryText(): String {

        val ownedRewards =
            RewardCatalog.rewards
                .filter {
                    it.id != "nothing"
                }
                .mapNotNull { reward ->

                    val amount =
                        getRewardAmount(
                            reward.id
                        )

                    if (amount > 0) {
                        "${reward.emoji} " +
                                "${reward.name} × $amount"
                    } else {
                        null
                    }
                }

        return if (ownedRewards.isEmpty()) {
            "Bạn chưa có phần thưởng nào."
        } else {
            ownedRewards.joinToString(
                separator = "\n"
            )
        }
    }
}