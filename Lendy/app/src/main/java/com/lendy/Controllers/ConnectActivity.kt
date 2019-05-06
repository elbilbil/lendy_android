package com.lendy.Controllers

import android.app.AlertDialog
import android.app.Fragment
import android.app.FragmentManager
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.lendy.Manager.DataManager
import com.lendy.Manager.ServiceProvider
import com.lendy.R
import com.lendy.Utils.DataUtils
import com.lendy.Utils.RegisterFragment
import kotlinx.android.synthetic.main.connect_activity.*

class ConnectActivity : AppCompatActivity() {
    var listOfFragment = arrayListOf<Any>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.connect_activity)


        // Click sur le message "Pas de compte"
        noaccount.setOnClickListener {
            addFragmentToActivity(fragmentManager, RegisterFragment(), R.id.connect)
        }


        // Click sur le bouton Connexion
        connexion.setOnClickListener {
            if (!emailEdit.text.isNullOrEmpty() && !passwordEdit.text.isNullOrEmpty())
            {
                DataManager.loginUser(this, emailEdit.text.toString(), passwordEdit.text.toString(), callback = { success ->
                    if (success)
                    {
                        DataUtils.writeStringOnPreferences(this, "address", DataManager.SharedData.sharedUser?.username)
                        DataUtils.writeStringOnPreferences(this, "password", DataManager.SharedData.sharedUser?.password)
                        startActivity(Intent(this.applicationContext, MainActivity::class.java))
                        finish()
                    }
                    else {
                        this.runOnUiThread {
                            AlertDialog.Builder(this)
                            .setTitle("Impossible de se connecter")
                            .setMessage("Email ou Mot de passe non valide")
                            .setPositiveButton("OK", null)
                            .show()
                        }
                    }
                })
            }
            else
            {
                AlertDialog.Builder(this)
                .setTitle("Impossible de se connecter")
                .setMessage("Veuillez rentrer des informations valides")
                .setPositiveButton("OK", null)
                .show()
            }
        }
    }

    fun addFragmentToActivity(manager: FragmentManager, fragment: Fragment?, frameId: Int) {

        if (fragment == null) {
            return;
        }

        val transaction = manager.beginTransaction()
        transaction.add(frameId, fragment)
        transaction.addToBackStack(null);
        transaction.commitAllowingStateLoss()
        listOfFragment.add(fragment)
    }

    fun removeFragment(fragment: Fragment?)
    {
        if (fragment == null)
        {
            return
        }

        val transaction = fragment.fragmentManager.beginTransaction()
        transaction.remove(fragment)
        transaction.commitAllowingStateLoss()
        listOfFragment.remove(fragment)
    }

    override fun onBackPressed() {
        // DO Nothing
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}
