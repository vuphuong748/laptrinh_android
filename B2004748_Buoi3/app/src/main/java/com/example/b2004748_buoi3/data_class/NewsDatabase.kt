package com.example.b2004748_buoi3.data_class

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [NewsEntity::class], version = 1)
abstract class NewsDatabase : RoomDatabase(){
    abstract fun doa():NewsDao
}