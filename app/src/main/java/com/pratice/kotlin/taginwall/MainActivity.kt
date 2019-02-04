package com.pratice.kotlin.taginwall

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*

class MainActivity : AppCompatActivity() {
    val RC_CODE = 100
    companion object {
        var USER_NAME = "name"
        var USER_UID = "uid"
        var USER_FRIEDN_STATE="friend_state"
    }

    val TAG = MainActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val userName = getSharedPreferences("user", Context.MODE_PRIVATE)
            .getString(USER_UID, null)
        if (userName == null) {
            startActivityForResult(Intent(this, LoginActivity::class.java), RC_CODE)
        }

//        setNickName(userName)
        setNavigation()



    }

//    private fun setNickName(userName: String?) {
//        val db = FirebaseFirestore.getInstance().collection("users").document(userName!!)
//        db.addSnapshotListener { documentSnapshot, exception ->
//            if (documentSnapshot != null && documentSnapshot.exists()) {
//                val user = documentSnapshot.toObject(UserData::class.java)
//                tv_name.text = user?.name
//            }
//        }
//    }

    private fun setNavigation() {
        val control = findNavController(R.id.host_nav)
        bt_nav.setupWithNavController(control)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_CODE) {
            if (resultCode != Activity.RESULT_OK) {
                finish()
            }else{

            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.host_nav).navigateUp()
    }
}
