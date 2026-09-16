package com.example.tuyfood

import kotlin.random.Random

enum class RewardRarity(
    val displayName: String,
    val color: String
) {
    COMMON("Thường", "#7A869A"),
    UNCOMMON("Ít gặp", "#3498DB"),
    RARE("Hiếm", "#9B59B6"),
    EPIC("Rất hiếm", "#E84393"),
    LEGENDARY("Đặc biệt", "#F1C40F")
}

data class MiniGameReward(
    val id: String,
    val name: String,
    val emoji: String,
    val voucherCode: String,
    val rarity: RewardRarity,
    val probability: Int
)

object RewardCatalog {

    /*
     * Tổng xác suất bằng 100%.
     */
    val rewards = listOf(

        MiniGameReward(
            id = "points_100",
            name = "100 điểm",
            emoji = "⭐",
            voucherCode = "",
            rarity = RewardRarity.COMMON,
            probability = 35
        ),

        MiniGameReward(
            id = "nothing",
            name = "Chúc may mắn lần sau",
            emoji = "😢",
            voucherCode = "",
            rarity = RewardRarity.COMMON,
            probability = 25
        ),

        MiniGameReward(
            id = "discount_10",
            name = "Giảm 10%",
            emoji = "🎟️",
            voucherCode = "TUYFOOD10",
            rarity = RewardRarity.UNCOMMON,
            probability = 20
        ),

        MiniGameReward(
            id = "discount_20k",
            name = "Giảm 20.000đ",
            emoji = "💵",
            voucherCode = "GIAM20K",
            rarity = RewardRarity.RARE,
            probability = 12
        ),

        MiniGameReward(
            id = "free_ship",
            name = "Miễn phí vận chuyển",
            emoji = "🚚",
            voucherCode = "FREESHIP",
            rarity = RewardRarity.EPIC,
            probability = 7
        ),

        MiniGameReward(
            id = "free_food",
            name = "Một món miễn phí",
            emoji = "🍔",
            voucherCode = "FREEFOOD",
            rarity = RewardRarity.LEGENDARY,
            probability = 1
        )
    )

    fun randomReward(): MiniGameReward {

        var randomNumber =
            Random.nextInt(100)

        for (reward in rewards) {

            if (randomNumber <
                reward.probability
            ) {
                return reward
            }

            randomNumber -=
                reward.probability
        }

        return rewards.first()
    }

    fun findById(
        rewardId: String
    ): MiniGameReward? {

        return rewards.find {
            it.id == rewardId
        }
    }
}