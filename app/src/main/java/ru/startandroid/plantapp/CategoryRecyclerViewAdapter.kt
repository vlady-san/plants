package ru.startandroid.plantapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CategoryRecyclerViewAdapter(private val category: List<Category>, private val listenerChooseCategory: OnChooseCategoryItemClickListener) :
    RecyclerView.Adapter<CategoryRecyclerViewAdapter.MyViewHolder>() {

    override fun getItemCount() = category.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent?.context).inflate(R.layout.horizontal_recycler_view_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(category[position], listenerChooseCategory)
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var image: ImageView = view.findViewById(R.id.plant_category_image)
        var title: TextView = view.findViewById(R.id.plant_category_title)

        fun bind(
            category: Category,
            listenerChooseCategory: OnChooseCategoryItemClickListener
        ) {
            image.setImageResource(category.image)
            title.text = category.title
            itemView.setOnClickListener {
                listenerChooseCategory.onItemClick(category.title)
            }
        }

    }
}