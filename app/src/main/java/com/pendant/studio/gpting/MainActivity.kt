package com.pendant.studio.gpting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    lateinit var chatAdapter : ChatAdapter
    val data = mutableListOf<ChatData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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