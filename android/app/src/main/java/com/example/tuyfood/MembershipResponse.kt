package com.example.tuyfood

data class MembershipResponse(
    val userId: Long,
    val name: String,
    val level: String,
    val totalCompletedSpend: Double,
    val nextLevel: String?,
    val remainingAmount: Double,
    val progressPercent: Int,
    val unlockedFeatures: List<String>,
    val lockedFeatures: List<String>
)

