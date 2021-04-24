package com.example.noticemycar

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Patterns
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login_panel.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class LoginPanel : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_panel)
        loginpassword.setOnKeyListener { _, keyCode, event ->
            when {
                ((keyCode == KeyEvent.KEYCODE_ENTER) && (event.action == KeyEvent.ACTION_DOWN)) -> {
                    logindo.performClick()
                    return@setOnKeyListener true
                }
                else -> false
            }
        }

        loginmail.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(TextUtils.isEmpty(loginmail.text.toString()) || !Patterns.EMAIL_ADDRESS.matcher(loginmail.text.toString()).matches())
                    loginbladmail.setVisibility(View.VISIBLE)
                else
                    loginbladmail.setVisibility(View.INVISIBLE)
            }
        })

        loginpassword.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(loginpassword.text.toString().length<6)
                    loginbladhaslo.setVisibility(View.VISIBLE)
                else
                    loginbladhaslo.setVisibility(View.INVISIBLE)
            }
        })


        logindo.setOnClickListener{
            if(loginbladmail.visibility==View.VISIBLE || loginbladhaslo.visibility==View.VISIBLE) {
                loginblad.setText("Popraw powyższe błędy!")
                loginblad.setVisibility(View.VISIBLE)
                return@setOnClickListener
            }
            else
                loginblad.setVisibility(View.INVISIBLE)

            if(TextUtils.isEmpty(loginmail.text.toString()) || TextUtils.isEmpty(loginpassword.text.toString())) {
                loginblad.setText("Uzupełnij wszystkie pola w formularzu!")
                loginblad.setVisibility(View.VISIBLE)
                return@setOnClickListener
            }
            else
                loginblad.setVisibility(View.INVISIBLE)

            val login = Thread(Runnable {
                try {
                    var reqParam = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(loginmail.text.toString(), "UTF-8")
                    reqParam += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(loginpassword.text.toString(), "UTF-8")
                    val mURL = URL("https://citygame.ga/api/auth/login")

                    with(mURL.openConnection() as HttpURLConnection) {
                        requestMethod = "POST"

                        val wr = OutputStreamWriter(getOutputStream());
                        wr.write(reqParam);
                        wr.flush();

                        if(responseCode!=200){
                            UserToken = ""
                            var loginresult: Intent = Intent(applicationContext, LoginResult::class.java)
                            startActivity(loginresult)
                            return@with
                        }

                        BufferedReader(InputStreamReader(inputStream)).use {
                            val response = StringBuffer()

                            var inputLine = it.readLine()
                            while (inputLine != null) {
                                response.append(inputLine)
                                inputLine = it.readLine()
                            }
                            var token: String = JSONObject(response.toString()).get("access_token").toString()
                            UserToken = token
                            val sharedPreferences: SharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
                            val editor: SharedPreferences.Editor = sharedPreferences.edit()
                            editor.putString(SHAREDPREFSTOKEN, token)
                            editor.apply()
                            var loginresult: Intent = Intent(applicationContext, LoginResult::class.java)
                            startActivity(loginresult)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            })

            login.start()

        }
    }
}