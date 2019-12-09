package com.lendy.Utils.fragments

import android.app.Activity
import android.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import com.lendy.Controllers.MainActivity
import com.lendy.Manager.DataManager
import com.lendy.Manager.ServiceProvider
import com.lendy.Models.LocationObj
import com.lendy.R
import com.lendy.Utils.DataUtils
import com.lendy.Utils.DialogResult
import com.lendy.Utils.custom_views.ChooseDateDialog
import kotlinx.android.synthetic.main.supp_order_fragment.*

class SuppOrderFragment : Fragment() {
    private var currentActivity: Activity? = null
    private var currentView: View? = null
    private var dialogResult: DialogResult? = null
    private var userId: String = ""
    private var dateTimestamp: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.supp_order_fragment, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.currentView = view
        this.currentActivity = this.activity

        if (this.arguments.getString("userId") != null)
            userId = this.arguments.getString("userId") as String

        dialogResult = object : DialogResult {
            override fun getHourResult(hour: Int, minute: Int) {
            }

            override fun getDialogResult(date: String, index: Int, dateTime: Long) {
                choose_date_supp.setText(date)
                dateTimestamp = dateTime
            }
        }

        date_supp.setOnClickListener {
            val datedialog = ChooseDateDialog(activity, dialogResult, 1)
            datedialog.show()
        }

        hour_supp.setOnClickListener {
            DataUtils.showHourPicker(this.currentActivity, dialogResult = object : DialogResult {
                override fun getHourResult(hour: Int, minute: Int) {
                    choose_hour.setText(hour.toString() + "h" + minute.toString())
                }

                override fun getDialogResult(date: String, index: Int, dateTime: Long) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
            })
        }

        valider.setOnClickListener {

            if ((choose_place.text == null || choose_place.text.toString().equals("")) &&
                    (choose_date_supp.text == null || choose_date_supp.text.toString().equals("")) &&
                    (choose_hour.text == null || choose_hour.text.toString().equals(""))) {
                Toast.makeText(this.currentActivity, "Vous devez renseigner tous les champs avant de valider", Toast.LENGTH_SHORT).show()
            } else {

                val locationObj = LocationObj()
                locationObj.address = choose_place.text.toString()
                locationObj.latitude = 48.856977499999999
                locationObj.longitude = 2.3296994

                ServiceProvider.addReservation(activity, DataManager.SharedData.token, userId,
                        dateTimestamp.toString(), dateTimestamp.toString(), locationObj,
                        (System.currentTimeMillis() / 1000).toString(),
                        callback = { code, msg ->
                            if (code == 200) {
                                activity.runOnUiThread {
                                    if (this.currentActivity is MainActivity)
                                        (this.currentActivity as MainActivity).showDialog("Votre demande de rendez-vous à été envoyée !")
                                    this.currentActivity!!.onBackPressed()
                                }
                            }
                        });
            }
        }

        if (this.currentActivity is MainActivity)
            (this.currentActivity as MainActivity).hideBottomNavigation()
    }

}