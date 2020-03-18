package com.example.myapp

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import java.util.*

class RegisterActivity : AppCompatActivity() {
    private var CreateAccountButton: Button? = null
    private var InputName: EditText? = null
    private var InputPhoneNumber: EditText? = null
    private var InputPassword: EditText? = null
    private var loadingBar: ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        CreateAccountButton = findViewById<View>(R.id.register_btn) as Button
        InputName = findViewById<View>(R.id.register_username_input) as EditText
        InputPhoneNumber = findViewById<View>(R.id.register_phone_number_input) as EditText
        InputPassword = findViewById<View>(R.id.register_password_input) as EditText
        loadingBar = ProgressDialog(this)
        CreateAccountButton!!.setOnClickListener { CreateAccount() }
    }

    private fun CreateAccount() {
        val name = InputName!!.text.toString()
        val phone = InputPhoneNumber!!.text.toString()
        val password = InputPassword!!.text.toString()
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show()
        } else if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "Please enter your phone number", Toast.LENGTH_SHORT).show()
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show()
        } else {
            loadingBar!!.setTitle("Create Account")
            loadingBar!!.setMessage("Please wait while we are checking the credentials")
            loadingBar!!.setCanceledOnTouchOutside(false)
            loadingBar!!.show()
            ValidatePhoneNumber(name, phone, password)
        }
    }

    private fun ValidatePhoneNumber(name: String, phone: String, password: String) {
        val RootRef: DatabaseReference
        RootRef = FirebaseDatabase.getInstance().reference
        RootRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (!dataSnapshot.child("Users").child(phone).exists()) {
                    val userdataMap = HashMap<String, Any>()
                    userdataMap["phone"] = phone
                    userdataMap["password"] = password
                    userdataMap["name"] = name
                    RootRef.child("Users").child(phone).updateChildren(userdataMap)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(this@RegisterActivity, "Congratulation, your account has been created ", Toast.LENGTH_SHORT).show()
                                    loadingBar!!.dismiss()
                                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                                    startActivity(intent)
                                } else {
                                    loadingBar!!.dismiss()
                                    Toast.makeText(this@RegisterActivity, "Error, please try again later", Toast.LENGTH_SHORT).show()
                                }
                            }
                } else {
                    Toast.makeText(this@RegisterActivity, "This " + phone + "already exists", Toast.LENGTH_SHORT).show()
                    loadingBar!!.dismiss()
                    Toast.makeText(this@RegisterActivity, "Please try again with another phone number", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                    startActivity(intent)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }
}