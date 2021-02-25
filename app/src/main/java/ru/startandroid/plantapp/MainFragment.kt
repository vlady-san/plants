package ru.startandroid.plantapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_main.*


interface LoadFragments{
    fun loadFragment(position: Int): Fragment
}
class MainFragment : Fragment(), LoadFragments {

    interface OnActivityDataListener {
        fun onActivityDataListener()
    }
    private var mListener: OnActivityDataListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.main_fragment, container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val fragmentAdapter = MyPagerAdapter(requireActivity().supportFragmentManager, this)
        viewpager.adapter = fragmentAdapter
        tabs.setupWithViewPager(viewpager)

        viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                mListener?.onActivityDataListener()
            }

        })
    }

    override fun loadFragment(position: Int): Fragment {
        return when (position) {
            0 -> AllPlantFragment()
            else -> {
                var fragment = MyPlantFragment()
                mListener = fragment as OnActivityDataListener
                fragment
            }
        }
    }


}