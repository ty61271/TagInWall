package com.pratice.kotlin.taginwall

import com.google.firebase.firestore.DocumentReference

data class RoomData(
    val from: DocumentReference? = null,
    val roomId: String,
    val roomPassword: String,
    val state: Int = 0
)