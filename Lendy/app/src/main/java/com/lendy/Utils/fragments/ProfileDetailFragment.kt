package com.lendy.Utils.fragments

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.view.*
import com.lendy.Controllers.*
import com.lendy.Models.Users
import com.lendy.R
import android.graphics.drawable.Drawable
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.lendy.Utils.DataUtils.Companion.addFragmentToActivity
import com.lendy.Utils.custom_views.SendMessageDialog
import com.squareup.picasso.Picasso
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

        val type = object : TypeToken<HashMap<String?, Any?>>() {}.type

        if (b.getSerializable("infos") != null) {
            userInfo = b.getSerializable("infos") as HashMap<String?, Any?>
            /*if (b.getString("infos") != null) {
                userInfo = Gson().fromJson(b.getString("infos"), type)*/
                user = userInfo["user"] as Users?

                if (!user?.type.equals("preteur"))
                    reserver.text = "Confier"

                profilpicture.setImageDrawable(userInfo["firstImage"] as Drawable?)
                profilname.text = user!!.firstname + " " + user!!.lastname
                vehiculedescription.text = user!!.car?.model + "\n" + user!!.car?.types + "\n" + user!!.car?.transmission + "\n" + user!!.car?.km + " km"
                Picasso.get().load(user!!.car?.picture).fit().into(car_image)
                profilcontainer.text = user!!.address
            }

            contacter.setOnClickListener {
                val sendMessageDialog = SendMessageDialog(activity, user)
                sendMessageDialog.show()
            }

            reserver.setOnClickListener {
                if (this.currentActivity is MainActivity) {
                    (this.currentActivity as MainActivity).contractFragment = ContractFragment()

                    //(this.currentActivity as MainActivity).contractFragment!!.arguments = bundle

                    addFragmentToActivity(activity.fragmentManager, (activity as MainActivity).contractFragment, R.id.activity_main)
                }


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