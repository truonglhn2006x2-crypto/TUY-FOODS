package com.example.tuyfood

/**
 * Nguồn dữ liệu món ăn duy nhất cho toàn app (36 món, 6 danh mục mới).
 * MenuFragment và HomeFragment đều lấy dữ liệu từ đây để tránh trùng lặp.
 */
object FoodData {

    const val CAT_NUONG = "Món nướng & Xiên que"
    const val CAT_CHIEN = "Món chiên & Rán giòn"
    const val CAT_TRON = "Món trộn & Chua cay giải ngấy"
    const val CAT_NO_NHE = "Món no nhẹ & Tinh bột"
    const val CAT_SINH_TO = "Đồ uống - Sinh tố & Nước mát"
    const val CAT_TRA = "Đồ uống - Trà & Đá xay"

    val allFoods = listOf(
        FoodItem(id = 1,  name = "Bánh bao chay",        description = "Bánh bao hấp nhân chay nóng hổi",      price = 10000, emoji = "🥟", imageRes = R.drawable.banh_bao_chay,    rating = 4.6, soldCount = 300,  category = CAT_NO_NHE),
        FoodItem(id = 2,  name = "Bánh mì bơ tỏi",       description = "Bánh mì nướng phô mai bơ tỏi thơm",    price = 15000, emoji = "🥖", imageRes = R.drawable.banh_mi_bo_toi,   rating = 4.7, soldCount = 280,  category = CAT_NO_NHE),
        FoodItem(id = 3,  name = "Bánh tráng cuốn",      description = "Cuốn trứng, khô bò, rau răm",           price = 25000, emoji = "🌯", imageRes = R.drawable.banh_trang_cuon,  rating = 4.5, soldCount = 260,  category = CAT_TRON),
        FoodItem(id = 4,  name = "Bánh tráng nướng",     description = "Trứng, xúc xích, phô mai, tương ớt",    price = 25000, emoji = "🫓", imageRes = R.drawable.banh_trang_nuong, rating = 4.8, soldCount = 470,  category = CAT_NUONG),
        FoodItem(id = 5,  name = "Bò nướng lá lốt",      description = "Thịt bò cuốn lá lốt nướng than",        price = 25000, emoji = "🥩", imageRes = R.drawable.bo_nuong_la_lot,  rating = 4.7, soldCount = 310,  category = CAT_NUONG),
        FoodItem(id = 6,  name = "Cá viên xiên",         description = "10 viên chiên giòn, sốt me/tương ớt",   price = 20000, emoji = "🍢", imageRes = R.drawable.ca_vien_xien,     rating = 4.6, soldCount = 420,  category = CAT_NUONG),
        FoodItem(id = 7,  name = "Chân gà sốt Thái",     description = "Chua cay đậm vị kiểu Thái",             price = 45000, emoji = "🍗", imageRes = R.drawable.chan_ga_sot_thai, rating = 4.7, soldCount = 9999, category = CAT_TRON, badge = "Bán chạy"),
        FoodItem(id = 8,  name = "Dồi sụn nướng",        description = "Giòn sần sật, chấm tương ớt",           price = 10000, emoji = "🍢", imageRes = R.drawable.doi_sun,          rating = 4.5, soldCount = 240,  category = CAT_NUONG),
        FoodItem(id = 9,  name = "Gà rán giòn",          description = "Gà chiên giòn tan, ăn kèm tương ớt",    price = 25000, emoji = "🍗", imageRes = R.drawable.ga_ran,           rating = 4.8, soldCount = 512,  category = CAT_CHIEN),
        FoodItem(id = 10, name = "Há cảo chiên",         description = "Vỏ giòn, nhân tôm thịt đậm đà",         price = 30000, emoji = "🥟", imageRes = R.drawable.ha_cao_chien,     rating = 4.5, soldCount = 270,  category = CAT_CHIEN),
        FoodItem(id = 11, name = "Khoai tây chiên",      description = "Giòn rụm, ăn kèm tương cà",             price = 30000, emoji = "🍟", imageRes = R.drawable.khoai_tay_chien,  rating = 4.6, soldCount = 380,  category = CAT_CHIEN),
        FoodItem(id = 12, name = "Khô gà bã mía",        description = "Khô gà xé sợi trộn bã mía giòn tan",    price = 22000, emoji = "🍗", imageRes = R.drawable.kho_ga_ba_mia,    rating = 4.4, soldCount = 220,  category = CAT_TRON),
        FoodItem(id = 13, name = "Lạp xưởng nướng",      description = "Lạp xưởng nướng thơm, chấm bột ớt",     price = 30000, emoji = "🌭", imageRes = R.drawable.lap_xuong_nuong,  rating = 4.6, soldCount = 300,  category = CAT_NUONG),
        FoodItem(id = 14, name = "Matcha đá xay",        description = "Trà xanh Nhật xay đá mát lạnh",         price = 45000, emoji = "🍵", imageRes = R.drawable.matcha_da_xay,    rating = 4.5, soldCount = 200,  category = CAT_TRA),
        FoodItem(id = 15, name = "Mì Indomie trứng ốp",  description = "Mì trộn cay Indonesia, trứng ốp la",    price = 15000, emoji = "🍜", imageRes = R.drawable.mi_indomi,        rating = 4.7, soldCount = 330,  category = CAT_NO_NHE),
        FoodItem(id = 16, name = "Nem chua rán",         description = "Nem rán giòn",                          price = 40000, emoji = "🥟", imageRes = R.drawable.nem_ran,          rating = 4.6, soldCount = 1596, category = CAT_CHIEN, badge = "Bán chạy"),
        FoodItem(id = 17, name = "Nước sâm",             description = "Nước sâm pha mía",                      price = 12000, emoji = "🥤", imageRes = R.drawable.nuoc_sam,         rating = 4.5, soldCount = 350,  category = CAT_SINH_TO),
        FoodItem(id = 18, name = "Ngô chiên bơ",         description = "Ngô chiên giòn phủ bơ béo ngậy",        price = 20000, emoji = "🌽", imageRes = R.drawable.ngo_chien,        rating = 4.4, soldCount = 230,  category = CAT_CHIEN),
        FoodItem(id = 19, name = "Ngô xào tôm khô",      description = "Ngô xào bơ tôm khô hành phi",           price = 15000, emoji = "🌽", imageRes = R.drawable.ngo_xao,          rating = 4.5, soldCount = 250,  category = CAT_TRON),
        FoodItem(id = 20, name = "Phô mai que",          description = "Phô mai kéo sợi chiên giòn",            price = 30000, emoji = "🧀", imageRes = R.drawable.pho_mai_que,      rating = 4.8, soldCount = 400,  category = CAT_CHIEN),
        FoodItem(id = 21, name = "Sinh tố rau má",       description = "Rau má xay sánh mịn, mát lành",         price = 20000, emoji = "🥤", imageRes = R.drawable.sinh_to_rau_ma,   rating = 4.4, soldCount = 180,  category = CAT_SINH_TO),
        FoodItem(id = 22, name = "Sinh tố bơ",           description = "Bơ sáp xay sữa béo ngậy",               price = 40000, emoji = "🥑", imageRes = R.drawable.sinh_to_bo,       rating = 4.7, soldCount = 320,  category = CAT_SINH_TO),
        FoodItem(id = 23, name = "Sinh tố dừa",          description = "Dừa xiêm xay mát lạnh",                 price = 40000, emoji = "🥥", imageRes = R.drawable.sinh_to_dua,      rating = 4.5, soldCount = 260,  category = CAT_SINH_TO),
        FoodItem(id = 24, name = "Sinh tố dứa",          description = "Dứa (thơm) xay chua ngọt tự nhiên",     price = 40000, emoji = "🍍", imageRes = R.drawable.sinh_to_thom,     rating = 4.4, soldCount = 210,  category = CAT_SINH_TO),
        FoodItem(id = 25, name = "Sinh tố mãng cầu",     description = "Mãng cầu xiêm xay thơm béo",            price = 40000, emoji = "🍈", imageRes = R.drawable.sinh_to_mang_cau, rating = 4.6, soldCount = 200,  category = CAT_SINH_TO),
        FoodItem(id = 26, name = "Sinh tố xoài",         description = "Xoài chín xay sánh mịn",                price = 40000, emoji = "🥭", imageRes = R.drawable.sinh_to_xoai,     rating = 4.7, soldCount = 340,  category = CAT_SINH_TO),
        FoodItem(id = 27, name = "Sữa tươi chiên",       description = "Vỏ giòn, nhân sữa béo tan chảy",        price = 45000, emoji = "🥛", imageRes = R.drawable.sua_tuoi_chien,   rating = 4.6, soldCount = 250,  category = CAT_CHIEN),
        FoodItem(id = 28, name = "Takoyaki",             description = "Bánh bạch tuộc Nhật, sốt mayo",         price = 30000, emoji = "🐙", imageRes = R.drawable.takoyaki,         rating = 4.8, soldCount = 280,  category = CAT_NO_NHE),
        FoodItem(id = 29, name = "Tokbokki",             description = "Bánh gạo cay Hàn Quốc",                 price = 20000, emoji = "🍥", imageRes = R.drawable.tokbokki,         rating = 4.7, soldCount = 300,  category = CAT_NO_NHE),
        FoodItem(id = 30, name = "Trà chanh",            description = "Trà chanh mật ong giải khát",           price = 15000, emoji = "🍋", imageRes = R.drawable.tra_chanh,        rating = 4.5, soldCount = 380,  category = CAT_TRA),
        FoodItem(id = 31, name = "Trà hoa quả",          description = "Trà trái cây tổng hợp tươi mát",        price = 45000, emoji = "🍹", imageRes = R.drawable.tra_hoa_qua,      rating = 4.6, soldCount = 270,  category = CAT_TRA),
        FoodItem(id = 32, name = "Trà sữa cốm",          description = "Trà sữa vị cốm đặc trưng Hà Nội",       price = 55000, emoji = "🧋", imageRes = R.drawable.tra_sua_com,      rating = 4.5, soldCount = 999,  category = CAT_TRA),
        FoodItem(id = 33, name = "Trà tắc",              description = "Trà quất chua ngọt truyền thống",       price = 15000, emoji = "🍊", imageRes = R.drawable.tra_tac,          rating = 4.6, soldCount = 310,  category = CAT_TRA),
        FoodItem(id = 34, name = "Trà Thái xanh/đỏ",     description = "Trà Thái sữa đặc thơm béo",             price = 22000, emoji = "🧋", imageRes = R.drawable.tra_thai,         rating = 4.7, soldCount = 350,  category = CAT_TRA),
        FoodItem(id = 35, name = "Xoài dầm",             description = "Xoài xanh dầm muối ớt chua cay",        price = 20000, emoji = "🥭", imageRes = R.drawable.xoai_dam,         rating = 4.5, soldCount = 260,  category = CAT_TRON),
        FoodItem(id = 36, name = "Xúc xích nướng",       description = "Xúc xích nướng than thơm phức",         price = 10000, emoji = "🌭", imageRes = R.drawable.xuc_xich,         rating = 4.6, soldCount = 400,  category = CAT_NUONG)
    )

    /** Danh sách 6 danh mục mới, dùng để dựng các nút danh mục ở Home & Menu */
    val categories = listOf(
        Category(CAT_NUONG,   "🍢"),
        Category(CAT_CHIEN,   "🍗"),
        Category(CAT_TRON,    "🥗"),
        Category(CAT_NO_NHE,  "🍞"),
        Category(CAT_SINH_TO, "🥤"),
        Category(CAT_TRA,     "🍵")
    )

    /** Top N món bán chạy nhất, dùng cho mục "Món phổ biến" ở trang chủ */
    fun getTopSelling(n: Int = 5): List<FoodItem> =
        allFoods.sortedByDescending { it.soldCount }.take(n)
}

data class Category(
    val name: String,
    val emoji: String
)