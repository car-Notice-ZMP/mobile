package com.example.noticemycar

import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toFile
import androidx.loader.content.CursorLoader
import kotlinx.android.synthetic.main.activity_notice_add.*
import java.io.ByteArrayOutputStream
import java.io.DataOutputStream
import java.io.File
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder


class NoticeAdd : AppCompatActivity() {
    private val pickImage = 100
    private var imageUri: Uri? = null
    private var selectedFile: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notice_add)

        addimg.setOnClickListener{
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }

        submitogl.setOnClickListener{
            val dodajogl = Thread(Runnable {
                try {
                    var reqParam = URLEncoder.encode("title", "UTF-8") + "=" + URLEncoder.encode(tytul.text.toString(), "UTF-8")
                    reqParam += "&" + URLEncoder.encode("message", "UTF-8") + "=" + URLEncoder.encode(opis.text.toString(), "UTF-8")
                    reqParam += "&" + URLEncoder.encode("mark", "UTF-8") + "=" + URLEncoder.encode(marka.text.toString(), "UTF-8")
                    reqParam += "&" + URLEncoder.encode("model", "UTF-8") + "=" + URLEncoder.encode(model.text.toString(), "UTF-8")
                    reqParam += "&" + URLEncoder.encode("price", "UTF-8") + "=" + URLEncoder.encode(cena.text.toString(), "UTF-8")
                    reqParam += "&" + URLEncoder.encode("color", "UTF-8") + "=" + URLEncoder.encode(kolor.text.toString(), "UTF-8")
                    reqParam += "&" + URLEncoder.encode("body", "UTF-8") + "=" + URLEncoder.encode(nadwozie.text.toString(), "UTF-8")
                    reqParam += "&" + URLEncoder.encode("mileage", "UTF-8") + "=" + URLEncoder.encode(przebieg.text.toString(), "UTF-8")
                    reqParam += "&" + URLEncoder.encode("year", "UTF-8") + "=" + URLEncoder.encode(rok.text.toString(), "UTF-8")

                    var buffer:ByteArray = reqParam.toByteArray()
                    val mURL = URL("https://citygame.ga/api/notices/store")

                    with(mURL.openConnection() as HttpURLConnection) {
                        setRequestProperty("Authorization", "Bearer $UserToken")
                        requestMethod = "POST"

                        val wr = DataOutputStream(getOutputStream());
                        wr.write(buffer);
                        wr.flush();

                        var registerresult: Intent = Intent(applicationContext, NoticeAddResult::class.java).apply{
                            putExtra("EXTRA_RESPONSE", responseCode)
                        }
                        startActivity(registerresult)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            })

            dodajogl.start()

        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            img.setImageURI(imageUri)
        }
    }
}