package com.example.noticemycar

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_notice_add_result.*

class NoticeAddResult : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notice_add_result)
        if(intent.getIntExtra("EXTRA_RESPONSE", 0)==200){
            textView5.setText("Ogłoszenie dodano prawidłowo")
            textView5.setTextColor(Color.GREEN)
        }
        else{
            textView5.setText("Dodanie ogłoszenia nie powiodło się. Spróbuj ponownie.")
            textView5.setTextColor(Color.RED)
        }
        mainmenu.setOnClickListener {
            var menugl: Intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(menugl)
        }
    }
}