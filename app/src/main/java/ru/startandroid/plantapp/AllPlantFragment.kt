package ru.startandroid.plantapp

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.all_plant_fragment.*

class AllPlantFragment : Fragment(), OnChooseCategoryItemClickListener,
    OnLikeOrDeleteItemClickListener, OnGetInfoItemClickListener {

    private lateinit var mDb : MyDataBase
    private lateinit var categoryDao : CategoryDao
    private lateinit var plantDao: PlantDao
    private lateinit var adapter : PlantRecyclerViewAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.all_plant_fragment,container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDb = context?.let { MyDataBase.getInstance(it) }!!
        categoryDao = mDb.getCategoryDao()
        plantDao = mDb.getPlantDao()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        vertical_recycler_view.layoutManager = LinearLayoutManager(context)
        horizontal_recycler_view.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        adapter = PlantRecyclerViewAdapter(plantDao.getAll(), this, this)
        vertical_recycler_view.adapter = adapter
        horizontal_recycler_view.adapter = CategoryRecyclerViewAdapter(categoryDao.getAll(), this)

        search_icon_button.setOnClickListener {
            edit_text_catalog.requestFocus()
            var imm =  requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(edit_text_catalog, 0)
        }

        search_cancel.setOnClickListener {
            edit_text_catalog.text.clear()
        }



        edit_text_catalog.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                search_cancel.visibility = if(s.isNotEmpty()) View.VISIBLE else View.GONE
                adapter.filter.filter(s)
            }

            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
            }
        })
    }

    override fun onItemClick(title: String) {
        adapter.updateAdapter(plantDao.getAllByFamily(title))
    }

    override fun onItemClick(id: Long) {
        var plant = plantDao.getPlantById(id)
        if(plant.is_my)
            Toast.makeText(context, "Растение уже находится в вашей коллекции", Toast.LENGTH_SHORT).show()
        else {
            plant.is_my = true
            plantDao.insert(plant)
            Toast.makeText(context, "Растение добавлено в вашу коллекцию", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getInfoPlant(id: Long) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container, PlantInfoFragment.newInstance(id))
            .addToBackStack("1")
            .commit()
    }

}
