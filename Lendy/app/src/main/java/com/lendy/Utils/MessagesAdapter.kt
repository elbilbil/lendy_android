package com.lendy.Utils

import android.app.Activity
import android.app.Fragment
import android.app.FragmentManager
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.lendy.Controllers.MainActivity
import com.lendy.Models.Discussion
import com.lendy.Models.User
import com.lendy.Models.Users
import com.lendy.R
import kotlinx.android.synthetic.main.user_list_elem.view.*

class MessagesAdapter(val discussions: ArrayList<Discussion>?, val activity : Activity) : RecyclerView.Adapter<MessagesAdapter.ViewHolder>()
{

    override fun onBindViewHolder(holder: MessagesAdapter.ViewHolder, position: Int) {

        holder.container.setOnClickListener { v ->
            run {

                // Faire en sorte que le fragment ProfileDetailFragment soit en premier plan et non en second plan derriere l'activity
                //Ouvrir fragment au clic sur un élément en chargeant son contenu
                //Faire le Search en cliquant dessus

                if (activity is MainActivity) {
                    val bundle = Bundle()
                    activity.profileDetailFragment = ProfileDetailFragment()
                    activity.profileDetailFragment!!.arguments = bundle
                    addFragmentToActivity(activity.fragmentManager, activity.profileDetailFragment, R.id.activity_main)
                }
            }
        }
        //holder.name?.text = arrayListOfElements[position].firstname + " " + arrayListOfElements[position].lastname
        holder.last_message?.text = "Salut"
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