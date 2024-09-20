package com.example.portablemoviestudio.viewModels

import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.portablemoviestudio.api.ApiInstances
import com.example.portablemoviestudio.data.MovieDataList
import com.example.portablemoviestudio.data.TMDBApiKey
import com.example.portablemoviestudio.data.apiCallErrMessage
import com.example.portablemoviestudio.data.sharedPrefMovieList
import com.example.portablemoviestudio.entities.ViewMovie
import com.example.portablemoviestudio.entities.Movies
import com.example.portablemoviestudio.entities.UiState
import com.example.portablemoviestudio.repo.SharePrefFunction.writeToSharePref
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeScreenViewModel(private val saveStateHandle: SavedStateHandle): ViewModel() {
    // ホーム画面のUI状態保持する値定義
    private val _uiState = MutableStateFlow<UiState<Movies>>(UiState.Success(Movies()))
    val uiState:StateFlow<UiState<Movies>> = _uiState
    // 映画リストを保持する値定義
    private val _movieLists = mutableStateListOf(ViewMovie())
    val movieLists: List<ViewMovie> = _movieLists
    //　お気に入りり数を保持する値定義
    private val _likedMovieCount = MutableStateFlow<Int>(0)
    val likedMovieCount:StateFlow<Int> = _likedMovieCount


    //private val testState = saveStateHandle.getStateFlow("testState", UiState.Success(Movies()))
    //var testState2 by mutableStateOf(UiState.Success(Movies()))
    //   private set


    fun initialize() {
        getMovieList()
    }

    private fun getMovieList() {
        //　サーバ通信の非同期処理を実行
        viewModelScope.launch {
            try {
                // UI状態更新
                _uiState.value = UiState.Loading
                //saveStateHandle["testState"] = UiState.Loading
                delay(2000)
                // 既存のデータクリヤー
                MovieDataList.clear()
                _movieLists.clear()
                //　サーバ通信
                val response = ApiInstances.movieApiInstance.getMovies(TMDBApiKey)

                if(response.isSuccessful){
                    //　通信成功の場合
                    val tempLikedData = mutableListOf<Int>()
                    response.body()?.results?.forEach { e ->
                        val isLiked = isLikedMovie(e.id)
                        MovieDataList.add(ViewMovie(isLiked, e))
                        if(isLiked){
                            tempLikedData.add(e.id)
                        }
                    }
                    sharedPrefMovieList.clear()
                    sharedPrefMovieList.addAll(tempLikedData)

                    _movieLists.addAll(MovieDataList)
                    _likedMovieCount.value = sharedPrefMovieList.size

                    response.body()?.let {
                            _uiState.value = UiState.Success(it)
                    }
                }else{
                    //　通信成功の場合
                    _uiState.value = UiState.Error(apiCallErrMessage)
                }

            }catch (e:Exception) {
                _uiState.value = UiState.Error(apiCallErrMessage)
            }
        }
    }
    //　お気に入り状態変換してローカルに保存
    fun updateFeb(
        sharedPreferences: SharedPreferences,
        item: ViewMovie
    ) {
        _movieLists[_movieLists.indexOf(item)] = ViewMovie(!item.isLiked, item.movies)
        if(!item.isLiked){
            sharedPrefMovieList.add(item.movies.id)
        }else{
            sharedPrefMovieList.remove(item.movies.id)
        }
        _likedMovieCount.value = sharedPrefMovieList.size
        writeToSharePref(sharedPreferences, sharedPrefMovieList.toString())
    }

    // お気に入りり映画の判断
    private fun isLikedMovie(movieId: Int): Boolean {
        return sharedPrefMovieList.contains(movieId)
    }
}