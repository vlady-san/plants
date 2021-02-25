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
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.all_plant_fragment.edit_text_catalog
import kotlinx.android.synthetic.main.all_plant_fragment.search_cancel
import kotlinx.android.synthetic.main.all_plant_fragment.search_icon_button
import kotlinx.android.synthetic.main.my_plant_fragment.*

class MyPlantFragment : Fragment(), OnLikeOrDeleteItemClickListener,
    MainFragment.OnActivityDataListener,
    OnGetInfoItemClickListener{

    private lateinit var mDb : MyDataBase
    private lateinit var categoryDao : CategoryDao
    private lateinit var plantDao: PlantDao
    private lateinit var adapter : MyPlantRecyclerViewAdapter

    override fun onResume() {
        super.onResume()
        if(adapter != null)
            Log.d("My", "my plant ${plantDao.getMyPlant().size}")
            adapter.updateAdapter(plantDao.getMyPlant())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.my_plant_fragment,container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDb = context?.let { MyDataBase.getInstance(it) }!!
        categoryDao = mDb.getCategoryDao()
        plantDao = mDb.getPlantDao()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        my_plant_recycler_view.layoutManager = LinearLayoutManager(context)

        adapter = MyPlantRecyclerViewAdapter(plantDao.getMyPlant(), this, this)
        my_plant_recycler_view.adapter = adapter

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

    override fun getInfoPlant(id: Long) {

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container, PlantInfoFragment.newInstance(id))
            .addToBackStack("2")
            .commit()

    }

    override fun onActivityDataListener() {
        adapter.updateAdapter(plantDao.getMyPlant())
    }

    override fun onItemClick(id: Long) {
        var plant = plantDao.getPlantById(id)
        plant.is_my = false
        plantDao.insert(plant)
        adapter.updateAdapter(plantDao.getMyPlant())
    }


}
