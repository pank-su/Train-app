package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.create

class MainActivity : AppCompatActivity() {
    lateinit var login_et: EditText
    lateinit var password_et: EditText
    val retrofit: Retrofit = Retrofit.Builder().baseUrl("http://fspobot.tw1.ru:8080/auth/").build()
    val service = retrofit.create(ApiService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        login_et = findViewById<EditText>(R.id.login_et)
        password_et = findViewById<EditText>(R.id.password_et)
    }

    fun reg(v: View){
        val intent = Intent(this, RegActivity::class.java).putExtra("login", login_et.text.toString())
        startActivity(intent)
    }

    fun login(v: View){
        var json_obj = JSONObject()
        json_obj.put("email", login_et.text.toString())
        json_obj.put("password", password_et.text.toString())

        CoroutineScope(Dispatchers.IO).launch {
            val requestBody = json_obj.toString().toRequestBody("application/json".toMediaTypeOrNull())
            val response = service.login(requestBody)
            println(response.code().toString())
            if (response.isSuccessful) {
                json_obj = JSONObject(response.body().toString())
                println(json_obj.get("state"))
            }
        }


    }
}