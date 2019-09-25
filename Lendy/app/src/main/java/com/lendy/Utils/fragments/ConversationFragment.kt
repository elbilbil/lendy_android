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
import android.support.v7.widget.LinearLayoutManager
import com.lendy.Models.Message
import com.lendy.Utils.DataUtils
import com.lendy.Utils.adapters.ConversationAdapter
import kotlinx.android.synthetic.main.conversation_fragment.*
import android.support.v7.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lendy.R
import java.lang.reflect.Type

class ConversationFragment : Fragment() {

    var currentActivity: Activity? = null
    var currentView: View? = null
    var conversation_content: ArrayList<Message>? = null
    var contactId: String? = null
    var adapter: ConversationAdapter? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.conversation_fragment, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.currentView = view
        this.currentActivity = this.activity
        val cA = this.currentActivity

        if (cA is MainActivity)
        {
            cA.conversationFragment = this
        }
        val b = this.arguments

        if (b.getString("contactId") != null) {
            contactId = b.getString("contactId") as String?
        }

        if (b.getString("conversation_content") != null) {
            val type = object : TypeToken<ArrayList<Message>>() {}.type
            conversation_content = Gson().fromJson(b.getString("conversation_content"), type)
            recyclerInit(conversation_content)
        }

        send.setOnClickListener {
            if (message.text.isNotEmpty()) {
                DataManager.sendMessage(currentActivity, DataManager.SharedData.token, message.text.toString(), contactId, callback = { success: Boolean ->
                    if (success) {
                        DataManager.getSpecificDiscussionMessages(activity, DataManager.SharedData.token, contactId, callback = { success2, messages ->
                            if (success2 && messages != null) {
                                activity.runOnUiThread {
                                    recyclerInit(messages)
                                    message.text.clear()
                                    DataUtils.hideSoftKeyboard(activity)
                                    conversation.post(Runnable {
                                        conversation.smoothScrollToPosition(adapter?.getItemCount()!! - 1)
                                    })
                                }
                            } else {
                                activity.runOnUiThread {
                                    AlertDialog.Builder(activity)
                                            .setTitle("Erreur")
                                            .setMessage("Impossible d'afficher cette conversation , veuillez réessayer")
                                            .setPositiveButton("OK", null)
                                            .show()
                                }
                            }
                        })

                    } else {
                        activity!!.runOnUiThread {
                            activity!!.runOnUiThread {
                                android.app.AlertDialog.Builder(activity)
                                        .setTitle("Erreur")
                                        .setMessage("Une erreur est survenue, veuillez réessayer")
                                        .setPositiveButton("OK", null)
                                        .show()
                            }
                        }
                    }
                })
            } else {
                activity!!.runOnUiThread {
                    activity!!.runOnUiThread {
                        android.app.AlertDialog.Builder(activity)
                                .setTitle("Erreur")
                                .setMessage("Une erreur est survenue, veuillez réessayer")
                                .setPositiveButton("OK", null)
                                .show()
                    }
                }
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

    override fun onStop() {
        super.onStop()
    }

    fun onFail() {

        val cA = this.currentActivity
    }

    fun recyclerInit(discussion_content: ArrayList<Message>?) {
        if (discussion_content == null || discussion_content.size == 0) {
            return
        }

        adapter = ConversationAdapter(discussion_content, currentActivity!!)
        conversation.layoutManager = LinearLayoutManager(currentActivity, LinearLayoutManager.VERTICAL, false)
        //conversation.addItemDecoration(DividerItemDecoration(conversation.context, DividerItemDecoration.VERTICAL))
        // Adapter changes cannot affect the size of the RecyclerView
        conversation.setHasFixedSize(true)

        // Attach an Adapter to the recycleView who will contains the list of lootboxes and manage it
        conversation.adapter = adapter

        conversation.post(Runnable {
            conversation.smoothScrollToPosition(adapter?.getItemCount()!! - 1)
        })
    }
}