package com.example.b2004748_buoi3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.b2004748_buoi3.data_class.NewsDatabase
import com.example.b2004748_buoi3.databinding.ActivityMainBinding
import com.example.b2004748_buoi3.Constants.NEWS_DATABASE

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnAddNews.setOnClickListener {
            startActivity(Intent(this,AddNewsActivity::class.java).setAction("your.custom.action"))
        }
    }
    private val newsDB : NewsDatabase by lazy {
        Room.databaseBuilder(this,NewsDatabase::class.java,NEWS_DATABASE)
            .allowMainThreadQueries() //truy van csdl
            .fallbackToDestructiveMigration() //xoa csdl hien tai va tao moi
            .build()
    }
    private val newsAdapter by lazy { NewsAdapter() }
    override fun onResume() {
        super.onResume()
        checkItem()
    }
    private fun checkItem(){
        binding.apply {
            if(newsDB.doa().getAllNews().isNotEmpty()){
                rvNewsList.visibility= View.VISIBLE
                tvEmptyText.visibility=View.GONE
                newsAdapter.differ.submitList(newsDB.doa().getAllNews())
                setupRecyclerView()
            }else{
                rvNewsList.visibility=View.GONE
                tvEmptyText.visibility=View.VISIBLE
            }
        }
    }
    //hiển thị danh sách dọc
    private fun setupRecyclerView(){
        binding.rvNewsList.apply {
            layoutManager= LinearLayoutManager(this@MainActivity)
            adapter=newsAdapter
        }
    }
}