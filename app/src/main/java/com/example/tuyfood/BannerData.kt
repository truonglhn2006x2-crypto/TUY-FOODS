package com.example.tuyfood

data class BannerItem(
    val imageRes: Int,
    val badge: String,
    val title: String,
    val subtitle: String
)

object BannerData {
    val slides = listOf(
        BannerItem(
            imageRes = R.drawable.ga_ran,
            badge = "🔥 Flash Sale",
            title = "Gà Rán Giòn -30%",
            subtitle = "Giảm ngay 30% cho đơn đầu tiên"
        ),
        BannerItem(
            imageRes = R.drawable.tra_sua_com,
            badge = "🎉 Combo hời",
            title = "Trà Sữa Cốm + Bánh Tráng Nướng",
            subtitle = "Mua combo tiết kiệm hơn mua lẻ"
        ),
        BannerItem(
            imageRes = R.drawable.chan_ga_sot_thai,
            badge = "🏆 Bán chạy #1",
            title = "Chân Gà Sốt Thái",
            subtitle = "Món hot nhất tuần này"
        ),
        BannerItem(
            imageRes = R.drawable.sinh_to_xoai,
            badge = "🎁 Ưu đãi mới",
            title = "Sinh Tố Xoài Mát Lạnh",
            subtitle = "Giải nhiệt ngày hè, giảm 10%"
        )
    )
}