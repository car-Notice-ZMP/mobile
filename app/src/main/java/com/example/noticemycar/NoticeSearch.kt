package com.example.noticemycar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_notice_search.*
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class NoticeSearch : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notice_search)

    button.setOnClickListener() {
        var search: Intent = Intent(applicationContext, NoticeSearchResult::class.java).apply {
            putExtra("min", editTextTextPersonName2.text.toString())
            putExtra("max", editTextNumber.text.toString())
            if (radioButton3.isChecked) {
                putExtra("field", "year")
            } else if (radioButton4.isChecked) {
                putExtra("field", "mileage")
            } else if (radioButton5.isChecked) {
                putExtra("field", "price")
            }
        }
        startActivity(search)
    }
    }
}