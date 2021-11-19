package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Retrofit
import java.util.regex.Pattern

class RegActivity : AppCompatActivity() {
    var EMAIL_ADDRESS_PATTERN: Pattern = Pattern.compile(
        "[a-z0-9]{1,256}" +
                "\\@" +
                "[a-z0-9]{1,64}" +
                "\\." +
                "[a-z]{1,3}"
    )
    lateinit var email_et: TextView
    lateinit var login_et: TextView
    lateinit var sec_pass: TextView
    val retrofit: Retrofit = Retrofit.Builder().baseUrl("http://fspobot.tw1.ru:8080/auth/").build()
    val service = retrofit.create(ApiService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reg)
        val login = intent.getStringExtra("login")
        login_et = findViewById<EditText>(R.id.login_et)
        login_et.text = login
        this.email_et = findViewById<EditText>(R.id.email_et)
        email_et.setOnFocusChangeListener { view, v -> checkMail(v) }
        sec_pass = findViewById<EditText>(R.id.password_et2)
        val first_pass = findViewById<EditText>(R.id.password_et)
        val textWatcher = object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {
                if (sec_pass.text.toString() != first_pass.text.toString() && first_pass.text.toString() != "" && sec_pass.text.toString() != "") {
                    sec_pass.error = "Password mismatch"
                    first_pass.error = "Password mismatch"
                } else{
                    sec_pass.error = null
                    first_pass.error = null
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        }
        sec_pass.addTextChangedListener(textWatcher)
        first_pass.addTextChangedListener(textWatcher)
    }

    private fun checkMail(b: Boolean) {
        if (!b && email_et.text != "" && !EMAIL_ADDRESS_PATTERN.matcher(email_et.text.toString())
                .matches()
        ) {
            email_et.error = "Please write normal email"
        } else {
            email_et.error = null
        }
    }

    fun register(v: View){
        if (login_et.text == "" || email_et.error != null || sec_pass.error != null){
            Toast.makeText(this@RegActivity, "У вас что-то заплонено не так", Toast.LENGTH_SHORT).show()
        }
        var jsonObj = JSONObject()
        jsonObj.put("firstName", login_et.text)
        jsonObj.put("lastName", "not_needed")
        jsonObj.put("email", email_et.text)
        jsonObj.put("password", sec_pass.text)
        CoroutineScope(Dispatchers.Main).launch {
            val requestBody = jsonObj.toString().toRequestBody("application/json".toMediaTypeOrNull())
            val response = service.register(requestBody)
            if (response.isSuccessful) {
                jsonObj = JSONObject(response.body()?.string())
                if (jsonObj.get("state") == "ok"){
                    this@RegActivity.finish()
                }
            } else{
                Toast.makeText(this@RegActivity, "Что-то пошло не так", Toast.LENGTH_SHORT).show()
            }
        }

    }

}