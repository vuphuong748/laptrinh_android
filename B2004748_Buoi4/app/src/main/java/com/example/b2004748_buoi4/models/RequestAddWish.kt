package com.example.b2004748_buoi4.models

import com.google.gson.annotations.SerializedName

data class RequestAddWish(
    val idUser: String,
    @SerializedName("name")//dung de doi dung ten tham so truyen len server
    val fullName: String,
    val content: String
)
