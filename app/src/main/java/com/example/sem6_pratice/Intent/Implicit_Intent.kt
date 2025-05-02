package com.example.sem6_pratice.Intent

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.sem6_pratice.R

class Implicit_Intent : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_implicit_intent)

        val btnintent=findViewById<Button>(R.id.btnclick1)
        val btnIntent1=findViewById<Button>(R.id.btnclick2)

        //for opening the web broweser
        btnintent.setOnClickListener {
            val intent=Intent(Intent.ACTION_VIEW)
            intent.data=Uri.parse("https://github.com/")
            startActivity(intent);
        }

        btnIntent1.setOnClickListener {
            val intent1=Intent(Intent.ACTION_DIAL)
            startActivity(intent1);
        }

    }
}