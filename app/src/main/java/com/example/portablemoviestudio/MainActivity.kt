package com.example.portablemoviestudio

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.portablemoviestudio.navigation.MainScreenNavHost
import com.example.portablemoviestudio.repo.SharePrefFunction
import com.example.portablemoviestudio.viewModels.HomeScreenViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // Shared Preference Instanceを設定
            val sharedPreferences = getSharedPreferences(
                "com.pp.sharedpref.PREFERENCES_FILE_KEY",
                Context.MODE_PRIVATE
            )
            //　ローカルデータ（お気に入りのデータ）をShared Preferenceから取得
            SharePrefFunction.readFromSharedPref(sharedPreferences)
            //　メインナビゲーションのコントローラーを作成
            val mainNavController = rememberNavController()
            //　Bottomナビゲーションバーのコントローラーを作成
            val bottomNavController = rememberNavController()
            // メイン画面のViewModel Instanceを作成
            val mainViewModel = viewModel<HomeScreenViewModel>()
            // メイン画面のナビゲーションホストを呼ぶ
            MainScreenNavHost(
                mainNavController = mainNavController,
                bottomNavController = bottomNavController,
                resources = resources,
                sharedPreferences = sharedPreferences,
                mainViewModel = mainViewModel
            )
        }
    }
}
