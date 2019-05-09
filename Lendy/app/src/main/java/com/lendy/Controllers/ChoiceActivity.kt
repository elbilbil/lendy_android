package com.lendy.Controllers

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.lendy.Manager.DataManager
import com.lendy.R
import com.lendy.Utils.DataUtils
import com.lmntrx.android.library.livin.missme.ProgressDialog
import kotlinx.android.synthetic.main.choice_screen.*

class ChoiceActivity : AppCompatActivity() {

    private lateinit var progressdialog: ProgressDialog;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.choice_screen)

        progressdialog = ProgressDialog(this)

        propose.setOnClickListener {
            DataUtils.progressShow(progressdialog, "Veuillez patienter", false)

            val i = Intent(this.applicationContext, MainActivity::class.java)
            i.putExtra("key", "propose")
            DataManager.SharedData.sharedUser?.type = "preteur"
            DataManager.registerUser(this, DataManager.SharedData.sharedUser, callback = { success, newuser ->
                this.runOnUiThread {
                    DataUtils.progressHide(progressdialog)
                }
                if (success && newuser != null) {
                    DataUtils.writeStringOnPreferences(this, "token", newuser.token)
                    DataUtils.writeStringOnPreferences(this, "username", DataManager.SharedData.sharedUser?.username)
                    DataUtils.writeStringOnPreferences(this, "password", DataManager.SharedData.sharedUser?.password)
                    startActivity(i)
                    finish()
                } else
                    Log.e("Register FAILED", "FAILED")
            })
        }

        recherche.setOnClickListener {
            DataUtils.progressShow(progressdialog, "Veuillez patienter", false)

            val i = Intent(this.applicationContext, MainActivity::class.java)
            i.putExtra("key", "search")
            DataManager.SharedData.sharedUser?.type = "emprunteur"
            DataManager.registerUser(this, DataManager.SharedData.sharedUser, callback = { success, newuser ->
                this.runOnUiThread {
                    DataUtils.progressHide(progressdialog)
                }
                if (success && newuser != null) {
                    DataUtils.writeStringOnPreferences(this, "token", newuser.token)
                    DataUtils.writeStringOnPreferences(this, "username", DataManager.SharedData.sharedUser?.username)
                    DataUtils.writeStringOnPreferences(this, "password", DataManager.SharedData.sharedUser?.password)
                    startActivity(i)
                    finish()
                } else
                    Log.e("Register FAILED", "FAILED")
            })
        }

    }

    override fun onBackPressed() {
        // DO Nothing
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}
