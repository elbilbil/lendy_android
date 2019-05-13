package com.lendy.Utils.adapters

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageSwitcher
import android.widget.ImageView
import com.lendy.Manager.DataManager
import com.lendy.Models.Message
import com.lendy.R
import kotlinx.android.synthetic.main.item_recevided_message.view.*
import java.util.ArrayList
import android.widget.TextView
import kotlinx.android.synthetic.main.item_recevided_message.view.date_content
import kotlinx.android.synthetic.main.item_recevided_message.view.message_content
import kotlinx.android.synthetic.main.item_send_message.view.*

class ConversationAdapter(val conversation_content: ArrayList<Message>, val activity : Activity) : RecyclerView.Adapter<RecyclerView.ViewHolder>()
{
    override fun onCreateViewHolder(viewGroup: ViewGroup, type: Int): RecyclerView.ViewHolder {
        val view: View
        if (type == 1) {
            view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_recevided_message, viewGroup, false)
            return RecViewHolder(view)
        } else {
            view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_send_message, viewGroup, false)
            return SendViewHolder(view)
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, i: Int) {
        if (viewHolder is RecViewHolder ) {
            viewHolder.message_content.text = conversation_content[i].message
            viewHolder.date_content.text = "18:11"
        } else if (viewHolder is SendViewHolder ){
            viewHolder.message_content.text = conversation_content[i].message
            viewHolder.date_content.text = "19:11"
        }
    }

    override fun getItemCount(): Int {
        return conversation_content.size ?: 0
    }

    override fun getItemViewType(position: Int): Int {
        if (conversation_content[position].refUser?.get(0)?.equals(DataManager.SharedData.sharedUser?._id)!!) {
            return 0
        } else {
            return 1
        }
    }


    class RecViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var message_content: TextView = itemView.findViewById(R.id.message_content)
        var date_content: TextView = itemView.findViewById(R.id.date_content)
    }

    class SendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var message_content: TextView = itemView.findViewById(R.id.message_content)
        var date_content: TextView = itemView.findViewById(R.id.date_content)
    }

}