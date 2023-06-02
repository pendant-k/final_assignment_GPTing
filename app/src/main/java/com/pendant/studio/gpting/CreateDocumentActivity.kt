package com.pendant.studio.gpting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import io.noties.markwon.Markwon

class CreateDocumentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_document)

        // Firestore instance
        val db = Firebase.firestore
        // Get current user info
        val currentUser = Firebase.auth.currentUser

        val question = intent.getStringExtra("question")
        val answer = intent.getStringExtra("answer")

        val questionText = findViewById<TextView>(R.id.create_doc_question)
        questionText.text = question

        val answerText = findViewById<TextView>(R.id.create_doc_answer_body)


        // convert Text to markdown
        val markwon : Markwon = Markwon.create(this);
        markwon.setMarkdown(answerText, answer!!)



        val backBtn = findViewById<ImageButton>(R.id.back_button)
        backBtn.setOnClickListener {
            finish()
        }

        val uploadBtn = findViewById<Button>(R.id.upload_button)
        uploadBtn.setOnClickListener {

            val title = findViewById<EditText>(R.id.create_doc_title_edit).text.toString()
            val memo = findViewById<EditText>(R.id.create_doc_memo_edit).text.toString()

            val doc = Document(title = title, memo = memo, question = question, answer = answer)

            val docRef = db.collection("users").document(currentUser!!.uid)

            docRef.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        Log.d("debug", "DocumentSnapshot data: ${document.id}")

                        docRef.update("docs", FieldValue.arrayUnion(doc)).addOnSuccessListener {
                            finish()
                        }
                            .addOnFailureListener { e ->
                                Log.d("debug", "Can not writing document", e)
                            }
                    } else {
                        Log.d("debug", "No such document")
                        docRef.set(hashMapOf("docs" to listOf<Map<String,Any>>())).addOnSuccessListener {

                            docRef.update("docs", FieldValue.arrayUnion(doc)).addOnSuccessListener {
                                finish()
                            }
                                .addOnFailureListener { e ->
                                    Log.d("debug", "Can not writing document", e)
                                }
                        }
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("debug", "get failed with ", exception)
                }
        }

    }
}