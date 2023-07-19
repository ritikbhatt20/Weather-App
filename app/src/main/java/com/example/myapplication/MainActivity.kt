package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton

class MainActivity : AppCompatActivity() {

    companion object{
        const val KEY = "com.example.myapplication.MainActivity.KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val startedBtn=findViewById<AppCompatButton>(R.id.compatButton)
        val cityText = findViewById<EditText>(R.id.et1)

        startedBtn.setOnClickListener {
            if(cityText.text.isNotEmpty()){
                val cityName = cityText.text.toString()
                val intent = Intent(this, WeatherActivity::class.java)
                intent.putExtra(KEY, cityName)
                startActivity(intent)
                finish()
            }
            else {
                Toast.makeText(this, "Please enter a City Name first", Toast.LENGTH_SHORT).show()
            }
        }
    }
}