package com.example.sem6_pratice.Intent

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.sem6_pratice.R

class Explicit_intent : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_explicit_intent2)

        val btnIntent=findViewById<Button>(R.id.btnclick)

        btnIntent.setOnClickListener {
            //open a new activity.........>>>>>
            val intent=Intent(this, explicit_page2::class.java)
            startActivity(intent)

        }
    }
}