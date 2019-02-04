package com.pratice.kotlin.taginwall

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {
    val TAG = HomeFragment::class.java.simpleName
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bt_fab.setOnClickListener {
            Log.d(TAG, "AAA")

            creatRooms()

        }
    }

    private fun creatRooms() {
        val room=LayoutInflater.from(activity).inflate(R.layout.ad_layout,null)
        val edRoomId=room.findViewById (R.id.ed_roomid) as EditText
        val edPassword=room.findViewById(R.id.ed_password) as EditText
//        AlertDialog.Builder(activity!!)
//            .setMessage("Set room id and password")
//            .setView(room)
//            .setPositiveButton("ok"){ _: DialogInterface, _: Int ->
//                val roomId=edRoomId.text.toString()
//                val roomPassword=edPassword.text.toString()
//                firebaseStore.setToFirebase(roomId,roomPassword)
//                findNavController().navigate(R.id.action_homeFragment_to_roomFragment)
//            }
//            .setNegativeButton("cancel", null)
//            .show()
        findNavController().navigate(R.id.action_homeFragment_to_roomFragment)
    }


}