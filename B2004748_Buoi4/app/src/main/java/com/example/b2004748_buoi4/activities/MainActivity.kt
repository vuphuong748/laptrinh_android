package com.example.b2004748_buoi4.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.b2004748_buoi4.R
import com.example.b2004748_buoi4.databinding.ActivityMainBinding
import com.example.b2004748_buoi4.fragments.LoginFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, LoginFragment())
            .commit()
    }
}