package ru.startandroid.plantapp

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.all_plant_fragment.*
import kotlinx.android.synthetic.main.all_plant_fragment.edit_text_catalog
import kotlinx.android.synthetic.main.all_plant_fragment.search_cancel
import kotlinx.android.synthetic.main.all_plant_fragment.search_icon_button
import kotlinx.android.synthetic.main.my_plant_fragment.*

class PlantInfoFragment : Fragment() {

    companion object {

        fun newInstance(id: Long): PlantInfoFragment {
            val args = Bundle()
            args.putLong("id", id)

            val fragment = PlantInfoFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var mDb : MyDataBase
    private lateinit var plantDao: PlantDao

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.plant_info_fragment,container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDb = context?.let { MyDataBase.getInstance(it) }!!
        //categoryDao = mDb.getCategoryDao()
        plantDao = mDb.getPlantDao()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }


    }
