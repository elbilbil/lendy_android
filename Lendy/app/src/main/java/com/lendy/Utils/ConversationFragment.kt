package com.lendy.Utils

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

class ConversationFragment : Fragment() {

    var currentActivity: Activity? = null
    var currentView: View? = null
    var userInfo: HashMap<String?, Any?> = hashMapOf()
    var user: Users? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.conversation_fragment, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.currentView = view
        this.currentActivity = this.activity
        val cA = this.currentActivity

        val b = this.arguments
        if (b.getSerializable("infos") != null) {
            userInfo = b.getSerializable("infos") as HashMap<String?, Any?>
            user = userInfo["user"] as Users?
            profilpicture.setImageDrawable(userInfo["firstImage"] as Drawable?)
            profilname.text = user!!.firstname + " " + user!!.lastname
        }

        //recyclerInit()
        contacter.setOnClickListener {
            // APpeler une conversation avec l'id de l'user actuel et notre email
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

    fun recyclerInit(role: String?, users: ArrayList<Users>) {
        if (role == null)
            return
        // CAROUSEL DESACTIVATED
        //recyclerview.layoutManager = LinearManager(this, LinearLayoutManager.HORIZONTAL, false)

        //recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        // Adapter changes cannot affect the size of the RecyclerView
        recyclerView.setHasFixedSize(true)

        // Attach an Adapter to the recycleView who will contains the list of lootboxes and manage it
        //recyclerView.adapter = getDriversOrPassenger(users, role)?.let { RecyclerAdapter(it, this) }

        /*recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val offset = recyclerView!!.computeHorizontalScrollOffset()
                if (offset % recyclerView!!.getChildAt(0).width === 0) {
                    currentPosition = offset / recyclerView!!.getChildAt(0).width
                }
            }
        })
*/
    }
}