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
import com.lendy.R
import kotlinx.android.synthetic.main.register.*

class RegisterFragment : Fragment() {

    var currentActivity: Activity? = null
    var currentView: View? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.register, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.currentView = view
        this.currentActivity = this.activity
        val cA = this.currentActivity
        subscribe.setOnClickListener {
            if (isEmailValid(emailText.text) && !nameText.text.isNullOrEmpty() && !lastnametext.text.isNullOrEmpty() && !passwordText.text.isNullOrEmpty())
            {
                // Création de l'user en BDD
                // On passe sur la MainActivity
                val user = User()
                user.firstname = nameText.text.toString()
                user.lastname = lastnametext.text.toString()
                user.username = emailText.text.toString()
                user.password = passwordText.text.toString()
                DataManager.SharedData.sharedUser = user
                //DataUtils.writeinArrayOnPreferences("users", DataManager.SharedData.sharedUsers, this.activity)
                startActivity(Intent(this.activity.applicationContext, ChoiceActivity::class.java))
                activity.finish()
            }
            else
            {
                activity.runOnUiThread {
                    AlertDialog.Builder(this.currentActivity)
                            .setTitle("Impossible de valider l'inscription")
                            .setMessage("Veuillez rentrer des informations valides afin de valider l'inscription")
                            .setPositiveButton("OK", null)
                            .show()
                }
            }
        }

    }

    fun isEmailValid(email: CharSequence?): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() && !email.isNullOrEmpty()
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