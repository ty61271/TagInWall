package com.pratice.kotlin.taginwall

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    val TAG = LoginActivity::class.java.simpleName
    val db = FirebaseFirestore.getInstance().collection("users")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        showError()
    }

    private fun showError() {
        ed_uid.addTextChangedListener {
            setError(it!!, til_uid)
        }
        ed_name.addTextChangedListener {
            setError(it!!, til_name)
        }
    }

    private fun setError(ediText: Editable, tilLayout: TextInputLayout) {
        if (ediText.isBlank()) {
            tilLayout.error = "cant not null"
        } else {
            tilLayout.error = ""
        }
    }

    fun login(view: View) {
        val loginUid = ed_uid.text.toString()
        val loginName = ed_name.text.toString()
        if (loginUid.isBlank() || loginName.isBlank()) {
            AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage("Please check id and name")
                .setPositiveButton("OK", null)
                .show()
        } else {
            checkUserExists(loginUid, loginName)
        }
    }

    private fun checkUserExists(loginUid: String, loginName: String) {

        db.document(loginUid).get().addOnCompleteListener {
            if (it.isSuccessful) {
                val dc = it.result
                if (dc!!.exists()) {
                    AlertDialog.Builder(this)
                        .setTitle("Error")
                        .setMessage("The Uid is have be added ,please change other")
                        .setPositiveButton("OK", null)
                        .show()
                } else {
                    val userData = UserData(loginUid, loginName)
                    writeUserToFirebase(loginUid, userData)
                    setPreferences()
                    setResult(Activity.RESULT_OK)
                    finish()
                }
            }
        }
    }

    private fun writeUserToFirebase(loginUid: String, userData: UserData) {
        db.document(loginUid).set(userData).addOnSuccessListener {
            Log.d(TAG, "加入成功")
        }
    }

    private fun setPreferences() {
        getSharedPreferences("user", Context.MODE_PRIVATE).edit()
            .putString(MainActivity.USER_UID, ed_uid.text.toString())
            .apply()
    }
}
