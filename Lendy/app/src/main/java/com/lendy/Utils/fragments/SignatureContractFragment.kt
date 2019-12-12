package com.lendy.Utils.fragments

import android.app.Activity
import android.app.Fragment
import android.app.FragmentManager
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lendy.Controllers.MainActivity
import com.lendy.Manager.DataManager
import com.lendy.Models.Users
import com.lendy.R
import com.lendy.Utils.DataUtils
import com.lendy.Utils.custom_views.SendMessageDialog
import kotlinx.android.synthetic.main.contract_fragment.*
import kotlinx.android.synthetic.main.signature_contract.*

class SignatureContractFragment : Fragment()
{
    private var currentActivity: Activity? = null
    private var currentView: View? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.signature_contract, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.currentView = view
        this.currentActivity = this.activity

        validate.setOnClickListener {

            activity.runOnUiThread {
                (this.currentActivity as MainActivity).showDialog("Votre contrat à été signé avec succès !")
                Handler().postDelayed(Runnable {
                    activity.onBackPressed()
                }, 2000)
            }
        }

        if (this.currentActivity is MainActivity)
            (this.currentActivity as MainActivity).hideBottomNavigation()
    }

}