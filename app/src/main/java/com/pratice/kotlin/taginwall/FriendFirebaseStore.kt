package com.pratice.kotlin.taginwall

import android.content.Context
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.MetadataChanges
import com.google.firebase.firestore.QuerySnapshot

class FriendFirebaseStore(val mAcivity: Context, val userId: String?) {
    val TAG = FriendFirebaseStore::class.java.simpleName
    val db = FirebaseFirestore.getInstance().collection("users")
    private lateinit var userData: UserData

    fun readFirebaseRealtime(
        tvUserId: TextView,
        tvUserName: TextView
    ) {
        db.addSnapshotListener(MetadataChanges.INCLUDE) { querySnapshot: QuerySnapshot?, e: FirebaseFirestoreException? ->
            querySnapshot?.forEach {
                if (it.id.contains(userId!!)) {
                    Log.d(TAG, "已存在 ${it.data}")
                    userData = it.toObject(UserData::class.java)
                    tvUserId.text = userData.uid
                    tvUserName.text = userData.name

                }

            }
        }
    }

    fun setNickNameToFirebase(textEdit: String) = db.document("$userId").update("name", textEdit)

    fun setFriendData(textEdit: String) = FriendData(db.document("$textEdit"), true)

    fun setMyFriendData() = FriendData(db.document("$userId"), false)

    fun setFriendToFirebase(textEdit: String) {

        db.document(textEdit).get().addOnSuccessListener {
            if (it.id == userId) {
                Toast.makeText(mAcivity, "cant add self", Toast.LENGTH_LONG).show()
            } else {
                if (it.exists()) {
                    val friendData = setFriendData(textEdit)
                    val myFriendData = setMyFriendData()
                    setMyFriendCollection(userId!!, textEdit, friendData)
                    setMyFriendCollection(textEdit, userId!!, myFriendData)
                }
            }
            Log.d(TAG, "加入成功")
        }
    }

    fun setMyFriendCollection(userDoc: String, friendDoc: String, friendData: FriendData) =
        db.document("$userDoc").collection("friends")
            .document(friendDoc).set(friendData).addOnSuccessListener {
                Log.d(TAG, "加入我的完成")
            }

}




