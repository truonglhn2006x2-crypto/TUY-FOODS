package com.example.tuyfood

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
<<<<<<< HEAD
import android.widget.ImageView
=======
>>>>>>> d31ed13d44c2f34ceb4e4c95166b592a82ce5f7c
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FoodAdapter(
    private var items: List<FoodItem>,
    private val onAddClick: (FoodItem) -> Unit
) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    class FoodViewHolder(view: View) : RecyclerView.ViewHolder(view) {
<<<<<<< HEAD

        val imgFood: ImageView =
            view.findViewById(R.id.imgFood)

        val txtName: TextView =
            view.findViewById(R.id.txtName)

        val txtDescription: TextView =
            view.findViewById(R.id.txtDescription)

        val txtPrice: TextView =
            view.findViewById(R.id.txtPrice)

        val btnAdd: Button =
            view.findViewById(R.id.btnAdd)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FoodViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.item_food,
                parent,
                false
            )

        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: FoodViewHolder,
        position: Int
    ) {

        val food = items[position]

        // ẢNH MÓN ĂN
        if (food.imageRes != 0) {
            holder.imgFood.setImageResource(food.imageRes)
        }

        // TÊN
        holder.txtName.text = food.name

        // MÔ TẢ
        holder.txtDescription.text =
            food.description

        // GIÁ
        holder.txtPrice.text =
            "${"%,d".format(food.price)}đ"

        // THÊM VÀO GIỎ
=======
        val txtEmoji: TextView = view.findViewById(R.id.txtEmoji)
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

        holder.txtEmoji.text = food.emoji
        holder.txtName.text = food.name
        holder.txtDescription.text = food.description
        holder.txtPrice.text = "${"%,d".format(food.price)}đ"

>>>>>>> d31ed13d44c2f34ceb4e4c95166b592a82ce5f7c
        holder.btnAdd.setOnClickListener {
            onAddClick(food)
        }
    }

<<<<<<< HEAD
    override fun getItemCount(): Int =
        items.size

    fun updateData(
        newItems: List<FoodItem>
    ) {
        items = newItems
        notifyDataSetChanged()
    }
}
=======
    override fun getItemCount(): Int = items.size

    fun updateData(newItems: List<FoodItem>) {
        items = newItems
        notifyDataSetChanged()
    }
}
>>>>>>> d31ed13d44c2f34ceb4e4c95166b592a82ce5f7c
