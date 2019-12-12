package com.lendy.Utils.fragments

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
import androidx.core.app.NotificationCompat.getExtras
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import android.util.Log
import com.lendy.Utils.DataUtils
import com.lendy.Utils.adapters.RecyclerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.profiledetail.*

class HomeFragment : Fragment() {

    var currentActivity: Activity? = null
    var currentView: View? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.currentView = view
        this.currentActivity = this.activity
        val cA = this.currentActivity

        if (DataUtils.readStringFromPreferences(currentActivity, "token") == "default")
            DataManager.SharedData.token = DataManager.SharedData.sharedUser?.token
        else
            DataManager.SharedData.token = DataUtils.readStringFromPreferences(currentActivity, "token")

        DataManager.getMyself(currentActivity, DataManager.SharedData.token, "username", DataUtils.readStringFromPreferences(currentActivity, "username"), callback =
        { success ->
            if (success) {
                if (DataManager.SharedData.sharedUser?.type == "preteur") {
                    DataManager.getDrivers(currentActivity, DataManager.SharedData.token, callback = { success, arrayUsers ->
                        if (success && arrayUsers != null) {
                            currentActivity!!.runOnUiThread {
                                DataManager.SharedData.sharedDrivers?.let { recyclerInit("emprunteur", it) }
                                messagetype?.text = "Ils cherchent une voiture"
                                // Load toutes les personnes qui recherchent une voiture et peupler le recyclerView de ces données
                            }
                        } else
                            Log.e("bad", "bad")
                    })

                }
                // Si le choix selectionné est "SEARCH"
                else if (DataManager.SharedData.sharedUser?.type == "emprunteur") {
                    DataManager.getLenders(currentActivity, DataManager.SharedData.token, callback = { success, arrayUsers ->
                        if (success && arrayUsers != null) {
                            currentActivity!!.runOnUiThread {
                                DataManager.SharedData.sharedLenders?.let { recyclerInit("preteur", it) }
                                messagetype?.text = "Ils proposent une voiture"
                                // Load toutes les personnes qui proposent leur voiture et peupler le recyclerView de ces données également
                            }
                        } else
                            Log.e("bad", "bad")
                    })
                }
            } else
                Log.e("bad", "bad")
        })
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


    fun recyclerInit(role: String?, users: ArrayList<Users>) {
        if (role == null)
            return
        // CAROUSEL DESACTIVATED
        //recyclerview.layoutManager = LinearManager(this, LinearLayoutManager.HORIZONTAL, false)

        if (recyclerView != null) {
            recyclerView.layoutManager = LinearLayoutManager(currentActivity, LinearLayoutManager.VERTICAL, false)
            recyclerView.addItemDecoration(DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL))
            // Adapter changes cannot affect the size of the RecyclerView
            recyclerView.setHasFixedSize(true)

            if (currentActivity != null && currentActivity is MainActivity)
                recyclerView.adapter = (currentActivity as MainActivity).getDriversOrPassenger(users, role)?.let { RecyclerAdapter(it, currentActivity!!) }
        }
        // Attach an Adapter to the recycleView who will contains the list of lootboxes and manage it

        /*recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val offset = recyclerView!!.computeHorizontalScrollOffset()
                if (offset % recyclerView!!.getChildAt(0).width === 0) {
                    currentPosition = offset / recyclerView!!.getChildAt(0).width
                }
            }
        })
    */
    }

    fun onSucceed() {

        val cA = this.currentActivity
    }

    fun onFail() {

        val cA = this.currentActivity
    }
}