package com.pendant.studio.gpting

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        holder.bind(data[position])
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