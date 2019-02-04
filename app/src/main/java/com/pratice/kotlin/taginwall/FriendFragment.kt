package com.pratice.kotlin.taginwall

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.pratice.kotlin.taginwall.MainActivity.Companion.USER_UID
import kotlinx.android.synthetic.main.fragment_friend.*

class FriendFragment : Fragment() {

    val TAG = FriendFragment::class.java.simpleName
    private lateinit var mActivity: Context
    private var userId: String? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_friend, container, false)
    }

    private lateinit var friendFirebaseStore: FriendFirebaseStore

    private lateinit var friendAdater: FriendAdater

    private lateinit var addAdater: FirestoreRecyclerAdapter<FriendData, FriendHolder>

    private lateinit var isFriendAdater: FirestoreRecyclerAdapter<FriendData, FriendHolder>

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        userId = mActivity.getSharedPreferences("user", MODE_PRIVATE).getString(USER_UID, null)
        friendFirebaseStore = FriendFirebaseStore(mActivity, userId)

        setNickName()
        setAddFriend()

        friendAdater = FriendAdater(mActivity, userId)
        freind_add_recycle.setHasFixedSize(true)
        freind_add_recycle.layoutManager = LinearLayoutManager(mActivity)
        addAdater = friendAdater.getAdater(false)
        freind_add_recycle.adapter = addAdater

        friend_recycler.setHasFixedSize(true)
        friend_recycler.layoutManager=LinearLayoutManager(mActivity)
        isFriendAdater=friendAdater.getAdater(true)
        friend_recycler.adapter=isFriendAdater

    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context

    }

    override fun onStart() {
        super.onStart()
        friendFirebaseStore.readFirebaseRealtime(tv_userid, tv_name)
        addAdater.startListening()
        isFriendAdater.startListening()

    }

    private fun setNickName() {
        tv_name.setOnClickListener {
            showAlertDialog("Nick name", R.id.tv_name)
        }
    }

    private fun setAddFriend() {
        fab_add_friend.setOnClickListener {
            showAlertDialog("Add friend", R.id.fab_add_friend)
        }
    }

    @SuppressLint("RestrictedApi")
    fun showAlertDialog(title: String, resBtn: Int) {
        val textInputLayout = TextInputLayout(mActivity)
        val textInputEdit = TextInputEditText(mActivity)
        textInputEdit.addTextChangedListener {
            if (textInputEdit.text.toString().isBlank()) {
                textInputLayout.error = "cant not null"
            } else {
                textInputLayout.error = ""
            }
        }
        with(textInputLayout) {
            hint = title
            addView(textInputEdit)
            isErrorEnabled = true
        }
        AlertDialog.Builder(mActivity)
            .setTitle(title)
            .setMessage("Set yours ${title.toLowerCase()}")
            .setView(textInputLayout, 20, 20, 20, 20)
            .setPositiveButton("ok") { _: DialogInterface, _: Int ->
                val textEdit = textInputEdit.text.toString()
                if (!textEdit.isBlank()) {
                    when (resBtn) {
                        R.id.tv_name -> friendFirebaseStore.setNickNameToFirebase(textEdit)
                        R.id.fab_add_friend -> friendFirebaseStore.setFriendToFirebase(textEdit)
                    }
                }
            }
            .show()
    }
}