package com.example.noticemycar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_notice_details.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class NoticeDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notice_details)

        var jsonStr: String = ""

        val getnotices = Thread(Runnable {
            try {
                val mURL = URL("https://citygame.ga/api/notices/show/"+intent.getIntExtra("id", 4))
                with(mURL.openConnection() as HttpURLConnection) {
                    requestMethod = "GET"

                    BufferedReader(InputStreamReader(inputStream)).use {
                        val response = StringBuffer()

                        var inputLine = it.readLine()
                        while (inputLine != null) {
                            response.append(inputLine)
                            inputLine = it.readLine()
                        }
                        it.close()
                        jsonStr = response.toString()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        })
        getnotices.start()
        while(getnotices.isAlive){}

        var jsonObject = JSONObject(jsonStr)
        val jsonArray = jsonObject.optJSONArray("notice_with_comment")
        jsonObject = jsonArray.getJSONObject(0)
        Picasso.get().load(jsonObject.optString("image_url")).into(zdjecie)
        tytul.text = jsonObject.optString("title")
        opis.text = jsonObject.optString("message")
        autor.text = jsonObject.optString("notice_author")
        emailautora.text = jsonObject.optString("notice_author_email")
        marka.text = jsonObject.optString("mark")
        model.text = jsonObject.optString("model")
        kolor.text = jsonObject.optString("color")
        rok.text = jsonObject.optString("year")
        przebieg.text = jsonObject.optString("mileage")
        cena.text = jsonObject.optString("price")
        nadwozie.text = jsonObject.optString("body")
    }
}