package com.pendant.studio.gpting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import io.noties.markwon.Markwon

class CreateDocumentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_document)

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

    }
}