package com.example.portablemoviestudio.data

import androidx.navigation.compose.rememberNavController

// TMDB API 情報
val baseUrl = "https://api.themoviedb.org"
const val urlPath = "/3/discover/movie"
val TMDBApiKey = "9852ab8e097048d1025e97a6e93c67da"
val BaseImageUrl = "https://image.tmdb.org/t/p/w185/"

// エラー文言
val apiCallErrMessage = "Data Fetch Error"

// Shared Preference Key
val MovieListKey = "SharedPrefMovieList"

// Screen Name
val HomeScreen = "映画一覧"
val LikedMovieScreen = "お気に入り"
val MovieDatailScreen = "映画詳細"

