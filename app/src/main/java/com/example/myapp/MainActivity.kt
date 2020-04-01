package com.example.myapp

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapp.Model.Users
import com.example.myapp.Prevalent.Prevalent
import com.google.firebase.database.*
import io.paperdb.Paper

class MainActivity : AppCompatActivity() {
    private var joinNowButton: Button? = null
    private var loginButton: Button? = null
    private var loadingBar: ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        joinNowButton = findViewById<View>(R.id.main_join_now_btn) as Button
        loginButton = findViewById<View>(R.id.main_login_btn) as Button
        loadingBar = ProgressDialog(this)
        Paper.init(this)
        loginButton!!.setOnClickListener {
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
        }
        joinNowButton!!.setOnClickListener {
            val intent = Intent(this@MainActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
        val UserPhoneKey = Paper.book().read<String>(Prevalent.UserPhoneKey)
        val UserPasswordKey = Paper.book().read<String>(Prevalent.UserPasswordKey)
        if (UserPhoneKey !== "" && UserPasswordKey !== "") {
            if (!TextUtils.isEmpty(UserPhoneKey) && !TextUtils.isEmpty(UserPasswordKey)) {
                AllowAccess(UserPhoneKey, UserPasswordKey)
                loadingBar!!.setTitle("Logged in")
                loadingBar!!.setMessage("Please wait while we are checking credentials")
                loadingBar!!.setCanceledOnTouchOutside(false)
                loadingBar!!.show()
            }
        }
    }

    private fun AllowAccess(phone: String, password: String) {
        val RootRef: DatabaseReference
        RootRef = FirebaseDatabase.getInstance().reference
        RootRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.child("Users").child(phone).exists()) {
                    val usersData = dataSnapshot.child("Users").child(phone).getValue(Users::class.java)!!
                    if (usersData.phone == phone) {
                        if (usersData.password == password) {
                            Toast.makeText(this@MainActivity, "Logged in successfully", Toast.LENGTH_SHORT).show()
                            loadingBar!!.dismiss()
                            val intent = Intent(this@MainActivity, HomeActivity::class.java)
                            startActivity(intent)
                        } else {
                            loadingBar!!.dismiss()
                            Toast.makeText(this@MainActivity, "Password is incorrect", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(this@MainActivity, "This " + phone + "does not exist", Toast.LENGTH_SHORT).show()
                    loadingBar!!.dismiss()
                    Toast.makeText(this@MainActivity, "Please try again or create new account", Toast.LENGTH_SHORT).show()
                    loadingBar!!.dismiss()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }
}