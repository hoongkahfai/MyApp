package com.example.myapp

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapp.Model.Users
import com.example.myapp.Prevalent.Prevalent
import com.google.firebase.database.*
import com.rey.material.widget.CheckBox
import io.paperdb.Paper

class LoginActivity : AppCompatActivity() {
    private var InputPhoneNumber: EditText? = null
    private var InputPassword: EditText? = null
    private var LoginButton: Button? = null
    private var loadingBar: ProgressDialog? = null
    private var AdminLink: TextView? = null
    private var NotAdminLink: TextView? = null
    private var parentDbName = "Users"
    private var chkBoxRememberMe: CheckBox? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        LoginButton = findViewById<View>(R.id.login_btn) as Button
        InputPassword = findViewById<View>(R.id.login_password_input) as EditText
        InputPhoneNumber = findViewById<View>(R.id.login_phone_number_input) as EditText
        AdminLink = findViewById<View>(R.id.admin_panel_link) as TextView
        NotAdminLink = findViewById<View>(R.id.not_admin_panel_link) as TextView
        loadingBar = ProgressDialog(this)
        chkBoxRememberMe = findViewById<View>(R.id.remember_me_chkb) as CheckBox
        Paper.init(this)
        LoginButton!!.setOnClickListener { LoginUser() }
        AdminLink!!.setOnClickListener {
            LoginButton!!.text = "Login Admin"
            AdminLink!!.visibility = View.INVISIBLE
            NotAdminLink!!.visibility = View.VISIBLE
            parentDbName = "Admins"
        }
        NotAdminLink!!.setOnClickListener {
            LoginButton!!.text = "Login"
            AdminLink!!.visibility = View.VISIBLE
            NotAdminLink!!.visibility = View.INVISIBLE
            parentDbName = "Users"
        }
    }

    private fun LoginUser() {
        val phone = InputPhoneNumber!!.text.toString()
        val password = InputPassword!!.text.toString()
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "Please enter phone number", Toast.LENGTH_SHORT).show()
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show()
        } else {
            loadingBar!!.setTitle("Login Account")
            loadingBar!!.setMessage("Please wait while we are checking credentials")
            loadingBar!!.setCanceledOnTouchOutside(false)
            loadingBar!!.show()
            AllowAccessToAccount(phone, password)
        }
    }

    private fun AllowAccessToAccount(phone: String, password: String) {
        if (chkBoxRememberMe!!.isChecked) {
            Paper.book().write(Prevalent.UserPhoneKey, phone)
            Paper.book().write(Prevalent.UserPasswordKey, password)
        }
        val RootRef: DatabaseReference
        RootRef = FirebaseDatabase.getInstance().reference
        RootRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.child(parentDbName).child(phone).exists()) {
                    val usersData = dataSnapshot.child(parentDbName).child(phone).getValue(Users::class.java)!!
                    if (usersData.phone == phone) {
                        if (usersData.password == password) {
                            if (parentDbName == "Admins") {
                                Toast.makeText(this@LoginActivity, "Logged in successfully", Toast.LENGTH_SHORT).show()
                                loadingBar!!.dismiss()
                                val intent = Intent(this@LoginActivity, AdminCategoryActivity::class.java)
                                startActivity(intent)
                            } else if (parentDbName == "Users") {
                                Toast.makeText(this@LoginActivity, "Logged in successfully", Toast.LENGTH_SHORT).show()
                                loadingBar!!.dismiss()
                                val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                                startActivity(intent)
                            }
                        } else {
                            loadingBar!!.dismiss()
                            Toast.makeText(this@LoginActivity, "Password is incorrect", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(this@LoginActivity, "This " + phone + "does not exist", Toast.LENGTH_SHORT).show()
                    loadingBar!!.dismiss()
                    Toast.makeText(this@LoginActivity, "Please try again or create new account", Toast.LENGTH_SHORT).show()
                    loadingBar!!.dismiss()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }
}