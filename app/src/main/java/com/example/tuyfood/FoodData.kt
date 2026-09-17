package com.example.tuyfood

object FoodData {

    // =========================
    // CATEGORIES
    // =========================

    const val CAT_NUONG = "Món nướng & Xiên que"
    const val CAT_CHIEN = "Món chiên & Rán giòn"
    const val CAT_TRON = "Món trộn & Chua cay giải ngấy"
    const val CAT_NO_NHE = "Món no nhẹ & Tinh bột"
    const val CAT_SINH_TO = "Đồ uống - Sinh tố & Nước mát"
    const val CAT_TRA = "Đồ uống - Trà & Đá xay"


    // =========================
    // 36 FOODS
    // =========================

    val allFoods = listOf(

        FoodItem(
            id = 1,
            name = "Bánh bao chay",
            price = 10000,
            emoji = "🥟",
            description = "Bánh bao hấp nhân chay nóng hổi",
            category = CAT_NO_NHE,
            rating = 4.6,
            soldCount = 300,
            imageRes = R.drawable.banh_bao_chay
        ),

        FoodItem(
            id = 2,
            name = "Bánh mì bơ tỏi",
            price = 15000,
            emoji = "🥖",
            description = "Bánh mì nướng phô mai bơ tỏi thơm",
            category = CAT_NO_NHE,
            rating = 4.7,
            soldCount = 280,
            imageRes = R.drawable.banh_mi_bo_toi
        ),

        FoodItem(
            id = 3,
            name = "Bánh tráng cuốn",
            price = 25000,
            emoji = "🌯",
            description = "Cuốn trứng, khô bò, rau răm",
            category = CAT_TRON,
            rating = 4.5,
            soldCount = 260,
            imageRes = R.drawable.banh_trang_cuon
        ),

        FoodItem(
            id = 4,
            name = "Bánh tráng nướng",
            price = 25000,
            emoji = "🍕",
            description = "Trứng, xúc xích, phô mai, tương ớt",
            category = CAT_CHIEN,
            rating = 4.8,
            soldCount = 470,
            imageRes = R.drawable.banh_trang_nuong
        ),

        FoodItem(
            id = 5,
            name = "Bò nướng lá lốt",
            price = 25000,
            emoji = "🥩",
            description = "Thịt bò cuốn lá lốt nướng than",
            category = CAT_NUONG,
            rating = 4.7,
            soldCount = 310,
            imageRes = R.drawable.bo_nuong_la_lot
        ),

        FoodItem(
            id = 6,
            name = "Cá viên xiên",
            price = 20000,
            emoji = "🍢",
            description = "10 viên chiên giòn, sốt me/tương ớt",
            category = CAT_NUONG,
            rating = 4.6,
            soldCount = 420,
            imageRes = R.drawable.ca_vien_xien
        ),

        FoodItem(
            id = 7,
            name = "Chân gà sốt Thái",
            price = 45000,
            emoji = "🍗",
            description = "Chua cay đậm vị kiểu Thái",
            category = CAT_TRON,
            rating = 4.7,
            soldCount = 9999,
            imageRes = R.drawable.chan_ga_sot_thai,
            badge = "Bán chạy"
        ),

        FoodItem(
            id = 8,
            name = "Dồi sụn nướng",
            price = 10000,
            emoji = "🌭",
            description = "Giòn sần sật, chấm tương ớt",
            category = CAT_NUONG,
            rating = 4.5,
            soldCount = 240,
            imageRes = R.drawable.doi_sun
        ),

        FoodItem(
            id = 9,
            name = "Gà rán giòn",
            price = 25000,
            emoji = "🍗",
            description = "Gà chiên giòn tan, ăn kèm tương ớt",
            category = CAT_CHIEN,
            rating = 4.8,
            soldCount = 512,
            imageRes = R.drawable.ga_ran,
            badge = "HOT"
        ),

        FoodItem(
            id = 10,
            name = "Há cảo chiên",
            price = 30000,
            emoji = "🥟",
            description = "Vỏ giòn, nhân tôm thịt đậm đà",
            category = CAT_CHIEN,
            rating = 4.5,
            soldCount = 270,
            imageRes = R.drawable.ha_cao_chien
        ),

        FoodItem(
            id = 11,
            name = "Khoai tây chiên",
            price = 30000,
            emoji = "🍟",
            description = "Giòn rụm, ăn kèm tương cà",
            category = CAT_CHIEN,
            rating = 4.6,
            soldCount = 380,
            imageRes = R.drawable.khoai_tay_chien
        ),

        FoodItem(
            id = 12,
            name = "Khô gà bã mía",
            price = 22000,
            emoji = "🍗",
            description = "Khô gà xé sợi trộn bã mía giòn tan",
            category = CAT_TRON,
            rating = 4.4,
            soldCount = 220,
            imageRes = R.drawable.kho_ga_ba_mia
        ),

        FoodItem(
            id = 13,
            name = "Lạp xưởng nướng",
            price = 15000,
            emoji = "🌭",
            description = "Lạp xưởng nướng thơm, chấm bột ớt",
            category = CAT_NUONG,
            rating = 4.6,
            soldCount = 300,
            imageRes = R.drawable.lap_xuong_nuong
        ),

        FoodItem(
            id = 14,
            name = "Matcha đá xay",
            price = 45000,
            emoji = "🍵",
            description = "Trà xanh Nhật xay đá mát lạnh",
            category = CAT_TRA,
            rating = 4.5,
            soldCount = 200,
            imageRes = R.drawable.matcha_da_xay
        ),

        FoodItem(
            id = 15,
            name = "Mì Indomie trứng ốp",
            price = 15000,
            emoji = "🍜",
            description = "Mì trộn cay Indonesia, trứng ốp la",
            category = CAT_NO_NHE,
            rating = 4.7,
            soldCount = 330,
            imageRes = R.drawable.mi_indomi
        ),

        FoodItem(
            id = 16,
            name = "Nem chua rán",
            price = 40000,
            emoji = "🥟",
            description = "Nem rán giòn",
            category = CAT_CHIEN,
            rating = 4.6,
            soldCount = 1596,
            imageRes = R.drawable.nem_ran,
            badge = "Bán chạy"
        ),

        FoodItem(
            id = 17,
            name = "Nước sâm",
            price = 12000,
            emoji = "🥤",
            description = "Nước sâm pha mía",
            category = CAT_SINH_TO,
            rating = 4.5,
            soldCount = 350,
            imageRes = R.drawable.nuoc_sam
        ),

        FoodItem(
            id = 18,
            name = "Ngô chiên bơ",
            price = 20000,
            emoji = "🌽",
            description = "Ngô chiên giòn phủ bơ béo ngậy",
            category = CAT_CHIEN,
            rating = 4.4,
            soldCount = 230,
            imageRes = R.drawable.ngo_chien
        ),

        FoodItem(
            id = 19,
            name = "Ngô xào tôm khô",
            price = 15000,
            emoji = "🌽",
            description = "Ngô xào bơ tôm khô hành phi",
            category = CAT_TRON,
            rating = 4.5,
            soldCount = 250,
            imageRes = R.drawable.ngo_xao
        ),

        FoodItem(
            id = 20,
            name = "Phô mai que",
            price = 30000,
            emoji = "🧀",
            description = "Phô mai kéo sợi chiên giòn",
            category = CAT_CHIEN,
            rating = 4.8,
            soldCount = 400,
            imageRes = R.drawable.pho_mai_que,
            badge = "HOT"
        ),

        FoodItem(
            id = 21,
            name = "Sinh tố rau má",
            price = 20000,
            emoji = "🥤",
            description = "Rau má xay sánh mịn, mát lành",
            category = CAT_SINH_TO,
            rating = 4.4,
            soldCount = 180,
            imageRes = R.drawable.sinh_to_rau_ma
        ),

        FoodItem(
            id = 22,
            name = "Sinh tố bơ",
            price = 40000,
            emoji = "🥑",
            description = "Bơ sáp xay sữa béo ngậy",
            category = CAT_SINH_TO,
            rating = 4.7,
            soldCount = 320,
            imageRes = R.drawable.sinh_to_bo
        ),

        FoodItem(
            id = 23,
            name = "Sinh tố dừa",
            price = 40000,
            emoji = "🥥",
            description = "Dừa xiêm xay mát lạnh",
            category = CAT_SINH_TO,
            rating = 4.5,
            soldCount = 260,
            imageRes = R.drawable.sinh_to_dua
        ),

        FoodItem(
            id = 24,
            name = "Sinh tố dứa",
            price = 40000,
            emoji = "🍍",
            description = "Dứa (thơm) xay chua ngọt tự nhiên",
            category = CAT_SINH_TO,
            rating = 4.4,
            soldCount = 210,
            imageRes = R.drawable.sinh_to_thom
        ),

        FoodItem(
            id = 25,
            name = "Sinh tố mãng cầu",
            price = 40000,
            emoji = "🥤",
            description = "Mãng cầu xiêm xay thơm béo",
            category = CAT_SINH_TO,
            rating = 4.6,
            soldCount = 200,
            imageRes = R.drawable.sinh_to_mang_cau
        ),

        FoodItem(
            id = 26,
            name = "Sinh tố xoài",
            price = 40000,
            emoji = "🥭",
            description = "Xoài chín xay sánh mịn",
            category = CAT_SINH_TO,
            rating = 4.7,
            soldCount = 340,
            imageRes = R.drawable.sinh_to_xoai
        ),

        FoodItem(
            id = 27,
            name = "Sữa tươi chiên",
            price = 45000,
            emoji = "🥛",
            description = "Vỏ giòn, nhân sữa béo tan chảy",
            category = CAT_CHIEN,
            rating = 4.6,
            soldCount = 250,
            imageRes = R.drawable.sua_tuoi_chien
        ),

        FoodItem(
            id = 28,
            name = "Takoyaki",
            price = 30000,
            emoji = "🐙",
            description = "Bánh bạch tuộc Nhật, sốt mayo",
            category = CAT_CHIEN,
            rating = 4.8,
            soldCount = 280,
            imageRes = R.drawable.takoyaki
        ),

        FoodItem(
            id = 29,
            name = "Tokbokki",
            price = 20000,
            emoji = "🍜",
            description = "Bánh gạo cay Hàn Quốc",
            category = CAT_TRON,
            rating = 4.7,
            soldCount = 300,
            imageRes = R.drawable.tokbokki
        ),

        FoodItem(
            id = 30,
            name = "Trà chanh",
            price = 15000,
            emoji = "🍋",
            description = "Trà chanh mật ong giải khát",
            category = CAT_TRA,
            rating = 4.5,
            soldCount = 380,
            imageRes = R.drawable.tra_chanh
        ),

        FoodItem(
            id = 31,
            name = "Trà hoa quả",
            price = 45000,
            emoji = "🍹",
            description = "Trà trái cây tổng hợp tươi mát",
            category = CAT_TRA,
            rating = 4.6,
            soldCount = 270,
            imageRes = R.drawable.tra_hoa_qua
        ),

        FoodItem(
            id = 32,
            name = "Trà sữa cốm",
            price = 55000,
            emoji = "🧋",
            description = "Trà sữa vị cốm đặc trưng Hà Nội",
            category = CAT_TRA,
            rating = 4.5,
            soldCount = 999,
            imageRes = R.drawable.tra_sua_com,
            badge = "HOT"
        ),

        FoodItem(
            id = 33,
            name = "Trà tắc",
            price = 15000,
            emoji = "🍋",
            description = "Trà quất chua ngọt truyền thống",
            category = CAT_TRA,
            rating = 4.6,
            soldCount = 310,
            imageRes = R.drawable.tra_tac
        ),

        FoodItem(
            id = 34,
            name = "Trà Thái xanh/đỏ",
            price = 22000,
            emoji = "🧋",
            description = "Trà Thái sữa đặc thơm béo",
            category = CAT_TRA,
            rating = 4.7,
            soldCount = 350,
            imageRes = R.drawable.tra_thai
        ),

        FoodItem(
            id = 35,
            name = "Xoài dầm",
            price = 20000,
            emoji = "🥭",
            description = "Xoài xanh dầm muối ớt chua cay",
            category = CAT_TRON,
            rating = 4.5,
            soldCount = 260,
            imageRes = R.drawable.xoai_dam
        ),

        FoodItem(
            id = 36,
            name = "Xúc xích nướng",
            price = 10000,
            emoji = "🌭",
            description = "Xúc xích nướng than thơm phức",
            category = CAT_NUONG,
            rating = 4.6,
            soldCount = 400,
            imageRes = R.drawable.xuc_xich
        )
    )


    // =========================
    // TOP SELLING
    // =========================

    fun getTopSelling(n: Int = 5): List<FoodItem> {
        return allFoods
            .sortedByDescending { it.soldCount }
            .take(n)
    }
    // =========================
    // FOOD OPTIONS / TOPPINGS
    // =========================

    fun getOptions(food: FoodItem): List<FoodOption> {

        return when (food.name) {

            // Chân gà sốt Thái
            "Chân gà sốt Thái" -> listOf(
                FoodOption("Chân gà thêm", 7000),
                FoodOption("Trứng non", 10000),
                FoodOption("Xoài", 5000),
                FoodOption("Cóc", 5000)
            )

            // Trà sữa cốm
            "Trà sữa cốm" -> listOf(
                FoodOption("Cốm", 5000),
                FoodOption("Hạt nổ", 5000),
                FoodOption("Chân trâu trắng", 5000)
            )

            // Trà tắc
            "Trà tắc" -> listOf(
                FoodOption("Nha đam", 5000),
                FoodOption("Chân trâu trắng", 5000),
                FoodOption("Con cá", 5000)
            )

            // Trà chanh
            "Trà chanh" -> listOf(
                FoodOption("Nha đam", 5000),
                FoodOption("Chân trâu trắng", 5000),
                FoodOption("Con cá", 5000)
            )

            // Các loại sinh tố
            "Sinh tố bơ",
            "Sinh tố mãng cầu",
            "Sinh tố xoài",
            "Sinh tố dừa",
            "Sinh tố dứa" -> listOf(
                FoodOption("Nha đam", 5000),
                FoodOption("Chân trâu trắng", 5000),
                FoodOption("Con cá", 5000)
            )

            // Trà hoa quả
            "Trà hoa quả" -> listOf(
                FoodOption("Hoa quả thêm", 5000),
                FoodOption("Chân trâu trắng", 5000)
            )

            // Mì Indomie trứng ốp
            "Mì Indomie trứng ốp" -> listOf(
                FoodOption("Trứng ốp la", 7000),
                FoodOption("Xiên bẩn", 10000),
                FoodOption("Rau muống", 3000)
            )

            // Tokbokki
            "Tokbokki" -> listOf(
                FoodOption("Cay", 0, "spicy"),
                FoodOption("Không cay", 0, "spicy"),
                FoodOption("Chả cá", 10000),
                FoodOption("Trứng luộc", 7000)
            )

            // Bánh tráng nướng
            "Bánh tráng nướng" -> listOf(
                FoodOption("Thêm phô mai", 10000)
            )

            // Gà rán
            "Gà rán giòn" -> listOf(
                FoodOption("Sốt bơ tỏi", 5000, "sauce"),
                FoodOption("Sốt cay", 5000, "sauce"),
                FoodOption("Sốt phô mai", 5000, "sauce")
            )

            // Xoài dầm
            "Xoài dầm" -> listOf(
                FoodOption("Không cay", 0, "spicy"),
                FoodOption("Cay vừa", 0, "spicy"),
                FoodOption("Siêu cay", 0, "spicy")
            )

            // Rau má
            "Sinh tố rau má" -> listOf(
                FoodOption("Đậu xanh", 5000),
                FoodOption("Sữa dừa", 5000)
            )

            // Sữa tươi chiên
            "Sữa tươi chiên" -> listOf(
                FoodOption("Socola", 10000),
                FoodOption("Dâu tây", 10000)
            )

            // Trà Thái
            "Trà Thái xanh/đỏ" -> listOf(
                FoodOption("Thái xanh", 0, "tea"),
                FoodOption("Thái đỏ", 0, "tea")
            )

            // Nước sâm
            "Nước sâm" -> listOf(
                FoodOption("Hạt chia", 5000)
            )

            // Món không có topping
            else -> emptyList()
        }
    }
}