package com.pendant.studio.gpting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var chatAdapter : ChatAdapter
    val data = mutableListOf<ChatData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Get current user information
        val user = Firebase.auth.currentUser
        if(user != null){

            CoroutineScope(Dispatchers.Main).launch{
                // Show Toast Message when user is logged in
                Toast.makeText(this@MainActivity,"Hello ${user.email}",Toast.LENGTH_SHORT).show()
            }
        }

        // bottom Navigator

        loadFragment(ChatFragment())
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        // Add bottom Navigation event
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.bottom_chat_item-> {
                    loadFragment(ChatFragment())
                    true
                }
                R.id.bottom_storage_item-> {
                    loadFragment(StorageFragment())
                    true
                }
                R.id.bottom_setting_item-> {
                    loadFragment(SettingFragment())
                    true
                }
                else -> {
                    false
                }
            }
        }
    }
    private  fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container,fragment)
        transaction.commit()
    }
}