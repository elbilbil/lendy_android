package com.lendy.Utils.custom_views

import android.app.Activity
import androidx.appcompat.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.lendy.Manager.DataManager
import com.lendy.Models.Users

import com.lendy.R

class SendMessageDialog(activity1: Activity, user1: Users?) : AlertDialog.Builder(activity1) {
    private val view: View
    private var alertDialog: AlertDialog? = null
    private var user: Users? = null
    private var activity: Activity? = null

    init {
        view = LayoutInflater.from(context).inflate(R.layout.new_message_dialog, null)
        user = user1
        activity = activity1
        this.setView(view)
    }

    override fun show(): AlertDialog? {
        alertDialog = super.show()
        val validate = view.findViewById<TextView>(R.id.validate)
        val errormessage = view.findViewById<TextView>(R.id.errormessage)
        val message = view.findViewById<EditText>(R.id.message)

        validate.setOnClickListener { v ->
            activity!!.runOnUiThread {
                errormessage.visibility = View.GONE
            }
            if (message.text.isNotEmpty() && user != null) {
                DataManager.sendMessage(context, DataManager.SharedData.token, message.text.toString(), user?._id, callback = { success: Boolean ->
                    if (success) {
                        this.alertDialog!!.dismiss()
                        activity!!.runOnUiThread {
                            android.app.AlertDialog.Builder(activity)
                            .setTitle("Message envoyé avec succès")
                            .setMessage("Votre message à bien été envoyé à " + user!!.firstname + " " + user!!.lastname)
                            .setPositiveButton("OK", null)
                            .show()
                        }
                    } else {
                        activity!!.runOnUiThread {
                            errormessage.text = "Une erreur est survenue, veuillez réessayer"
                            errormessage.visibility = View.VISIBLE
                        }
                    }
                })
            } else {
                activity!!.runOnUiThread {
                    errormessage.text = "Message non valide"
                    errormessage.visibility = View.VISIBLE
                }
            }
        }
        return alertDialog
    }
}