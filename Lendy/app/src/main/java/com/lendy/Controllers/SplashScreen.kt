package com.lendy.Controllers

import android.app.AlertDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.lendy.Manager.DataManager
import com.lendy.R
import com.lendy.Utils.DataUtils
import kotlinx.android.synthetic.main.connect_activity.*

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        if (DataUtils.readStringFromPreferences(this, "username") == "default" || DataUtils.readStringFromPreferences(this, "password") == "default") {
            startActivity(Intent(this.applicationContext, ConnectActivity::class.java))
            finish()
        } else {
            performRoutine()
        }
    }

    fun performRoutine() {
        //startActivity(Intent(applicationContext, ConnectActivity::class.java))
        //finish()
        DataManager.loginUser(this, DataUtils.readStringFromPreferences(this, "username"), DataUtils.readStringFromPreferences(this, "password"), callback = { success ->
            if (success) {
                //DataUtils.writeStringOnPreferences(this, "address", DataManager.SharedData.sharedUser?.username)
                //DataUtils.writeStringOnPreferences(this, "password", DataManager.SharedData.sharedUser?.password)
                DataUtils.writeStringOnPreferences(this, "token", DataManager.SharedData.sharedUser?.token)
                startActivity(Intent(this.applicationContext, MainActivity::class.java))
                finish()
            } else {
                /*this.runOnUiThread {
                    AlertDialog.Builder(this)
                            .setTitle("Impossible de se connecter")
                            .setMessage("Email ou Mot de passe non valide")
                            .setPositiveButton("OK", null)
                            .show()
                }*/
                startActivity(Intent(this.applicationContext, ConnectActivity::class.java))
                finish()
            }
        })
    }

    override fun onBackPressed() {
        // DO Nothing
    }

}
