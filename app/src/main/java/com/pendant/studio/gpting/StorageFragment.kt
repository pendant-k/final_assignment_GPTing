package com.pendant.studio.gpting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pendant.studio.gpting.singleton.ChatListHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class StorageFragment : Fragment() {
    var documentList : MutableList<Document> = mutableListOf<Document>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_storage, container, false)

        fun mapToDocument(map: HashMap<String, Any>): Document {
            val question = map["question"] as? String
            val answer = map["answer"] as? String
            val title = map["title"] as? String
            val memo = map["memo"] as? String
            val docId = map["docId"] as? String ?: UUID.randomUUID().toString()

            // Create and return a new instance of your Document class
            return Document(question, answer, title, memo, docId)
        }
        // Get current User's uid
        val currentUser = Firebase.auth.currentUser

        val db = Firebase.firestore
        val userRef = db.collection("users").document(currentUser!!.uid)

        // Get docs from Firestore



        val progressBar = view.findViewById<ProgressBar>(R.id.storage_progress_circular)
        progressBar.isVisible = true;

        // Set recyclerView layout
        val recyclerView = view.findViewById<RecyclerView>(R.id.document_list_recycleView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val adapter= DocumentAdapter(documentList,requireContext())
        recyclerView.adapter = adapter


        userRef.get().addOnSuccessListener { document ->
            val docsArray = document["docs"] as? ArrayList<HashMap<String,Any>>
            docsArray?.let {
                for(map in it){
                    val document = mapToDocument(map)
                    documentList.add(document)
                }
            }
            adapter.updateData(documentList)
            adapter.notifyDataSetChanged()
            CoroutineScope(Dispatchers.Main).launch {
                progressBar.isVisible = false
            }
        }

        return view

    }
}