package com.pendant.studio.gpting

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class ChatFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter : ChatAdapter
    private lateinit var data : MutableList<ChatData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("fragment","Fragment init")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_chat, container, false)

        val sendButton = view.findViewById<Button>(R.id.send_button)
        val chatEditText = view.findViewById<EditText>(R.id.chat_editText)

        sendButton.setOnClickListener {
            
        }

    recyclerView = view.findViewById(R.id.chatlist_recycleView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter= ChatAdapter(requireContext())
        recyclerView.adapter = adapter
        data = mutableListOf(ChatData(question = "Good to see you", answer = "Hello, How can I assist you today?")
        ,ChatData(question = "Hi", answer = "Hello, How can I assist you today?\nI can help you"))

        adapter.data = data
        adapter.notifyDataSetChanged()
        return view
    }

}