package com.example.b2004748_buoi3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import com.example.b2004748_buoi3.data_class.NewsDatabase
import com.example.b2004748_buoi3.data_class.NewsEntity
import com.example.b2004748_buoi3.databinding.ActivityUpdateNewsBinding
import com.example.b2004748_buoi3.Constants
import com.example.b2004748_buoi3.Constants.BUNDLE_NEWS_ID
import com.google.android.material.snackbar.Snackbar
import android.content.Intent

class UpdateNewsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateNewsBinding
    private lateinit var newsEntity: NewsEntity
    private var newsId = 0
    private var defaultTitle = ""
    private var defaultDesc = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        intent.extras?.let {
            newsId = it.getInt(BUNDLE_NEWS_ID)
        }
        binding.apply {
            defaultTitle=newsDB.doa().getNews(newsId).newsTitle
            defaultDesc=newsDB.doa().getNews(newsId).newsDesc
            edtTitle.setText(defaultTitle)
            edtDesc.setText(defaultDesc)
            btnDelete.setOnClickListener {
                newsEntity= NewsEntity(newsId,defaultTitle,defaultDesc)
                newsDB.doa().deleteNews(newsEntity)
                startActivity(Intent(this@UpdateNewsActivity,MainActivity::class.java))
                finish()
            }
            btnSave.setOnClickListener {
                val title = edtTitle.text.toString()
                val desc=edtDesc.text.toString()
                if (title.isNotEmpty() || desc.isNotEmpty()){
                    newsEntity= NewsEntity(newsId,title,desc)
                    newsDB.doa().updateNews(newsEntity)
                    startActivity(Intent(this@UpdateNewsActivity,MainActivity::class.java))
                    finish()
                }
                else{
                    Snackbar.make(it,"Title and Description cannot be Empty", Snackbar.LENGTH_LONG).show()
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