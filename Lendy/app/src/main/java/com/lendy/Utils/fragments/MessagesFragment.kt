package com.lendy.Utils.fragments

import android.app.Activity
import android.app.AlertDialog
import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.view.*
import com.lendy.Manager.DataManager
import com.lendy.Models.Users
import com.lendy.R
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.lendy.Models.Discussion
import com.lendy.Utils.adapters.MessagesAdapter
import kotlinx.android.synthetic.main.messages_fragment.*

class MessagesFragment : Fragment() {

    var currentActivity: Activity? = null
    var currentView: View? = null
    var userInfo: HashMap<String?, Any?> = hashMapOf()
    var user: Users? = null
    //FINIR LES MESSAGES
    //REVOIR LA RECHERCHE
    //FAIRE NOTRE PROFIL

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.messages_fragment, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.currentView = view
        this.currentActivity = this.activity
        val cA = this.currentActivity

        val b = this.arguments

        DataManager.getDiscussions(currentActivity, DataManager.SharedData.token, callback = {success, discussions ->
            if (success && discussions != null)
            {
                currentActivity!!.runOnUiThread {
                    recyclerInit(discussions)
                }
            }
            else
            {
                currentActivity!!.runOnUiThread {
                    AlertDialog.Builder(this.currentActivity)
                            .setTitle("Erreur")
                            .setMessage("Erreur lors de la récupération des conversations, veuillez réessayer")
                            .setPositiveButton("OK", null)
                            .show()
                }
            }
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


    fun onSucceed() {

        val cA = this.currentActivity
    }

    fun onFail() {

        val cA = this.currentActivity
    }

    fun recyclerInit(discussions: ArrayList<Discussion>?) {
        if (discussions == null || discussions.size == 0) {
            nomessages.visibility = View.VISIBLE
            return
        }
        else
        {
            nomessages.visibility = View.GONE
        }
        recyclerView.layoutManager = LinearLayoutManager(currentActivity, LinearLayoutManager.VERTICAL, false)
        recyclerView.addItemDecoration(DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL))
        // Adapter changes cannot affect the size of the RecyclerView
        recyclerView.setHasFixedSize(true)

        // Attach an Adapter to the recycleView who will contains the list of lootboxes and manage it
        if (DataManager.SharedData?.sharedUser?.type!!.equals("preteur"))
            recyclerView.adapter = MessagesAdapter(discussions, currentActivity!!, DataManager.SharedData?.sharedDrivers!!)
        else
            recyclerView.adapter = MessagesAdapter(discussions, currentActivity!!, DataManager.SharedData?.sharedLenders!!)
    }
}