package com.pratice.kotlin.taginwall

import com.google.firebase.firestore.DocumentReference

data class FriendData(val from: DocumentReference?=null,var state:Boolean=false)