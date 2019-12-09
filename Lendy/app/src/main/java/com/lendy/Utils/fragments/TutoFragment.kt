package com.lendy.Utils.fragments

import android.app.Fragment
import android.app.FragmentManager
import android.graphics.Color
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lendy.Controllers.MainActivity
import com.lendy.R
import com.lendy.Utils.DataUtils
import com.lendy.Utils.adapters.TutorialPagerAdapter
import kotlinx.android.synthetic.main.tuto_resa_fragment.*

class TutoFragment : Fragment() {

    private var userId: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.tuto_resa_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewPager(view)

        if (this.arguments.getString("userId") != null)
            userId = this.arguments.getString("userId") as String

        goNext.setOnClickListener {
            if (activity is MainActivity)
            {
                val bundle = Bundle()
                bundle.putString("userId", userId)
                (activity as MainActivity).suppOrderFragment = SuppOrderFragment()

                (this.activity as MainActivity).suppOrderFragment?.arguments = bundle

                activity.fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                DataUtils.addFragmentToActivity(activity.fragmentManager, (activity as MainActivity).suppOrderFragment, R.id.activity_main)
            }
        }
    }

    private fun setViewPager(view: View) {

        //dots.setDotTint(Color.BLACK)
        //dots.setDotTintRes(R.color.black)

        if (activity is MainActivity) {
            val adapter = TutorialPagerAdapter((activity as MainActivity).supportFragmentManager)
            viewPager.adapter = adapter
            tabLayout.setupWithViewPager(viewPager, true)

            viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrolled(
                        position: Int,
                        positionOffset: Float,
                        positionOffsetPixels: Int
                ) {

                }

                override fun onPageSelected(position: Int) {

                }

                override fun onPageScrollStateChanged(state: Int) {

                }
            })
        }
    }

}