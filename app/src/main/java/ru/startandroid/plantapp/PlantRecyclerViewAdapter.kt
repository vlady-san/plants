package ru.startandroid.plantapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class PlantRecyclerViewAdapter(private var plants: List<Plant>,
                               private val listenerOrDelete: OnLikeOrDeleteItemClickListener,
private val listenerInfo : OnGetInfoItemClickListener) :
    RecyclerView.Adapter<PlantRecyclerViewAdapter.MyViewHolder>(), Filterable {

    private var plantsFiltered: List<Plant> = plants

    override fun getItemCount() = plantsFiltered.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent?.context).inflate(R.layout.vertical_recycler_view_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(plantsFiltered[position], listenerOrDelete, listenerInfo)
    }

    fun updateAdapter(mplants : List<Plant>)
    {
        plants = mplants
        plantsFiltered = mplants
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint.toString()
                if (charString.isEmpty()) {
                    plantsFiltered = plants
                } else {
                    val filteredList: MutableList<Plant> = ArrayList()
                    for (item in plants) {
                        if (item.title.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(item)
                        }
                    }
                    plantsFiltered = filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = plantsFiltered
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                if (results != null) {
                    plantsFiltered = results.values as ArrayList<Plant>
                    notifyDataSetChanged()
                }

            }

        }
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var image: ImageView = view.findViewById(R.id.image_plant)
        var title: TextView = view.findViewById(R.id.plant_title)
        var like : ImageButton = view.findViewById(R.id.like_button)

        fun bind(
            plant: Plant,
            listenerOrDelete: OnLikeOrDeleteItemClickListener,
            listenerInfo: OnGetInfoItemClickListener
        ) {
            itemView.setOnClickListener {
                plantsFiltered[adapterPosition].id?.let { it1 -> listenerInfo.getInfoPlant(it1) }
            }
            image.setImageResource(plant.image_little)
            title.text = plant.title
            like.setOnClickListener {
                plantsFiltered[adapterPosition].id?.let { it1 -> listenerOrDelete.onItemClick(it1) }
            }
        }

    }
}