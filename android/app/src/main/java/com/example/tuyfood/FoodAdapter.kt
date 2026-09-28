package com.example.tuyfood

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.widget.ImageView

class FoodAdapter(
    private var items: List<FoodItem>,
    private val onAddClick: (FoodItem) -> Unit
) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    class FoodViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgFood: ImageView = view.findViewById(R.id.imgFood)
        val txtName: TextView = view.findViewById(R.id.txtName)
        val txtDescription: TextView = view.findViewById(R.id.txtDescription)
        val txtPrice: TextView = view.findViewById(R.id.txtPrice)
        val btnAdd: Button = view.findViewById(R.id.btnAdd)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_food, parent, false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val food = items[position]

        if (food.imageRes != 0) {
            holder.imgFood.setImageResource(food.imageRes)
        }
        holder.txtName.text = food.name
        if (food.available) {
            holder.txtDescription.text = food.description
            holder.txtPrice.text = "${"%,d".format(food.price)}đ"

            holder.btnAdd.isEnabled = true
            holder.btnAdd.alpha = 1f
            holder.btnAdd.setOnClickListener {
                onAddClick(food)
            }
        } else {
            holder.txtDescription.text = "MÓN ĂN ĐÃ HẾT"
            holder.txtPrice.text = "Xin vui lòng chọn món khác"

            holder.btnAdd.isEnabled = false
            holder.btnAdd.alpha = 0.5f
            holder.btnAdd.setOnClickListener(null)
        }
    }

    override fun getItemCount(): Int = items.size

    fun updateData(newItems: List<FoodItem>) {
        items = newItems
        notifyDataSetChanged()
    }
}
