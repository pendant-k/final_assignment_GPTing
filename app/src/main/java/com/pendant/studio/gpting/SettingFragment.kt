package com.pendant.studio.gpting

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.IntentCompat
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage

class SettingFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Get logged in user information
        val currentUser = Firebase.auth.currentUser

        // Get storage ref for user
        val storage = Firebase.storage
        val storageRef = storage.reference
        var profileRef: StorageReference? = storageRef.child("profiles/${currentUser!!.uid}.jpg")



        val view = inflater.inflate(R.layout.fragment_setting, container, false)
        val userEmailText = view.findViewById<TextView>(R.id.user_email_textView)
        if(currentUser != null){
            userEmailText.text = currentUser.email
        } else {
            userEmailText.text = "Not logged in yet"
        }

        val logoutBtn = view.findViewById<Button>(R.id.logout_button)
        // Add Logout Event to logoutBtn
        logoutBtn.setOnClickListener {
            AlertDialog.Builder(container!!.context)
                .setTitle("Alert")
                .setMessage("Are you sure to Logout?")
                .setPositiveButton("ok", object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface, which: Int) {
                        AuthUI.getInstance()
                            .signOut(container!!.context)
                            .addOnCompleteListener {
                                val intent = Intent(container!!.context, LoginActivity::class.java);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(intent)
                            }
                    }
                })
                .setNegativeButton("cancel", object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface, which: Int) {
                        null
                    }
                })
                .create()
                .show()
        }
        return view
    }
}