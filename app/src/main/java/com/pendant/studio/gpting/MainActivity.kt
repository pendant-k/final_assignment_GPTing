package com.pendant.studio.gpting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
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

//    val recyclerView = findViewById<RecyclerView>(R.id.chatlist_recycleView)
//
//    private fun initRecycler(){
//        chatAdapter = ChatAdapter(this)
//        recyclerView.adapter = chatAdapter
//
//        data.apply {
//            add(ChatData(question = "Hello", answer = "Hello, How can I assist you today?"))
//            add(ChatData(question = "Hi", answer = "Hello, How can I assist you today?\nI can help you"))
//        }
//    }

}