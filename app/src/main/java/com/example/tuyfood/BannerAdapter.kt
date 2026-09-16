package com.example.tuyfood

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BannerAdapter(
    private val items: List<BannerItem>
) : RecyclerView.Adapter<BannerAdapter.BannerViewHolder>() {

    class BannerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgBanner: ImageView = view.findViewById(R.id.imgBanner)
        val txtBadge: TextView = view.findViewById(R.id.txtBadge)
        val txtBannerTitle: TextView = view.findViewById(R.id.txtBannerTitle)
        val txtBannerSubtitle: TextView = view.findViewById(R.id.txtBannerSubtitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_banner, parent, false)
        return BannerViewHolder(view)
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        val banner = items[position % items.size]
        holder.imgBanner.setImageResource(banner.imageRes)
        holder.txtBadge.text = banner.badge
        holder.txtBannerTitle.text = banner.title
        holder.txtBannerSubtitle.text = banner.subtitle
    }

    // Trả về số lớn để tạo cảm giác cuộn vô hạn 2 chiều (loop qua modulo)
    override fun getItemCount(): Int = if (items.isEmpty()) 0 else Int.MAX_VALUE

    fun getRealPosition(position: Int): Int = position % items.size
}