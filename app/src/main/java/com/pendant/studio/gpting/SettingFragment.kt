package com.pendant.studio.gpting

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

class SettingFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val currentUser = Firebase.auth.currentUser

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
            AuthUI.getInstance()
                .signOut(container!!.context)
                .addOnCompleteListener {
                     val intent = Intent(container!!.context, LoginActivity::class.java);
                     intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                     intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                     startActivity(intent)
                }
        }
        return view
    }
}