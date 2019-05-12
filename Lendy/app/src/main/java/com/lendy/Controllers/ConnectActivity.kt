package com.lendy.Controllers

import android.app.AlertDialog
import android.app.Fragment
import android.app.FragmentManager
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.lendy.Manager.DataManager
import com.lendy.R
import com.lendy.Utils.DataUtils
import com.lendy.Utils.fragments.RegisterFragment
import com.lmntrx.android.library.livin.missme.ProgressDialog
import kotlinx.android.synthetic.main.connect_activity.*

class ConnectActivity : AppCompatActivity() {
    private var listOfFragment = arrayListOf<Any>()
    private lateinit var progressdialog: ProgressDialog;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.connect_activity)

        progressdialog = ProgressDialog(this)

        // Click sur le message "Pas de compte"
        noaccount.setOnClickListener {
            addFragmentToActivity(fragmentManager, RegisterFragment(), R.id.connect)
        }

        // Click sur le bouton Connexion
        connexion.setOnClickListener {
            DataUtils.progressShow(progressdialog, "Connexion en cours", false)

            if (!emailEdit.text.isNullOrEmpty() && !passwordEdit.text.isNullOrEmpty()) {
                DataManager.loginUser(this, emailEdit.text.toString(), passwordEdit.text.toString(), callback = { success ->
                    this.runOnUiThread {
                        DataUtils.progressHide(progressdialog)
                    }
                    if (success) {
                        DataUtils.writeStringOnPreferences(this, "username", DataManager.SharedData.sharedUser?.username)
                        DataUtils.writeStringOnPreferences(this, "password", DataManager.SharedData.sharedUser?.password)
                        startActivity(Intent(this.applicationContext, MainActivity::class.java))
                        finish()
                    } else {
                        this.runOnUiThread {
                            AlertDialog.Builder(this)
                                    .setTitle("Impossible de se connecter")
                                    .setMessage("Email ou Mot de passe non valide")
                                    .setPositiveButton("OK", null)
                                    .show()
                        }
                    }
                })
            } else {
                DataUtils.progressHide(progressdialog)
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

    fun removeFragment(fragment: Fragment?) {
        if (fragment == null) {
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
