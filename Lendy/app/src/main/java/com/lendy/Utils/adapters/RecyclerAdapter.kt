package com.lendy.Utils.adapters


import android.app.Activity
import android.app.Fragment
import android.app.FragmentManager
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.google.gson.Gson
import com.lendy.Controllers.MainActivity
import com.lendy.Manager.DataManager
import com.lendy.Models.Users
import com.lendy.R
import com.lendy.Utils.DataUtils.Companion.addFragmentToActivity
import com.lendy.Utils.fragments.ProfileDetailFragment
import com.squareup.picasso.Picasso

/**
 * Class RankingAdapter , C'est ici que les données du RecyclerView vont être traitées
 * property: Class
 * @param: arrayListOfElements: ArrayList of Object SKMRanking
 * @param: activity : current activity
 */
class RecyclerAdapter(val arrayListOfElements: ArrayList<Users>, val activity : Activity) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>()
{
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.container.setOnClickListener { _ ->
            run {
                val userInfos: HashMap<String?, Any?> = hashMapOf()
                userInfos["firstImage"] = holder.firstImage?.drawable
                userInfos["user"] = arrayListOfElements[position]
                // Faire en sorte que le fragment ProfileDetailFragment soit en premier plan et non en second plan derriere l'activity
                //Ouvrir fragment au clic sur un élément en chargeant son contenu
                //Faire le Search en cliquant dessus

                if (activity is MainActivity) {
                    //val bundle = Bundle()
                    DataManager.SharedData.sharedDetailUser = userInfos
                    //bundle.putSerializable("infos", userInfos)
                    activity.profileDetailFragment = ProfileDetailFragment()
                    //activity.profileDetailFragment!!.arguments = bundle
                    addFragmentToActivity(activity.fragmentManager, activity.profileDetailFragment, R.id.activity_main)
                }
            }
        }

        holder.firstImage?.setImageResource(R.drawable.ic_account_circle)

        Picasso.get().load(arrayListOfElements[position].car?.picture).fit().into(holder.firstImage)

        holder.name?.text = arrayListOfElements[position].firstname + " " + arrayListOfElements[position].lastname
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

    // Ici on recupere tous les élements contenu dans le layout
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var firstImage = itemView.findViewById(R.id.firstImage) as ImageView?
        var name = itemView?.findViewById<View>(R.id.name) as TextView?
        var container = itemView.findViewById(R.id.elemcontainer) as ConstraintLayout
    }
}