package com.pendant.studio.gpting

import java.util.UUID

data class Document(
    val question : String? = null,
    val answer : String? = null,
    val title : String? = null,
    val memo : String? = null,
    val docId : String = UUID.randomUUID().toString(),
) {
}
