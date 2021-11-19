package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Retrofit

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

    fun reg(v: View) {
        val intent =
            Intent(this, RegActivity::class.java).putExtra("login", login_et.text.toString())
        startActivity(intent)
    }

    fun login(v: View) {
        if (login_et.text.toString() == "" || password_et.text.toString() == "") {
            return
        }
        var json_obj = JSONObject()
        json_obj.put("email", login_et.text.toString())
        json_obj.put("password", password_et.text.toString())
        CoroutineScope(Dispatchers.Main).launch {
            val requestBody =
                json_obj.toString().toRequestBody("application/json".toMediaTypeOrNull())
            val response = service.login(requestBody)
            if (response.isSuccessful) {
                json_obj = JSONObject(response.body()?.string())
                if (json_obj.get("state") == "ok") {
                    var token = json_obj.get("token")
                    println(token)
                    startActivity(Intent(this@MainActivity, CountriesActivity::class.java))
                    this@MainActivity.finish()
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "Неверный логин или пароль",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(this@MainActivity, "Неверный логин или пароль", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        // startActivity(Intent(this@MainActivity, CountriesActivity::class.java))
    }
}