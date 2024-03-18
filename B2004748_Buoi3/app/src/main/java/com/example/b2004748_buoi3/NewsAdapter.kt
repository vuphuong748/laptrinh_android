package com.example.b2004748_buoi3

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.b2004748_buoi3.data_class.NewsEntity
import com.example.b2004748_buoi3.databinding.ItemNewsBinding
import com.example.b2004748_buoi3.Constants.BUNDLE_NEWS_ID

class NewsAdapter: RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    private lateinit var binding: ItemNewsBinding
    private lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemNewsBinding.inflate(inflater, parent, false)
        context = parent.context
        return ViewHolder()
    }
    override fun onBindViewHolder(holder: NewsAdapter.ViewHolder, position: Int) {
        holder.bind(differ.currentList[position]) //cap nhat giao dien
        holder.setIsRecyclable(false)
    }
    //xd so luong muc du lieu can hien thi
    override fun getItemCount(): Int {
        return differ.currentList.size
    }
    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: NewsEntity) {
            //InitView
            binding.apply {
                //Set text
                tvTitle.text = item.newsTitle
                tvDesc.text= item.newsDesc

                root.setOnClickListener {
                    val intent= Intent(context, UpdateNewsActivity::class.java).setAction("your.custom.action")
                    intent.putExtra(BUNDLE_NEWS_ID, item.newsId)
                    context.startActivity(intent)
                }
            }
        }
    }
    private val differCallback = object : DiffUtil.ItemCallback<NewsEntity>() {
        override fun areItemsTheSame(oldItem: NewsEntity, newItem: NewsEntity): Boolean {
            return oldItem.newsId == newItem.newsId
        }
        override fun areContentsTheSame(oldItem: NewsEntity, newItem: NewsEntity): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallback)
}