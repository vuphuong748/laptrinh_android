package com.example.b2004748_buoi4.sharedpreferences

import android.content.Context
import android.content.SharedPreferences

class AppSharedPreferences(val context: Context) {
    fun putIdUser(key: String, value: String) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("BEST_WISHES", 0)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getIdUser(key: String): String? {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("BEST_WISHES", 0)
        return sharedPreferences.getString(key, "")
    }

    fun putWish(key: String, value: String) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("BEST_WISHES", 0)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getWish(key: String): String? {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("BEST_WISHES", 0)
        return sharedPreferences.getString(key, "")
    }
}