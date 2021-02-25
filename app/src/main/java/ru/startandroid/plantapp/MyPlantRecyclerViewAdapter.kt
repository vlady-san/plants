package ru.startandroid.plantapp

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class MyPlantRecyclerViewAdapter(private var plants: List<Plant>,
                                 private var listener: OnLikeOrDeleteItemClickListener,
                                 private val getInfoListener : OnGetInfoItemClickListener) :
    RecyclerView.Adapter<MyPlantRecyclerViewAdapter.MyViewHolder>(), Filterable {

    private var plantsFiltered: List<Plant> = plants

    override fun getItemCount() = plantsFiltered.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent?.context).inflate(R.layout.my_plant_recycler_view_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(plantsFiltered[position], listener, getInfoListener)
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
        var titleFamily: TextView = view.findViewById(R.id.plant_family_title)
        var delete : ImageButton = view.findViewById(R.id.button_delete)

        fun bind(
            plant: Plant,
            listener: OnLikeOrDeleteItemClickListener,
            infoListener: OnGetInfoItemClickListener
        ) {
            image.setImageResource(plant.image_middle)
            title.text = plant.title
            titleFamily.text = plant.family
            delete.setOnClickListener {
                Log.d("My", adapterPosition.toString())
                plantsFiltered[adapterPosition].id?.let { it1 -> listener.onItemClick(it1) }
            }
            itemView.setOnClickListener {
                plantsFiltered[adapterPosition].id?.let { it1 -> getInfoListener.getInfoPlant(it1) }
            }
        }

    }
}