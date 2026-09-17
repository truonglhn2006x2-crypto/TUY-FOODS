package com.example.tuyfood

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
<<<<<<< HEAD
import android.widget.ImageView
=======
>>>>>>> d31ed13d44c2f34ceb4e4c95166b592a82ce5f7c
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PopularFoodAdapter(
    private val items: List<FoodItem>,
    private val onAddClick: (FoodItem) -> Unit
) : RecyclerView.Adapter<PopularFoodAdapter.PopularViewHolder>() {

    class PopularViewHolder(view: View) : RecyclerView.ViewHolder(view) {
<<<<<<< HEAD

        val imgFood: ImageView = view.findViewById(R.id.imgFood)
=======
        val txtEmoji: TextView = view.findViewById(R.id.txtEmoji)
>>>>>>> d31ed13d44c2f34ceb4e4c95166b592a82ce5f7c
        val txtBadge: TextView = view.findViewById(R.id.txtBadge)
        val txtName: TextView = view.findViewById(R.id.txtName)
        val txtDescription: TextView = view.findViewById(R.id.txtDescription)
        val txtRating: TextView = view.findViewById(R.id.txtRating)
        val txtPrice: TextView = view.findViewById(R.id.txtPrice)
        val btnAdd: TextView = view.findViewById(R.id.btnAdd)
    }

<<<<<<< HEAD
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

        holder.txtDescription.text =
            food.description

        holder.txtPrice.text =
            "${"%,d".format(food.price)}đ"

        holder.txtRating.text =
            "⭐ ${food.rating}"

        // =========================
        // BADGE
        // =========================

        if (food.badge.isNotEmpty()) {

            holder.txtBadge.visibility =
                View.VISIBLE

            holder.txtBadge.text =
                food.badge

        } else {

            holder.txtBadge.visibility =
                View.GONE
        }

        // =========================
        // NÚT +
        // =========================

=======
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_popular_food, parent, false)
        return PopularViewHolder(view)
    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        val food = items[position]

        holder.txtEmoji.text = food.emoji
        holder.txtName.text = food.name
        holder.txtDescription.text = food.description
        holder.txtPrice.text = "${"%,d".format(food.price)}đ"
        holder.txtRating.text = "⭐ ${food.rating}"

        if (food.badge.isNotEmpty()) {
            holder.txtBadge.visibility = View.VISIBLE
            holder.txtBadge.text = food.badge
        } else {
            holder.txtBadge.visibility = View.GONE
        }

>>>>>>> d31ed13d44c2f34ceb4e4c95166b592a82ce5f7c
        holder.btnAdd.setOnClickListener {
            onAddClick(food)
        }
    }

<<<<<<< HEAD
    override fun getItemCount(): Int {
        return items.size
    }
=======
    override fun getItemCount(): Int = items.size
>>>>>>> d31ed13d44c2f34ceb4e4c95166b592a82ce5f7c
}