package com.lendy.Utils.fragments

import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lendy.R
import com.lendy.Utils.DataUtils
import com.lendy.Utils.custom_views.ChooseDateDialog
import kotlinx.android.synthetic.main.research_fragment.*
import kotlinx.android.synthetic.main.supp_order_fragment.*

class SuppOrderFragment : Fragment()
{
    private var currentActivity: Activity? = null
    private var currentView: View? = null
    private var dialogResult: DialogResult? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.supp_order_fragment, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.currentView = view
        this.currentActivity = this.activity

        dialogResult = object : DialogResult {
            override fun getHourResult(hour: Int, minute: Int) {
                Log.e("lol", "lol")
            }

            override fun getDialogResult(date: String, index: Int) {
                choose_date_supp.setText(date)
            }
        }

        date_supp.setOnClickListener {
            val datedialog = ChooseDateDialog(activity, dialogResult, 1)
            datedialog.show()
        }

        hour_supp.setOnClickListener {
            DataUtils.showHourPicker(this.currentActivity, dialogResult = object : DialogResult{
                override fun getHourResult(hour: Int, minute: Int) {
                    choose_hour.setText(hour.toString() + "h" + minute.toString())
                }

                override fun getDialogResult(date: String, index: Int) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
            })
        }
    }

}