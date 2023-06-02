package com.pendant.studio.gpting

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch



class DocumentAdapter(private var documents: MutableList<Document>,private val context: Context) : RecyclerView.Adapter<DocumentAdapter.ViewHolder>() {

    fun updateData(newDocuments: MutableList<Document>) {
        documents = newDocuments
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.document_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val document = documents[position]
        holder.bind(document)
        val removeBtn = holder.itemView.findViewById<ImageButton>(R.id.document_remove)
        removeBtn.setOnClickListener {
            val db = Firebase.firestore
            val currentUser = Firebase.auth.currentUser
            val userRef = db.collection("users").document(currentUser!!.uid)
            userRef.update("docs",FieldValue.arrayRemove(document)).addOnSuccessListener {
                documents.remove(document)
                updateData(documents)
                notifyDataSetChanged()
                CoroutineScope(Dispatchers.Main).launch {
                    Toast.makeText(context, "remove document successfully",Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener { e -> Log.d("dubeg","Cannot remove array item") }
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(context,ReadDocumentActivity::class.java)
            context.startActivity(intent.apply {
                putExtra("question",document.question)
                putExtra("answer", document.answer)
                putExtra("title", document.title)
                putExtra("memo", document.memo)
                putExtra("docId",document.docId)
            })
        }
    }

    override fun getItemCount(): Int {
        return documents.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.document_title_text)
        private val questionTextView: TextView = itemView.findViewById(R.id.document_question_textView)

        fun bind(document: Document) {
            titleTextView.text = document.title
            questionTextView.text = document.question
        }
    }
}
