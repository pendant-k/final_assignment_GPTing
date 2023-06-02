package com.pendant.studio.gpting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import io.noties.markwon.Markwon

class ReadDocumentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_document)

        val db = Firebase.firestore

        val currentUser = Firebase.auth.currentUser

        val title = intent.getStringExtra("title")
        val docId = intent.getStringExtra("docId")
        val question = intent.getStringExtra("question")
        val answer = intent.getStringExtra("answer")
        val memo = intent.getStringExtra("memo")



        val questionText = findViewById<TextView>(R.id.read_doc_question)
        val answerText = findViewById<TextView>(R.id.read_doc_answer_body)
        val editTitle = findViewById<EditText>(R.id.read_doc_title_edit)
        val editMemo = findViewById<EditText>(R.id.read_doc_memo_edit)

        editTitle.setText(title)
        editMemo.setText(memo)
        questionText.text = question
        val markwon : Markwon = Markwon.create(this)
        markwon.setMarkdown(answerText,answer!!)

        val backBtn = findViewById<ImageButton>(R.id.read_back_button)
        backBtn.setOnClickListener {
            finish()
        }

        val updateBtn = findViewById<Button>(R.id.update_button)
        updateBtn.setOnClickListener {

            val existDoc = Document(title=title, docId = docId!!, memo = memo, question = question, answer = answer)
            val existDocRef = db.collection("users").document(currentUser!!.uid)
            existDocRef.update(
                "docs",FieldValue.arrayRemove(existDoc)
            ).addOnSuccessListener {
                val doc = Document(title=editTitle.text.toString(), docId = docId!!, memo = editMemo.text.toString(), question = question, answer = answer)
                val docRef = db.collection("users").document(currentUser!!.uid)
                docRef.update(
                    "docs",FieldValue.arrayUnion(doc)
                ).addOnSuccessListener {
                    finish()
                }
            }

        }

    }
}