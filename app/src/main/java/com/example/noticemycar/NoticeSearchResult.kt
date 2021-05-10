package com.example.noticemycar

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class NoticeSearchResult : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notices_all)

        var jsonStr: String = ""

        val getnotices = Thread(Runnable {
            try {
                var reqParam = URLEncoder.encode("min", "UTF-8") + "=" + URLEncoder.encode(intent.getStringExtra("min"), "UTF-8")
                reqParam += "&" + URLEncoder.encode("max", "UTF-8") + "=" + URLEncoder.encode(intent.getStringExtra("max"), "UTF-8")
                reqParam += "&" + URLEncoder.encode("field", "UTF-8") + "=" + URLEncoder.encode(intent.getStringExtra("field"), "UTF-8")
                val mURL = URL("https://citygame.ga/api/notices/search/between")
                with(mURL.openConnection() as HttpURLConnection) {
                    requestMethod = "POST"

                    val wr = DataOutputStream(getOutputStream());
                    var buffer:ByteArray = reqParam.toByteArray()
                    wr.write(buffer);
                    wr.flush();

                    BufferedReader(InputStreamReader(inputStream)).use {
                        val response = StringBuffer()

                        var inputLine = it.readLine()
                        while (inputLine != null) {
                            response.append(inputLine)
                            inputLine = it.readLine()
                        }
                        it.close()
                        jsonStr = response.toString()
                        println(jsonStr)
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
        val jsonArray = jsonObject.optJSONArray("Result")

        for(i in 0 until jsonArray.length()){
            val jsonObject = jsonArray.getJSONObject(i)
            list.add(NoticeListModel(jsonObject.optString("mark")+" "+jsonObject.optString("model"), "Rok: "+jsonObject.optString("year"), jsonObject.optString("price")+" zł", jsonObject.optString("image_url"), jsonObject.optString("id").toInt()))

        }

        listview.adapter = NoticeListAdapter(this, R.layout.row, list)

        listview.setOnItemClickListener{ parent: AdapterView<*>, view: View, position:Int, id:Long ->
            var details: Intent = Intent(applicationContext, NoticeDetails::class.java)
            details.putExtra("id", list.get(position).id)
            startActivity(details)
        }
    }
}