package com.example.noticemycar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_logout.*

class Logout : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logout)
        UserToken = ""
        mainmenu.setOnClickListener {
            var menugl: Intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(menugl)
        }
    }
}