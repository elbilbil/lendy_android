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
import com.lendy.Models.User
import com.lendy.Models.Users
import com.lendy.R
import kotlinx.android.synthetic.main.user_list_elem.view.*

class ConversationAdapter(val arrayListOfElements: ArrayList<Users>, val activity : Activity) : RecyclerView.Adapter<ConversationAdapter.ViewHolder>()
{

    override fun onBindViewHolder(holder: ConversationAdapter.ViewHolder, position: Int) {

        holder.container.setOnClickListener { v ->
            run {
                val userInfos: HashMap<String?, Any?> = hashMapOf()
                userInfos["description"] = holder.description?.text.toString()
                userInfos["firstImage"] = holder.firstImage?.drawable
                userInfos["user"] = arrayListOfElements[position]
                // Faire en sorte que le fragment ProfileDetailFragment soit en premier plan et non en second plan derriere l'activity
                //Ouvrir fragment au clic sur un élément en chargeant son contenu
                //Faire le Search en cliquant dessus

                if (activity is MainActivity) {
                    val bundle = Bundle()
                    bundle.putSerializable("infos", userInfos)
                    activity.profileDetailFragment = ProfileDetailFragment()
                    activity.profileDetailFragment!!.arguments = bundle
                    addFragmentToActivity(activity.fragmentManager, activity.profileDetailFragment, R.id.activity_main)
                }
            }
        }

        if (arrayListOfElements[position].type != "emprunteur")
            holder.firstImage?.setImageResource(R.drawable.voiture_placeholder)
        else
            holder.firstImage?.setImageResource(R.drawable.avatar_placeholder)
        holder.name?.text = arrayListOfElements[position].firstname + " " + arrayListOfElements[position].lastname
        holder.description?.text = "Description"
    }

    override fun getItemCount(): Int {
        return arrayListOfElements.size
    }

    // Créer des détenteurs de vue. Chaque détenteur de vue est chargé d'afficher un seul élément avec une vue
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate((R.layout.user_list_elem), parent, false)
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
        var description = itemView?.findViewById<View>(R.id.description) as TextView?
        var accept = itemView.findViewById(R.id.accept) as ImageView?
        var remove = itemView.findViewById(R.id.remove) as ImageView?
        var container = itemView.findViewById(R.id.elemcontainer) as ConstraintLayout
    }
}