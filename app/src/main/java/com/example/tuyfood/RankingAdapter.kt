package com.example.tuyfood

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RankingAdapter(
    private val items: List<FoodItem>
) : RecyclerView.Adapter<RankingAdapter.RankingViewHolder>() {

    class RankingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtRank: TextView = view.findViewById(R.id.txtRank)
        val imgRankFood: ImageView = view.findViewById(R.id.imgRankFood)
        val txtRankName: TextView = view.findViewById(R.id.txtRankName)
        val txtRankSold: TextView = view.findViewById(R.id.txtRankSold)
        val txtRankPrice: TextView = view.findViewById(R.id.txtRankPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_ranking, parent, false)
        return RankingViewHolder(view)
    }

    override fun onBindViewHolder(holder: RankingViewHolder, position: Int) {
        val food = items[position]

        holder.txtRank.text = "${position + 1}"
        if (food.imageRes != 0) {
            holder.imgRankFood.setImageResource(food.imageRes)
        }
        holder.txtRankName.text = food.name
        holder.txtRankSold.text = "Đã bán ${"%,d".format(food.soldCount)}"
        holder.txtRankPrice.text = "${"%,d".format(food.price)}đ"
    }

    override fun getItemCount(): Int = items.size
}