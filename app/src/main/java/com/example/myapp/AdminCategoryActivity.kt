package com.example.myapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class AdminCategoryActivity : AppCompatActivity() {
    private var nike: ImageView? = null
    private var adidas: ImageView? = null
    private var puma: ImageView? = null
    private var vans: ImageView? = null
    private var gucci: ImageView? = null
    private var chanel: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_category)
        nike = findViewById<View>(R.id.nike) as ImageView
        adidas = findViewById<View>(R.id.adidas) as ImageView
        puma = findViewById<View>(R.id.puma) as ImageView
        vans = findViewById<View>(R.id.vans) as ImageView
        gucci = findViewById<View>(R.id.gucci) as ImageView
        chanel = findViewById<View>(R.id.chanel) as ImageView
        nike!!.setOnClickListener {
            val intent = Intent(this@AdminCategoryActivity, AdminAddNewProductActivity::class.java)
            intent.putExtra("category", "nike")
            startActivity(intent)
        }
        adidas!!.setOnClickListener {
            val intent = Intent(this@AdminCategoryActivity, AdminAddNewProductActivity::class.java)
            intent.putExtra("category", "adidas")
            startActivity(intent)
        }
        puma!!.setOnClickListener {
            val intent = Intent(this@AdminCategoryActivity, AdminAddNewProductActivity::class.java)
            intent.putExtra("category", "puma")
            startActivity(intent)
        }
        vans!!.setOnClickListener {
            val intent = Intent(this@AdminCategoryActivity, AdminAddNewProductActivity::class.java)
            intent.putExtra("category", "vans")
            startActivity(intent)
        }
        gucci!!.setOnClickListener {
            val intent = Intent(this@AdminCategoryActivity, AdminAddNewProductActivity::class.java)
            intent.putExtra("category", "gucci")
            startActivity(intent)
        }
        chanel!!.setOnClickListener {
            val intent = Intent(this@AdminCategoryActivity, AdminAddNewProductActivity::class.java)
            intent.putExtra("category", "chanel")
            startActivity(intent)
        }
    }
}