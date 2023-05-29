package com.pendant.studio.gpting

data class ChatData(
    val question : String,
    val answer : String,
)

data class ChatResponseData(
    val success : Boolean,
    val answer : String?
)
