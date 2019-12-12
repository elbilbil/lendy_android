package com.lendy.Utils.adapters


import android.app.Activity
import android.app.Fragment
import android.app.FragmentManager
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.google.gson.Gson
import com.lendy.Controllers.MainActivity
import com.lendy.Manager.DataManager
import com.lendy.Models.Member
import com.lendy.Models.Reservation
import com.lendy.Models.Users
import com.lendy.R
import com.lendy.Utils.DataUtils.Companion.addFragmentToActivity
import com.lendy.Utils.fragments.ContractFragment
import com.lendy.Utils.fragments.ProfileDetailFragment

/**
 * Class RankingAdapter , C'est ici que les données du RecyclerView vont être traitées
 * property: Class
 * @param: arrayListOfElements: ArrayList of Object SKMRanking
 * @param: activity : current activity
 */
class ReservationAdapter(val arrayListOfReservations: ArrayList<Reservation>, val activity : Activity) : RecyclerView.Adapter<ReservationAdapter.ViewHolder>()
{
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var destinataire : Member? = null
        for (member : Member in arrayListOfReservations[position].members)
        {
           if (!member._id.equals(DataManager.SharedData.sharedUser?._id))
           {
               destinataire = member
               holder.firstImage?.setImageResource(R.drawable.avatar_placeholder)
               holder.name?.text = member.firstname + " " + member.lastname
           }
        }

        holder.container.setOnClickListener { _ ->
            run {

                if (activity is MainActivity) {
                    //val bundle = Bundle()
                    if (destinataire != null)
                        DataManager.SharedData.sharedDetailMember = destinataire
                    activity.contractFragment = ContractFragment()
                    addFragmentToActivity(activity.fragmentManager, activity.contractFragment, R.id.activity_main)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return arrayListOfReservations.size
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

    // Ici on recupere tous les élements contenu dans le layout
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var firstImage = itemView.findViewById(R.id.firstImage) as ImageView?
        var name = itemView?.findViewById<View>(R.id.name) as TextView?
        var container = itemView.findViewById(R.id.elemcontainer) as ConstraintLayout
    }
}