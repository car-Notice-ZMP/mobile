package com.example.noticemycar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import com.squareup.picasso.Picasso
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class NoticesAll : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notices_all)

        var jsonStr: String = ""

        val getnotices = Thread(Runnable {
            try {
                val mURL = URL("https://citygame.ga/api/notices/all")
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

        var listview = findViewById<ListView>(R.id.listView)
        var list = mutableListOf<NoticeListModel>()

        val jsonObject = JSONObject(jsonStr)
        val jsonArray = jsonObject.optJSONArray("All notices")
        for(i in 0 until jsonArray.length()){
            val jsonObject = jsonArray.getJSONObject(i)
            list.add(NoticeListModel(jsonObject.optString("mark")+" "+jsonObject.optString("model"), "Rok: "+jsonObject.optString("year"), jsonObject.optString("price")+" zł", jsonObject.optString("image_url"), jsonObject.optString("id").toInt()))

        }

        listview.adapter = NoticeListAdapter(this, R.layout.row, list)

        listview.setOnItemClickListener{ parent: AdapterView<*>, view: View, position:Int, id:Long ->
            println(list.get(position).id)
        }
    }
}