package com.pendant.studio.gpting.singleton

import com.pendant.studio.gpting.ChatData

// Singleton instance to store ChatList data
object ChatListHolder {
    private val myList: MutableList<ChatData> = mutableListOf()

    fun getList(): MutableList<ChatData> {
        return myList
    }

    fun addItem(item: ChatData) {
        myList.add(item)
    }

    // Function for remove List
    fun reset(){
        myList.clear()
    }
}