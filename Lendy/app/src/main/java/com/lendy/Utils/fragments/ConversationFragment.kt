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
import android.support.v4.app.NotificationCompat.getExtras
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.profiledetail.*
import com.google.gson.Gson
import android.content.Intent.getIntent
import android.support.v7.widget.DividerItemDecoration
import com.lendy.Models.Discussion
import com.lendy.Models.Message
import com.lendy.Utils.adapters.ConversationAdapter
import com.lendy.Utils.adapters.MessagesAdapter
import kotlinx.android.synthetic.main.conversation_fragment.*
import kotlinx.android.synthetic.main.messages_fragment.*


class ConversationFragment : Fragment() {

    var currentActivity: Activity? = null
    var currentView: View? = null
    var conversation_content: ArrayList<Message>? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.conversation_fragment, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.currentView = view
        this.currentActivity = this.activity
        val cA = this.currentActivity

        val b = this.arguments
        if (b.getSerializable("conversation_content") != null) {
            conversation_content = b.getSerializable("conversation_content") as ArrayList<Message>?
            recyclerInit(conversation_content)
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

    fun recyclerInit(discusssion_content: ArrayList<Message>?) {
        if (discusssion_content == null || discusssion_content.size == 0) {
            return
        }
        conversation.layoutManager = LinearLayoutManager(currentActivity, LinearLayoutManager.VERTICAL, false)
        conversation.addItemDecoration(DividerItemDecoration(conversation.context, DividerItemDecoration.VERTICAL))
        // Adapter changes cannot affect the size of the RecyclerView
        conversation.setHasFixedSize(true)

        // Attach an Adapter to the recycleView who will contains the list of lootboxes and manage it
        conversation.adapter = ConversationAdapter(discusssion_content, currentActivity!!)
    }
}