package com.example.noticemycar

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.os.FileUtils
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_notice_add.*
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.net.URI


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
            /*
            if(regerrimie.visibility== View.VISIBLE || regerrmail.visibility== View.VISIBLE || regerrpass.visibility== View.VISIBLE || regerrpasscon.visibility== View.VISIBLE) {
                regerr.setText("Popraw powyższe błędy!")
                regerr.setVisibility(View.VISIBLE)
                return@setOnClickListener
            }
            else
                regerr.setVisibility(View.INVISIBLE)

            if(TextUtils.isEmpty(editTextTextPersonName.text.toString()) || TextUtils.isEmpty(editTextTextEmailAddress.text.toString()) || TextUtils.isEmpty(editTextTextPassword.text.toString()) || TextUtils.isEmpty(editTextTextPassword2.text.toString())) {
                regerr.setText("Uzupełnij wszystkie pola w formularzu!")
                regerr.setVisibility(View.VISIBLE)
                return@setOnClickListener
            }
            else
                regerr.setVisibility(View.INVISIBLE)
*/
            val dodajogl = Thread(Runnable {
                try {/*
                    var reqParam = URLEncoder.encode("title", "UTF-8") + "=" + URLEncoder.encode(tytul.text.toString(), "UTF-8")
                    reqParam += "&" + URLEncoder.encode("message", "UTF-8") + "=" + URLEncoder.encode(opis.text.toString(), "UTF-8")
                    reqParam += "&" + URLEncoder.encode("mark", "UTF-8") + "=" + URLEncoder.encode(marka.text.toString(), "UTF-8")
                    reqParam += "&" + URLEncoder.encode("model", "UTF-8") + "=" + URLEncoder.encode(model.text.toString(), "UTF-8")
                    reqParam += "&" + URLEncoder.encode("price", "UTF-8") + "=" + URLEncoder.encode(cena.text.toString(), "UTF-8")
                    reqParam += "&" + URLEncoder.encode("color", "UTF-8") + "=" + URLEncoder.encode(kolor.text.toString(), "UTF-8")
                    reqParam += "&" + URLEncoder.encode("body", "UTF-8") + "=" + URLEncoder.encode(nadwozie.text.toString(), "UTF-8")
                    reqParam += "&" + URLEncoder.encode("mileage", "UTF-8") + "=" + URLEncoder.encode(przebieg.text.toString(), "UTF-8")
                    reqParam += "&" + URLEncoder.encode("year", "UTF-8") + "=" + URLEncoder.encode(rok.text.toString(), "UTF-8")
                    reqParam += "&" + URLEncoder.encode("image", "UTF-8") + "="
                    var buffer:ByteArray = reqParam.toByteArray()
                    val bitmap = (img.getDrawable() as BitmapDrawable).getBitmap()
                    val stream = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)
                    val image = stream.toByteArray()
                    buffer += image
                    println(image.size)
                    println(image)

                    val mURL = URL("https://citygame.ga/api/notices/store")

                    with(mURL.openConnection() as HttpURLConnection) {
                        setRequestProperty("Authorization", "Bearer $UserToken")
                        requestMethod = "POST"

                        val wr = DataOutputStream(getOutputStream());
                        wr.write(buffer);
                        wr.flush();*/

                    val url = URL("https://citygame.ga/api/notices/store")
                    val connection = url.openConnection() as HttpURLConnection

                    connection.setRequestProperty("Authorization", "Bearer $UserToken")

                    val boundary: String = "X-INSOMNIA-BOUNDARY".toString()
                    connection.requestMethod = "POST"
                    connection.doOutput = true
                    connection.setRequestProperty("Content-Type","multipart/form-data;boundary=X-INSOMNIA-BOUNDARY"
                    )

                    val request =
                        DataOutputStream(connection.getOutputStream())

                    request.writeBytes("--X-INSOMNIA-BOUNDARY\r\n")
                    request.writeBytes("Content-Disposition: form-data; name=\"title\"\r\n")
                    request.writeBytes(tytul.text.toString() + "\r\n")

                    request.writeBytes("--X-INSOMNIA-BOUNDARY\r\n")
                    request.writeBytes("Content-Disposition: form-data; name=\"message\"\r\n")
                    request.writeBytes(opis.text.toString() + "\r\n")

                    request.writeBytes("--X-INSOMNIA-BOUNDARY\r\n")
                    request.writeBytes("Content-Disposition: form-data; name=\"mark\"\r\n")
                    request.writeBytes(marka.text.toString() + "\r\n")

                    request.writeBytes("--X-INSOMNIA-BOUNDARY\r\n")
                    request.writeBytes("Content-Disposition: form-data; name=\"model\"\r\n")
                    request.writeBytes(model.text.toString() + "\r\n")

                    request.writeBytes("--X-INSOMNIA-BOUNDARY\r\n")
                    request.writeBytes("Content-Disposition: form-data; name=\"price\"\r\n")
                    request.writeBytes(cena.text.toString() + "\r\n")

                    request.writeBytes("--X-INSOMNIA-BOUNDARY\r\n")
                    request.writeBytes("Content-Disposition: form-data; name=\"body\"\r\n")
                    request.writeBytes(nadwozie.text.toString() + "\r\n")

                    request.writeBytes("--X-INSOMNIA-BOUNDARY\r\n")
                    request.writeBytes("Content-Disposition: form-data; name=\"mileage\"\r\n")
                    request.writeBytes(przebieg.text.toString() + "\r\n")

                    request.writeBytes("--X-INSOMNIA-BOUNDARY\r\n")
                    request.writeBytes("Content-Disposition: form-data; name=\"year\"\r\n")
                    request.writeBytes(rok.text.toString() + "\r\n")

/*
                    request.writeBytes("--$boundary\r\n")
                    request.writeBytes("Content-Disposition: form-data; name=\"title\"\r\n\r\n")
                    request.writeBytes(tytul.text.toString() + "\r\n")

                    request.writeBytes("--$boundary\r\n")
                    request.writeBytes("Content-Disposition: form-data; name=\"message\"\r\n\r\n")
                    request.writeBytes(opis.text.toString() + "\r\n")

                    request.writeBytes("--$boundary\r\n")
                    request.writeBytes("Content-Disposition: form-data; name=\"mark\"\r\n\r\n")
                    request.writeBytes(marka.text.toString() + "\r\n")

                    request.writeBytes("--$boundary\r\n")
                    request.writeBytes("Content-Disposition: form-data; name=\"model\"\r\n\r\n")
                    request.writeBytes(model.text.toString() + "\r\n")

                    request.writeBytes("--$boundary\r\n")
                    request.writeBytes("Content-Disposition: form-data; name=\"price\"\r\n\r\n")
                    request.writeBytes(cena.text.toString() + "\r\n")

                    request.writeBytes("--$boundary\r\n")
                    request.writeBytes("Content-Disposition: form-data; name=\"color\"\r\n\r\n")
                    request.writeBytes(kolor.text.toString() + "\r\n")

                    request.writeBytes("--$boundary\r\n")
                    request.writeBytes("Content-Disposition: form-data; name=\"body\"\r\n\r\n")
                    request.writeBytes(nadwozie.text.toString() + "\r\n")

                    request.writeBytes("--$boundary\r\n")
                    request.writeBytes("Content-Disposition: form-data; name=\"mileage\"\r\n\r\n")
                    request.writeBytes(przebieg.text.toString() + "\r\n")

                    request.writeBytes("--$boundary\r\n")
                    request.writeBytes("Content-Disposition: form-data; name=\"year\"\r\n\r\n")
                    request.writeBytes(rok.text.toString() + "\r\n")


                    request.writeBytes("--$boundary\r\n")
                    */
                    /*request.writeBytes("""
                            Content-Disposition: form-data; name="file"; filename="${file.fileName.toString()}"
                            
                            
                            """.trimIndent()
                    )*/
                    val bitmap = (img.getDrawable() as BitmapDrawable).getBitmap()
                    val stream = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)
                    val image = stream.toByteArray()
                    request.writeBytes("--X-INSOMNIA-BOUNDARY\r\n")
                    request.writeBytes("Content-Disposition: form-data; name=\"image\"; filename=\"car.png\"\r\n")
                    request.writeBytes("Content-Type: image/png\r\n")
                    //request.write(FileUtils.readFileToByteArray(file))
                    request.write(image)

                    request.writeBytes("\r\n")

                    request.writeBytes("--X-INSOMNIA-BOUNDARY--\r\n")

                    request.flush()




                        println("URL : $url")
                        println("Response Code : ${connection.responseCode}")
/*
                        var registerresult: Intent = Intent(applicationContext, RegisterResult::class.java).apply{
                            putExtra("EXTRA_RESPONSE", responseCode)
                        }
                        startActivity(registerresult)
                        */
                    /*
                        BufferedReader(InputStreamReader(inputStream)).use {
                            val response = StringBuffer()

                            var inputLine = it.readLine()
                            while (inputLine != null) {
                                response.append(inputLine)
                                inputLine = it.readLine()
                            }
                            println("Response : $response")
                        //}
                    }*/
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