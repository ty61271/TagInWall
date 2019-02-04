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

    fun getAdater(state: Boolean): FirestoreRecyclerAdapter<FriendData, FriendHolder> {
        val db = FirebaseFirestore.getInstance().collection("users").document("$userId").collection("friends")
        val query = FirebaseFirestore.getInstance().collection("users").document("$userId").collection("friends")
            .whereEqualTo("state", state)
        val addOptions = FirestoreRecyclerOptions.Builder<FriendData>()
            .setQuery(query, FriendData::class.java)
            .build()
        var adater: FirestoreRecyclerAdapter<FriendData, FriendHolder> =
            object : FirestoreRecyclerAdapter<FriendData, FriendHolder>(addOptions) {
                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendHolder {
                    val view = LayoutInflater.from(mActivity).inflate(R.layout.friend_item_icon, parent, false)
                    return FriendHolder(view)
                }

                override fun onBindViewHolder(friendHolder: FriendHolder, position: Int, friendData: FriendData) {
//                傾聽user的數據改變
                    friendData.from!!.addSnapshotListener { documentSnapshot, e ->
                        val userData = documentSnapshot?.toObject(UserData::class.java)
                        friendHolder.itemView.friend_info.text = "${userData?.uid}\t:\n${userData?.name}"
                        if (state) {
                            friendHolder.itemView.btn_add_friend.text = "delete"
                            friendHolder.itemView.btn_add_friend.setOnClickListener {
                                db.document("${userData?.uid}").delete()
                            }
                        } else {
                            friendHolder.itemView.btn_add_friend.setOnClickListener {

                                db.document("${userData?.uid}").update("state", true)

                            }
                        }

                    }
                }
            }
        return adater

    }
}

class FriendHolder(itemView: View) : RecyclerView.ViewHolder(itemView)