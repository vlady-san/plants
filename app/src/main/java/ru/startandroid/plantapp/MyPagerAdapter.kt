package ru.startandroid.plantapp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class MyPagerAdapter(fm: FragmentManager, private val listener : LoadFragments) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return listener.loadFragment(position)
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "Все растений"
            else -> "Мои растения"
        }
    }


}