package com.example.tuyfood

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView

class HomeFragment : Fragment() {

    // Top món bán chạy nhất, lấy từ nguồn dữ liệu chung FoodData
    private val topRanking = FoodData.getTopSelling(5)

    // =========================
    // BANNER TỰ TRƯỢT
    // =========================
    private val bannerHandler = Handler(Looper.getMainLooper())
    private var bannerPosition = 0
    private lateinit var recyclerBanner: RecyclerView
    private lateinit var dotsContainer: LinearLayout
    private lateinit var bannerAdapter: BannerAdapter

    private val bannerAutoScroll = object : Runnable {
        override fun run() {
            bannerPosition++
            recyclerBanner.smoothScrollToPosition(bannerPosition)
            bannerHandler.postDelayed(this, 3000)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(
            R.layout.fragment_home,
            container,
            false
        )

        setupBanner(view)

        // SEARCH BAR THẬT: gõ chữ, bấm nút tìm kiếm trên bàn phím -> mở Menu và lọc luôn
        val edtHomeSearch = view.findViewById<EditText>(R.id.edtHomeSearch)
        edtHomeSearch.setOnEditorActionListener { textView, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val keyword = textView.text.toString().trim()
                openMenuWithQuery(keyword)
                true
            } else {
                false
            }
        }

        // Bấm icon 🔍 cũng mở Menu (dùng khi chưa gõ gì, giữ hành vi cũ)
        view.findViewById<View>(R.id.iconSearch).setOnClickListener {
            openMenuWithQuery(edtHomeSearch.text.toString().trim())
        }

        // CART ICON -> mở giỏ hàng
        view.findViewById<View>(R.id.btnCartIcon).setOnClickListener {
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, CartFragment())
                .addToBackStack(null)
                .commit()
        }

        // DANH MỤC (6 danh mục mới)
        view.findViewById<View>(R.id.btnCatNuong).setOnClickListener {
            openMenu(FoodData.CAT_NUONG)
        }

        view.findViewById<View>(R.id.btnCatChien).setOnClickListener {
            openMenu(FoodData.CAT_CHIEN)
        }

        view.findViewById<View>(R.id.btnCatTron).setOnClickListener {
            openMenu(FoodData.CAT_TRON)
        }

        view.findViewById<View>(R.id.btnCatNoNhe).setOnClickListener {
            openMenu(FoodData.CAT_NO_NHE)
        }

        view.findViewById<View>(R.id.btnCatSinhTo).setOnClickListener {
            openMenu(FoodData.CAT_SINH_TO)
        }

        view.findViewById<View>(R.id.btnCatTra).setOnClickListener {
            openMenu(FoodData.CAT_TRA)
        }

        view.findViewById<View>(R.id.btnCustomFood).setOnClickListener {
            openCustomFood()
        }

        view.findViewById<View>(R.id.btnSeeAllCategory).setOnClickListener {
            openMenu(null)
        }

        // "Xem tất cả" cạnh Bảng xếp hạng -> mở bảng xếp hạng đầy đủ (36 món)
        view.findViewById<View>(R.id.btnMenu).setOnClickListener {
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, RankingFragment())
                .addToBackStack(null)
                .commit()
        }

        // BẢNG XẾP HẠNG: hiện ngay trên trang chủ, không cần bấm vào đâu
        val recyclerPopular = view.findViewById<RecyclerView>(R.id.recyclerPopular)
        recyclerPopular.layoutManager = LinearLayoutManager(requireContext())
        recyclerPopular.adapter = RankingAdapter(topRanking)

        return view
    }

    // =========================
    // THIẾT LẬP BANNER TỰ TRƯỢT
    // =========================
    private fun setupBanner(view: View) {
        recyclerBanner = view.findViewById(R.id.recyclerBanner)
        dotsContainer = view.findViewById(R.id.dotsContainer)

        val slides = BannerData.slides
        bannerAdapter = BannerAdapter(slides)

        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerBanner.layoutManager = layoutManager
        recyclerBanner.adapter = bannerAdapter

        // Bắt đầu ở giữa dải số ảo để có thể vuốt lùi ngay từ đầu
        bannerPosition = Int.MAX_VALUE / 2 - (Int.MAX_VALUE / 2) % slides.size
        layoutManager.scrollToPosition(bannerPosition)

        // Snap để mỗi lần trượt dừng đúng 1 banner
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(recyclerBanner)

        // Tạo chấm chỉ báo
        setupDots(slides.size)
        updateDots(bannerAdapter.getRealPosition(bannerPosition))

        // Cập nhật chấm chỉ báo khi người dùng tự vuốt tay
        recyclerBanner.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(rv: RecyclerView, newState: Int) {
                super.onScrollStateChanged(rv, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val snapView = snapHelper.findSnapView(layoutManager) ?: return
                    val pos = layoutManager.getPosition(snapView)
                    bannerPosition = pos
                    updateDots(bannerAdapter.getRealPosition(pos))
                }
            }
        })
    }

    private fun setupDots(count: Int) {
        dotsContainer.removeAllViews()
        for (i in 0 until count) {
            val dot = ImageView(requireContext())
            val size = (8 * resources.displayMetrics.density).toInt()
            val params = LinearLayout.LayoutParams(size, size)
            params.marginStart = (4 * resources.displayMetrics.density).toInt()
            params.marginEnd = (4 * resources.displayMetrics.density).toInt()
            dot.layoutParams = params
            dot.setImageResource(R.drawable.dot_inactive)
            dot.tag = "dot_$i"
            dotsContainer.addView(dot)
        }
    }

    private fun updateDots(activeIndex: Int) {
        for (i in 0 until dotsContainer.childCount) {
            val dot = dotsContainer.getChildAt(i) as ImageView
            dot.setImageResource(if (i == activeIndex) R.drawable.dot_active else R.drawable.dot_inactive)
        }
    }

    override fun onResume() {
        super.onResume()
        bannerHandler.postDelayed(bannerAutoScroll, 3000)
    }

    override fun onPause() {
        super.onPause()
        bannerHandler.removeCallbacks(bannerAutoScroll)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bannerHandler.removeCallbacks(bannerAutoScroll)
    }

    private fun openMenu(category: String?) {
        val menuFragment = MenuFragment()

        if (category != null) {
            val bundle = Bundle()
            bundle.putString("category", category)
            menuFragment.arguments = bundle
        }

        parentFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, menuFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun openMenuWithQuery(keyword: String) {
        val menuFragment = MenuFragment()

        if (keyword.isNotBlank()) {
            val bundle = Bundle()
            bundle.putString("query", keyword)
            menuFragment.arguments = bundle
        }

        parentFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, menuFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun openCustomFood() {
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, CustomFoodFragment())
            .addToBackStack(null)
            .commit()
    }
}