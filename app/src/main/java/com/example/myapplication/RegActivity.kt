package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reg)
        val login = intent.getStringExtra("login")
        findViewById<EditText>(R.id.login_et).setText(login)
        this.email_et = findViewById<EditText>(R.id.email_et)
        email_et.setOnFocusChangeListener { view, v -> checkMail(v) }
        val sec_pass = findViewById<EditText>(R.id.password_et2)
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
}