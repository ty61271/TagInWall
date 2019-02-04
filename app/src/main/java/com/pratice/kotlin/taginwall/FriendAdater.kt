package com.pratice.kotlin.taginwall

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.friend_item_icon.view.*

class FriendAdater(val mActivity: Context, val userId: String?) {

    val TAG = FriendAdater::class.java.simpleName

    val query = FirebaseFirestore.getInstance().collection("users").document("$userId").collection("friends")
    val options = FirestoreRecyclerOptions.Builder<FriendData>()
        .setQuery(query, FriendData::class.java)
        .build()
    var adater: FirestoreRecyclerAdapter<FriendData, FriendHolder> =
        object : FirestoreRecyclerAdapter<FriendData, FriendHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendHolder {
                val view = LayoutInflater.from(mActivity).inflate(R.layout.friend_item_icon, parent, false)
                return FriendHolder(view)
            }

            override fun onBindViewHolder(friendHolder: FriendHolder, position: Int, friendData: FriendData) {
//                傾聽user的數據改變
                friendData.from!!.addSnapshotListener { documentSnapshot, e ->
                    val userData = documentSnapshot?.toObject(UserData::class.java)
                    friendHolder.itemView.friend_info.text = "${userData?.uid}\t:\t${userData?.name}"
                }
            }
        }
}

class FriendHolder(itemView: View) : RecyclerView.ViewHolder(itemView)