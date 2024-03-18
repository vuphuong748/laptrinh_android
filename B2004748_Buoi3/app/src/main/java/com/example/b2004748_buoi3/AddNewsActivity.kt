package com.example.b2004748_buoi3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import com.example.b2004748_buoi3.data_class.NewsDatabase
import com.example.b2004748_buoi3.data_class.NewsEntity
import com.example.b2004748_buoi3.databinding.ActivityAddNewsBinding
import com.example.b2004748_buoi3.Constants
import com.google.android.material.snackbar.Snackbar

class AddNewsActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddNewsBinding
    private lateinit var newsEntity: NewsEntity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAddNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            btnSave.setOnClickListener {
                val title = edtTitle.text.toString()
                val desc = edtDesc.text.toString()
                if (title.isNotEmpty() || desc.isNotEmpty()){
                    newsEntity= NewsEntity(0,title,desc)
                    newsDB.doa().insertNews(newsEntity)
                    finish()
                }
                else{
                    Snackbar.make(it,"Title and Description cannot be Empty",Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }
    private val newsDB : NewsDatabase by lazy {
        Room.databaseBuilder(this, NewsDatabase::class.java, Constants.NEWS_DATABASE)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
}