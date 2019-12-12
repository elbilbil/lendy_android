package com.lendy.Utils.fragments

import android.app.Activity
import android.app.Fragment
import android.app.FragmentManager
import android.os.Bundle
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

        profilname.text = DataManager.SharedData.sharedDetailMember!!.firstname + " " + DataManager.SharedData.sharedDetailMember!!.lastname
        avis.text = "(" + DataManager.SharedData.sharedDetailMember!!.rating + " avis)"
        description_name.text = DataManager.SharedData.sharedDetailMember?.ratings?.get(0)?.username!!
        description.text = DataManager.SharedData.sharedDetailMember?.ratings?.get(0)?.message!!


        contacter.setOnClickListener {
            val sendMessageDialog = SendMessageDialog(activity, null, DataManager.SharedData.sharedDetailMember)
            sendMessageDialog.show()
        }

        sign.setOnClickListener {

            if (this.currentActivity is MainActivity)
            {
                (this.currentActivity as MainActivity).signatureContractFragment = SignatureContractFragment()
                //(this.currentActivity as MainActivity).contractFragment!!.arguments = bundle
                activity.fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                DataUtils.addFragmentToActivity(activity.fragmentManager, (this.currentActivity as MainActivity).signatureContractFragment, R.id.activity_main)
            }
        }

        if (this.currentActivity is MainActivity)
            (this.currentActivity as MainActivity).hideBottomNavigation()
    }

}