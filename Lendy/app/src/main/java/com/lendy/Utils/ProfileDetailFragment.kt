package com.lendy.Utils

import android.app.Activity
import android.app.AlertDialog
import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import com.lendy.Controllers.*
import com.lendy.Manager.DataManager
import com.lendy.Models.User
import com.lendy.Models.Users
import com.lendy.R
import kotlinx.android.synthetic.main.register.*
import android.content.Intent.getIntent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.support.v4.app.NotificationCompat.getExtras
import kotlinx.android.synthetic.main.profiledetail.*

class ProfileDetailFragment : Fragment() {

    var currentActivity: Activity? = null
    var currentView: View? = null
    var userInfo: HashMap<String?, Any?> = hashMapOf()
    var user: Users? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.profiledetail, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.currentView = view
        this.currentActivity = this.activity
        val cA = this.currentActivity

        val b = this.arguments
        if (b.getSerializable("infos") != null) {
            userInfo = b.getSerializable("infos") as HashMap<String?, Any?>
            user = userInfo["user"] as Users?
            profilpicture.setImageDrawable(userInfo["firstImage"] as Drawable?)
            profilname.text = user!!.firstname + " " + user!!.lastname
        }

        contacter.setOnClickListener {
            val sendMessageDialog = SendMessageDialog(activity, user)
            sendMessageDialog.show()
        }

        if (cA is MainActivity)
            cA.hideBottomNavigation()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onSaveInstanceState(outState: Bundle?) {

    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onDetach() {
        super.onDetach()
    }

    override fun onDestroy() {
        super.onDestroy()
    }


    fun onSucceed() {

        val cA = this.currentActivity
    }

    fun onFail() {

        val cA = this.currentActivity
    }
}