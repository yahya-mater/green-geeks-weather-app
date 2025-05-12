package com.example.greengeeksweatherapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.greengeeksweatherapp.activites.MainActivity
import com.google.android.material.textfield.TextInputEditText

class search_page : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_search_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets


        }
        val city_name=findViewById<TextInputEditText>(R.id.city)
        val back_btn = findViewById<ConstraintLayout>(R.id.back_btn)
        back_btn.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        city_name.setOnClickListener{
            val check = city_name.text.toString()
            Toast.makeText(this, check, Toast.LENGTH_SHORT).show()
        }
    }
}