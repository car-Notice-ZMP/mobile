package com.example.noticemycar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile_view.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.net.HttpURLConnection

class ProfileView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_view)
        var name: String = ""
        var email: String = ""
        var avatar: String = ""
        logout.setOnClickListener {
            var wylog: Intent = Intent(applicationContext, Logout::class.java)
            startActivity(wylog)
        }
        val getprofile = Thread(Runnable {
            try {
                val mURL = URL("https://citygame.ga/api/auth/profile")
                with(mURL.openConnection() as HttpURLConnection) {

                    setRequestProperty("Authorization", "Bearer $UserToken")
                    requestMethod = "GET"

                    println("URL : $url")
                    println("Response Code : $responseCode")

                    BufferedReader(InputStreamReader(inputStream)).use {
                        val response = StringBuffer()

                        var inputLine = it.readLine()
                        while (inputLine != null) {
                            response.append(inputLine)
                            inputLine = it.readLine()
                        }
                        it.close()
                        var mess: String = JSONObject(response.toString()).get("message").toString()
                        var json: JSONObject = JSONObject(mess)
                        name = json.get("name").toString()
                        email = json.get("email").toString()
                        avatar = json.get("avatar").toString()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        })
        getprofile.start()
        while(getprofile.isAlive)

        show_name.text = name
        show_email.text = email
        Picasso.get().load(avatar).into(imageView);
    }

}