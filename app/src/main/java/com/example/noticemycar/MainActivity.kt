package com.example.noticemycar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main_logged.*
import android.content.Intent


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(UserToken==""){
            setContentView(R.layout.activity_main)

            login.setOnClickListener {
                var logowanie: Intent = Intent(applicationContext, LoginPanel::class.java)
                startActivity(logowanie)
            }

            register.setOnClickListener {
                var rejestracja: Intent = Intent(applicationContext, RegisterPanel::class.java)
                startActivity(rejestracja)
            }
        }
        else{
            setContentView(R.layout.activity_main_logged)
            myprofile.setOnClickListener {
                var prof: Intent = Intent(applicationContext, ProfileView::class.java)
                startActivity(prof)
            }
        }
    }
}