package com.example.myquizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val tvUserName: TextView = findViewById(R.id.tvUserName)
        val tvScore: TextView = findViewById(R.id.tvScore)
        val btnFinish: Button = findViewById(R.id.btnFinish)
        tvUserName.text = intent.getStringExtra(Constants.USERNAME)

        val total = intent.getIntExtra(Constants.TOTAL,3)
        val correct = intent.getIntExtra(Constants.CORRECT,0)
        tvScore.text = "My score is $correct/$total"

        btnFinish.setOnClickListener{
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

    }
}