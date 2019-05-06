package com.lendy.Controllers

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.lendy.Manager.DataManager
import com.lendy.R
import com.lendy.Utils.DataUtils
import kotlinx.android.synthetic.main.choice_screen.*

class ChoiceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.choice_screen)

        propose.setOnClickListener {
            val i = Intent(this.applicationContext, MainActivity::class.java)
            i.putExtra("key", "propose")
            DataManager.SharedData.sharedUser?.type = "preteur"
            DataManager.registerUser(this, DataManager.SharedData.sharedUser, callback = { success, newuser ->
                if (success && newuser != null) {
                    DataUtils.writeStringOnPreferences(this, "token", newuser.token)
                    startActivity(i)
                    finish()
                }
                else
                    Log.e("bad", "bad")
            })
        }

        recherche.setOnClickListener {
            val i = Intent(this.applicationContext, MainActivity::class.java)
            i.putExtra("key", "search")
            DataManager.SharedData.sharedUser?.type = "emprunteur"
            DataManager.registerUser(this, DataManager.SharedData.sharedUser, callback = { success, newuser ->
                if (success && newuser != null) {
                    DataUtils.writeStringOnPreferences(this, "token", newuser.token)
                    startActivity(i)
                    finish()
                }
                else
                    Log.e("bad", "bad")
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
