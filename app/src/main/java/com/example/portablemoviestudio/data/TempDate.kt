package com.example.portablemoviestudio.data

import com.example.portablemoviestudio.entities.ViewMovie

// アプリの初期処理フラグ
var isFirst = true
// アプリのローカルから取得したデータを格納するリスト
var sharedPrefMovieList = mutableListOf<Int>()
// サーバから取得したデータを格納するリスト
var MovieDataList = mutableListOf<ViewMovie>()
