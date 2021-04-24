package com.example.noticemycar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Patterns
import android.view.KeyEvent
import android.view.View
import kotlinx.android.synthetic.main.activity_register_panel.*
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class RegisterPanel : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_panel)
        editTextTextPassword2.setOnKeyListener { _, keyCode, event ->
            when {
                ((keyCode == KeyEvent.KEYCODE_ENTER) && (event.action == KeyEvent.ACTION_DOWN)) -> {
                    registerdo.performClick()
                    return@setOnKeyListener true
                }
                else -> false
            }
        }

        editTextTextPersonName.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(editTextTextPersonName.text.toString().length<1)
                    regerrimie.setVisibility(View.VISIBLE)
                else
                    regerrimie.setVisibility(View.INVISIBLE)
            }
        })

        editTextTextEmailAddress.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(TextUtils.isEmpty(editTextTextEmailAddress.text.toString()) || !Patterns.EMAIL_ADDRESS.matcher(editTextTextEmailAddress.text.toString()).matches())
                    regerrmail.setVisibility(View.VISIBLE)
                else
                    regerrmail.setVisibility(View.INVISIBLE)
            }
        })

        editTextTextPassword.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(editTextTextPassword.text.toString().length<6)
                    regerrpass.setVisibility(View.VISIBLE)
                else
                    regerrpass.setVisibility(View.INVISIBLE)
            }
        })

        editTextTextPassword2.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(editTextTextPassword2.text.toString()!=editTextTextPassword.text.toString())
                    regerrpasscon.setVisibility(View.VISIBLE)
                else
                    regerrpasscon.setVisibility(View.INVISIBLE)
            }
        })


        registerdo.setOnClickListener{
            if(regerrimie.visibility==View.VISIBLE || regerrmail.visibility==View.VISIBLE || regerrpass.visibility==View.VISIBLE || regerrpasscon.visibility==View.VISIBLE) {
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

            val register = Thread(Runnable {
                try {
                    var reqParam = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(editTextTextEmailAddress.text.toString(), "UTF-8")
                    reqParam += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(editTextTextPassword.text.toString(), "UTF-8")
                    reqParam += "&" + URLEncoder.encode("password_confirmation", "UTF-8") + "=" + URLEncoder.encode(editTextTextPassword2.text.toString(), "UTF-8")
                    reqParam += "&" + URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(editTextTextPersonName.text.toString(), "UTF-8")
                    val mURL = URL("https://citygame.ga/api/auth/register")

                    with(mURL.openConnection() as HttpURLConnection) {
                        requestMethod = "POST"

                        val wr = OutputStreamWriter(getOutputStream());
                        wr.write(reqParam);
                        wr.flush();

                        println("URL : $url")
                        println("Response Code : $responseCode")

                        var registerresult: Intent = Intent(applicationContext, RegisterResult::class.java).apply{
                            putExtra("EXTRA_RESPONSE", responseCode)
                        }
                        startActivity(registerresult)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            })

            register.start()

        }
    }
}