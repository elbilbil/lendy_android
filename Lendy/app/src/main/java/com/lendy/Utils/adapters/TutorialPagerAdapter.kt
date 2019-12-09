package com.lendy.Utils.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.lendy.Utils.fragments.TutoFragment
import com.lendy.Utils.fragments.ViewPagerFragment
import com.lendy.Utils.fragments.ViewPagerFragment2
import com.lendy.Utils.fragments.ViewPagerFragment3


class TutorialPagerAdapter(fragmentManager: FragmentManager) :
        FragmentStatePagerAdapter(fragmentManager) {
    private val count = 3

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment = ViewPagerFragment()
        when (position) {
            0 -> fragment = ViewPagerFragment()
            1 -> fragment = ViewPagerFragment2()
            2 -> fragment = ViewPagerFragment3()
        }
        return fragment
    }

    override fun getCount(): Int {
        return count
    }

}