package com.pendant.studio.gpting

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.noties.markwon.Markwon

class ChatAdapter(private val context: Context) : RecyclerView.Adapter<ChatAdapter.ViewHolder>() {
    var data = mutableListOf<ChatData>()


    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.chat_item,parent,false)
        // return ViewHolder
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = data[position]
        holder.bind(currentItem)
        val saveButton = holder.itemView.findViewById<ImageButton>(R.id.save_button)
        saveButton.setOnClickListener {

            AlertDialog.Builder(context)
                .setTitle("Save")
                .setMessage("Do you want to save this document?")
                .setPositiveButton("ok", object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface, which: Int) {
                        // TODO : implement start Create Doc Activity
                        val intent = Intent(context,CreateDocumentActivity::class.java)
                        context.startActivity(intent.apply {
                            putExtra("question", currentItem.question)
                            putExtra("answer", currentItem.answer)
                        })
                    }
                })
                .setNegativeButton("cancel", object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface, which: Int) {
                        null
                    }
                })
                .create()
                .show()
        }
    }


    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        // question item
        private val questionText : TextView = itemView.findViewById(R.id.question_textView)
        // answer item
        private val answerText : TextView = itemView.findViewById(R.id.answer_textView)

        fun bind(item: ChatData){

            questionText.text = item.question
//            answerText.text = item.answer

            val markwon : Markwon = Markwon.create(context);
            markwon.setMarkdown(answerText, item.answer)
        }
    }

}