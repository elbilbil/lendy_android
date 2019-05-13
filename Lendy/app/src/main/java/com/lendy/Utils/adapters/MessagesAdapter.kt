package com.lendy.Utils.adapters

import android.app.Activity
import android.app.AlertDialog
import android.app.Fragment
import android.app.FragmentManager
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.lendy.Controllers.MainActivity
import com.lendy.Models.Discussion
import com.lendy.Models.Users
import com.lendy.R
import com.lendy.Utils.fragments.ConversationFragment
import com.google.gson.Gson
import com.lendy.Manager.DataManager

class MessagesAdapter(val discussions: ArrayList<Discussion>?, val activity : Activity, val allUsers: ArrayList<Users>?) : RecyclerView.Adapter<MessagesAdapter.ViewHolder>()
{

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.container.setOnClickListener { v ->
            run {
                var contactId : String = "";

                for (elem in discussions?.get(position)?.members!!) {
                    if (!DataManager.SharedData.sharedUser?._id?.equals(elem)!!)
                    {
                        contactId = elem!!
                        break;
                    }
                }
                DataManager.getSpecificDiscussionMessages(activity, DataManager.SharedData.token, contactId, callback = {success, messages ->
                    if (success && messages != null)
                    {
                        if (activity is MainActivity) {
                            val bundle = Bundle()
                            bundle.putSerializable("conversation_content", messages)
                            bundle.putString("contactId", contactId)
                            activity.conversationFragment = ConversationFragment()
                            activity.conversationFragment!!.arguments = bundle
                            addFragmentToActivity(activity.fragmentManager, activity.conversationFragment, R.id.activity_main)
                        }
                    }
                    else
                    {
                        activity.runOnUiThread {
                            AlertDialog.Builder(activity)
                                    .setTitle("Erreur")
                                    .setMessage("Impossible d'afficher cette conversation , veuillez réessayer")
                                    .setPositiveButton("OK", null)
                                    .show()
                        }
                    }
                })
            }
        }
        //holder.name?.text = arrayListOfElements[position].firstname + " " + arrayListOfElements[position].lastname
        holder.last_message?.text = discussions?.get(position)?.messages?.get(0)?.message
        holder.name?.text = getUserName(position)
    }

    fun getUserName(position: Int): String?
    {
        if (allUsers != null) {
            for (user: Users in allUsers)
            {
                if (user._id?.equals(discussions?.get(position)?.members?.get(1))!!)
                {
                    return user.firstname!! + " " + user.lastname!!
                }
            }
        }
        return ""
    }

    override fun getItemCount(): Int {
        if (discussions != null) {
            return discussions.size
        }
        else return 0
    }

    // Créer des détenteurs de vue. Chaque détenteur de vue est chargé d'afficher un seul élément avec une vue
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate((R.layout.conversation_title_elem), parent, false)
        return ViewHolder(view)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    fun addFragmentToActivity(manager: FragmentManager, fragment: Fragment?, frameId: Int) {

        if (fragment == null) {
            return;
        }

        val transaction = manager.beginTransaction()
        transaction.add(frameId, fragment)
        transaction.addToBackStack(null);
        transaction.commitAllowingStateLoss()
    }

    // Ici on recupere tous les élements contenu dans le layout
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var firstImage = itemView.findViewById(R.id.firstImage) as ImageView?
        var name = itemView?.findViewById<View>(R.id.name) as TextView?
        var last_message = itemView?.findViewById<View>(R.id.last_message) as TextView?
        var container = itemView.findViewById(R.id.elemcontainer) as ConstraintLayout
    }
}