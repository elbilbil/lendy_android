package com.lendy.Utils.fragments

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.view.*
import com.lendy.Controllers.*
import com.lendy.Models.Users
import com.lendy.R
import android.graphics.drawable.Drawable
import android.util.Log
import com.lendy.Manager.DataManager
import com.lendy.Utils.custom_views.SendMessageDialog
import kotlinx.android.synthetic.main.profiledetail.*
import android.content.Intent
import android.widget.Toast
import android.provider.MediaStore
import android.graphics.Bitmap
import kotlinx.android.synthetic.main.myprofil_fragment.*
import kotlinx.android.synthetic.main.profiledetail.profilname
import kotlinx.android.synthetic.main.profiledetail.profilpicture
import kotlinx.android.synthetic.main.profiledetail.textView11
import java.io.IOException

class MyProfilFragment : Fragment() {

    var currentActivity: Activity? = null
    var currentView: View? = null
    var userInfo: HashMap<String?, Any?> = hashMapOf()
    var user: Users? = null
    var SELECT_IMAGE = 1211

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.myprofil_fragment, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.currentView = view
        this.currentActivity = this.activity
        val cA = this.currentActivity

        val b = this.arguments
        Log.e("lol", DataManager.SharedData.sharedUser.toString()!!)
        profilname.text = DataManager.SharedData.sharedUser!!.firstname + " " + DataManager.SharedData.sharedUser!!.lastname
        textView11.text = DataManager.SharedData.sharedUser!!.username +"\\n0606060606\\nMarseille, FR"

        profilpicture.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Choisissez une image"), SELECT_IMAGE)
        }

        /*if (b.getSerializable("infos") != null) {
            userInfo = b.getSerializable("infos") as HashMap<String?, Any?>
            user = userInfo["user"] as Users?
            profilpicture.setImageDrawable(userInfo["firstImage"] as Drawable?)
            profilname.text = user!!.firstname + " " + user!!.lastname
        }*/

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SELECT_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    try {
                        val bitmap = MediaStore.Images.Media.getBitmap(activity.contentResolver, data.data)
                        profilpicture.setImageBitmap(bitmap)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(activity, "Canceled", Toast.LENGTH_SHORT).show()
            }
        }
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
}