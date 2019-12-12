package com.lendy.Utils.fragments

import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.lendy.Controllers.MainActivity
import com.lendy.Controllers.SuppOrderActivity
import com.lendy.R
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
            if (activity is MainActivity) {
                val intent = Intent(activity, SuppOrderActivity::class.java)
                intent.putExtra("userId", userId)
                activity.startActivity(intent)
                /* val bundle = Bundle()
                 bundle.putString("userId", userId)
                 (activity as MainActivity).SuppOrderActivity = SuppOrderActivity()

                 (this.activity as MainActivity).SuppOrderActivity?.arguments = bundle

                 activity.fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                 DataUtils.addFragmentToActivity(activity.fragmentManager, (activity as MainActivity).SuppOrderActivity, R.id.activity_main)*/
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