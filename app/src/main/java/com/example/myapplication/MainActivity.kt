package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    public fun reg(v: View){
        var login = findViewById<EditText>(R.id.login_et).text.toString()
        val intent = Intent(this, RegActivity::class.java).putExtra("login", login)
        startActivity(intent)
    }
}