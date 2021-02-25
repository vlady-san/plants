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
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.all_plant_fragment.*
import kotlinx.android.synthetic.main.all_plant_fragment.edit_text_catalog
import kotlinx.android.synthetic.main.all_plant_fragment.search_cancel
import kotlinx.android.synthetic.main.all_plant_fragment.search_icon_button
import kotlinx.android.synthetic.main.care_plant_fragment.*
import kotlinx.android.synthetic.main.my_plant_fragment.*
import kotlinx.android.synthetic.main.plant_info_fragment.*

class PlantCareFragment : Fragment() {

    companion object {

        fun newInstance(id: Long): PlantCareFragment {
            val args = Bundle()
            args.putLong("id", id)

            val fragment = PlantCareFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var mDb: MyDataBase
    private lateinit var plantDao: PlantDao

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.care_plant_fragment, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDb = context?.let { MyDataBase.getInstance(it) }!!
        //categoryDao = mDb.getCategoryDao()
        plantDao = mDb.getPlantDao()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var plant: Plant? = null
        var id = arguments?.getLong("id", -1)
        if (id != null) {
            if (id != -1L) {
                plant = plantDao.getPlantById(id)
                image.setImageDrawable(resources.getDrawable(plant.image_middle, null))
                if (plant.watering != 0)
                    watering_set_up.text = "раз в ${plant.watering} дня"
                if (plant.fereliziter != 0)
                    fertilizer_set_up.text = "раз в ${plant.fereliziter} дня"
                if (plant.spraying != 0)
                    spraying_set_up.text = "раз в ${plant.spraying} дня"
            }
        }

        ll_watering.setOnClickListener {
            val myDialogFragment = NumberPickerDialogFragment.newInstance("watering")
            val manager = requireActivity().supportFragmentManager
            myDialogFragment.show(manager, "myDialog")
        }
        ll_spraying.setOnClickListener {
            val myDialogFragment = NumberPickerDialogFragment.newInstance("spraying")
            val manager = requireActivity().supportFragmentManager
            myDialogFragment.show(manager, "myDialog")
        }
        ll_fertilizer.setOnClickListener {
            val myDialogFragment = NumberPickerDialogFragment.newInstance("fertilizer")
            val manager = requireActivity().supportFragmentManager
            myDialogFragment.show(manager, "myDialog")
        }

        setFragmentResultListener(NumberPickerDialogFragment.NUMBER_PICKER) { key, bundle ->

            val result = bundle.getInt(NumberPickerDialogFragment.NUMBER_PICKER)
            val activity = bundle.getString(NumberPickerDialogFragment.ACTIVITY_PICKER)
            if (plant != null) {
                when (activity) {
                    "watering" -> {
                        plant.watering = result
                        if(result==0) watering_set_up.text = "Установить"
                        else watering_set_up.text = "раз в $result дня"
                    }
                    "fertilizer" -> {
                        plant.fereliziter = result
                        if(result==0) fertilizer_set_up.text = "Установить"
                        else
                        fertilizer_set_up.text = "раз в $result дня"
                    }
                    "spraying" -> {
                        plant.spraying = result
                        if(result==0) spraying_set_up.text = "Установить"
                        else
                        spraying_set_up.text = "раз в $result дня"
                    }
                }
                plantDao.insert(plant)
            }


        }


    }
}
