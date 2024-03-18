package com.example.b2004748_buoi4.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.b2004748_buoi4.R
import com.example.b2004748_buoi4.databinding.ItemWishBinding
import com.example.b2004748_buoi4.models.Wish
import com.example.b2004748_buoi4.sharedpreferences.AppSharedPreferences

class WishAdapter (
    private val context: Context,
    private val wishList: List<Wish>,
    private val appSharedPreferences: AppSharedPreferences,
    //truyen interface tu ngoai vao
    private val iClickItemWish: IClickItemWish,
): RecyclerView.Adapter<WishAdapter.WishViewHolder>() {
    class WishViewHolder(val binding: ItemWishBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishViewHolder {
        return WishViewHolder(ItemWishBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return wishList.size
    }

    override fun onBindViewHolder(holder: WishViewHolder, position: Int) {
        //lay doi tuong wish o vi tri position va thiet lap len giao dien
        val wish: Wish = wishList[position]
        holder.binding.tvName.text = wish.name
        holder.binding.tvContent.text = wish.content
        Glide.with(context).load(wish.owner.avatar)
            .error(R.drawable.img_blue)
            .into(holder.binding.imgAvatar)

        //neu nguoi dung dang nhap la chu nhan cua dieu uoc o vi tri position thi hien thi icon update va delete
        if(appSharedPreferences.getIdUser("idUser").toString() == wish.owner._id) {
            holder.binding.imgUpdate.visibility = ViewGroup.VISIBLE
            holder.binding.imgDelete.visibility = ViewGroup.VISIBLE
        }

        //bat su kien click vao bieu tuong xoa va sua -> thuc hien ham tuong ung da khai bao trong giao dien la file ApiService.kt
        holder.binding.imgUpdate.setOnClickListener {
            iClickItemWish.onCLickUpdate(wish._id, wish.name, wish.content)
        }

        holder.binding.imgDelete.setOnClickListener {
            iClickItemWish.onCLickRemove(wish._id)
        }
    }
    //dinh nghia 1 interface chua cac ham khi thuc hien click tung item
    interface IClickItemWish {
        fun onCLickUpdate(idWish: String, fullName: String, content: String)
        fun onCLickRemove(idWish: String)
    }
}