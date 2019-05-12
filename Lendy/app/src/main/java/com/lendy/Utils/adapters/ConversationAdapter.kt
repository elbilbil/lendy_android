package com.lendy.Utils.adapters

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
import com.lendy.Models.Message
import com.lendy.Models.Users
import com.lendy.R
import com.lendy.Utils.fragments.ProfileDetailFragment

class ConversationAdapter(val conversation_content: ArrayList<Message>, val activity : Activity) : RecyclerView.Adapter<ConversationAdapter.ViewHolder>()
{

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {



    }

    override fun getItemCount(): Int {
        return conversation_content.size
    }

    // Créer des détenteurs de vue. Chaque détenteur de vue est chargé d'afficher un seul élément avec une vue
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        //Faire deux view holder un pour les message de l'autre personne , un pour les notre

        /*afficher en fonction de l'envoyeur la bulle a gauche ou a droite dans la conversation
        + la possibilité d'écrire un msg ( enfin le click sur le bouton envoyer)
        holder.container.setOnClickListener { v ->
            run {

            }
        }*/
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

    }
}