package com.lendy.Utils.fragments

import android.app.Activity
import android.app.Fragment
import android.app.FragmentManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lendy.Controllers.MainActivity
import com.lendy.R
import com.lendy.Utils.DataUtils
import kotlinx.android.synthetic.main.contract_fragment.*

class ContractFragment : Fragment()
{
    private var currentActivity: Activity? = null
    private var currentView: View? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.contract_fragment, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.currentView = view
        this.currentActivity = this.activity

        sign.setOnClickListener {

            if (this.currentActivity is MainActivity)
            {
                /*(this.currentActivity as MainActivity).SuppOrderActivity = SuppOrderActivity()
                //(this.currentActivity as MainActivity).contractFragment!!.arguments = bundle
                activity.fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                DataUtils.addFragmentToActivity(activity.fragmentManager, (this.currentActivity as MainActivity).SuppOrderActivity, R.id.activity_main)*/
            }
        }

        if (this.currentActivity is MainActivity)
            (this.currentActivity as MainActivity).hideBottomNavigation()
    }

}