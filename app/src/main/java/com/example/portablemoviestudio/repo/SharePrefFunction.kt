package com.example.portablemoviestudio.repo

import android.content.SharedPreferences
import android.util.Log
import com.example.portablemoviestudio.data.MovieListKey
import com.example.portablemoviestudio.data.sharedPrefMovieList

object SharePrefFunction{
     fun readFromSharedPref(sharedPreferences: SharedPreferences) {
        val dataString: String = sharedPreferences.getString(MovieListKey, "") ?: ""
        if (dataString.length > 2) {
            val tempList = dataString.removeSurrounding("[", "]").split(", ").map { it.toInt() }
            sharedPrefMovieList.addAll(tempList)
                Log.d("###DataString", sharedPrefMovieList.toString())
        }
    }

    fun writeToSharePref(sharedPreferences: SharedPreferences, likedMovieList: String) {
        val editor = sharedPreferences.edit()
        editor.putString(MovieListKey, likedMovieList)
        editor.apply()
    }
}
