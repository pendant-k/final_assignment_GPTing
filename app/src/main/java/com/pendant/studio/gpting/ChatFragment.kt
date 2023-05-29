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
import com.google.gson.Gson
import com.pendant.studio.gpting.singleton.ChatListHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

// Class for Networking
class NetworkManager {
    private val client: OkHttpClient = OkHttpClient()

    // Function for make HTTP Request using OkHttp
    fun makePostRequest(url: String, question: String , callback: Callback) {
        val gson = Gson()
        val data = HashMap<String, String>()
        data["question"] = question
        val json = gson.toJson(data)
        // set json Media Type
        val jsonMediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
        val requestBody = json.toRequestBody(jsonMediaType)

        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(callback)
    }
}

class ChatFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter : ChatAdapter

    // network manager Instance
    private lateinit var networkManager : NetworkManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("fragment","Fragment init")

        networkManager = NetworkManager()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_chat, container, false)

        val sendButton = view.findViewById<Button>(R.id.send_button)
        val chatEditText = view.findViewById<EditText>(R.id.chat_editText)

        // TODO : Create Request Body

        sendButton.setOnClickListener {
            val chatText = chatEditText.text.toString()

            // Check is empty string
            if(chatText != ""){
                networkManager.makePostRequest("https://us-central1-mobile-prc.cloudfunctions.net/app/api", question = chatText  ,object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        Log.d("response",e.toString())
                    }

                    override fun onResponse(call: Call, response: Response) {
                        // Create Gson instance
                        val gson = Gson()
                        val responseBody = response.body?.string()
                        // Get data with data class
                        val dataObject = gson.fromJson(responseBody, ChatResponseData::class.java)
                        // Test response
                        Log.d("response",dataObject.answer.toString())

                        // save it with question to chat list
                        var newData = ChatData(question = chatText , answer = dataObject.answer.toString())
                        ChatListHolder.addItem(newData)

                        CoroutineScope(Dispatchers.Main).launch {
                            adapter.data = ChatListHolder.getList()
                            adapter.notifyDataSetChanged()
                        }
                    }

                })
            } else {
                // TODO : Toast Message -> Empty String Warning
            }
        }

    recyclerView = view.findViewById(R.id.chatlist_recycleView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter= ChatAdapter(requireContext())
        recyclerView.adapter = adapter

        adapter.data = ChatListHolder.getList()
        adapter.notifyDataSetChanged()
        return view
    }

}