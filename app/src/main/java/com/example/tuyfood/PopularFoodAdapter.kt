package com.example.tuyfood

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PopularFoodAdapter(
    private val items: List<FoodItem>,
    private val onAddClick: (FoodItem) -> Unit
) : RecyclerView.Adapter<PopularFoodAdapter.PopularViewHolder>() {

    class PopularViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgFood: ImageView = view.findViewById(R.id.imgFood)
        val txtBadge: TextView = view.findViewById(R.id.txtBadge)
        val txtName: TextView = view.findViewById(R.id.txtName)
        val txtDescription: TextView = view.findViewById(R.id.txtDescription)
        val txtRating: TextView = view.findViewById(R.id.txtRating)
        val txtPrice: TextView = view.findViewById(R.id.txtPrice)
        val btnAdd: TextView = view.findViewById(R.id.btnAdd)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PopularViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.item_popular_food,
                parent,
                false
            )
        return PopularViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: PopularViewHolder,
        position: Int
    ) {
        val food = items[position]

        // =========================
        // ẢNH MÓN ĂN
        // =========================
        if (food.imageRes != 0) {
            holder.imgFood.setImageResource(food.imageRes)
        }

        // =========================
        // THÔNG TIN MÓN
        // =========================
        holder.txtName.text = food.name
        holder.txtDescription.text = food.description
        holder.txtPrice.text = "${"%,d".format(food.price)}đ"
        holder.txtRating.text = "⭐ ${food.rating}"

        // =========================
        // BADGE
        // =========================
        if (food.badge.isNotEmpty()) {
            holder.txtBadge.visibility = View.VISIBLE
            holder.txtBadge.text = food.badge
        } else {
            holder.txtBadge.visibility = View.GONE
        }

        // =========================
        // NÚT +
        // =========================
        holder.btnAdd.setOnClickListener {
            onAddClick(food)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}