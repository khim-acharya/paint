package com.example.portablemoviestudio.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.portablemoviestudio.data.BackIcon
import com.example.portablemoviestudio.data.BackIconSize
import com.example.portablemoviestudio.data.BaseImageUrl
import com.example.portablemoviestudio.data.Black
import com.example.portablemoviestudio.data.DarkGray
import com.example.portablemoviestudio.data.DetailScreenDataPadding
import com.example.portablemoviestudio.data.DetailScreenPadding
import com.example.portablemoviestudio.data.LightGray
import com.example.portablemoviestudio.data.ListItemBottomMargin
import com.example.portablemoviestudio.data.ListSubTitleFontSize
import com.example.portablemoviestudio.data.MovieDatailScreen
import com.example.portablemoviestudio.data.MovieTitleFontSize
import com.example.portablemoviestudio.data.Transparent
import com.example.portablemoviestudio.data.White
import com.example.portablemoviestudio.entities.Result
import com.example.portablemoviestudio.viewModels.HomeScreenViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreenUI(
                        navController: NavHostController,
                        movieId: String,
                        mainViewModel: HomeScreenViewModel
){
    val listData = mainViewModel.movieLists
    var selectedData = Result()
    // 映画のIdから映画情報取得
    run breaking@{
        listData.forEach{e ->
            if(e.movies.id == movieId.toInt()){
                selectedData = e.movies
                return@breaking
            }
        }
    }

    Surface(
        color = LightGray, modifier = Modifier
            .fillMaxSize()
    ) {
        Column( Modifier.fillMaxWidth()) {
            TopAppBar(
                title = { Text(text = MovieDatailScreen, color = White) },
                colors = TopAppBarColors(
                    DarkGray,
                    Transparent,
                    Transparent,
                    Transparent,
                    Transparent
                ),
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = BackIcon,
                            contentDescription = "",
                            tint = White,
                            modifier = Modifier.size(BackIconSize)
                        )

                    }
                }
            )
            Column {
                // 映画の画面表示
                Box(
                    Modifier
                        .fillMaxWidth()
                        .weight(1f).background(color = Black)) {
                    AsyncImage(
                        model = "$BaseImageUrl${selectedData.poster_path}",
                        contentDescription = null,
                        Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                    )
                }
                // 映画の詳細情報表示
                Box(Modifier
                        .padding(horizontal = ListItemBottomMargin, vertical = DetailScreenPadding)
                        .fillMaxWidth()
                        .weight(2f))
                {
                    Column(verticalArrangement = Arrangement.spacedBy(ListItemBottomMargin)) {
                        //　詳細情報画面呼び出し
                        MovieDetailRow(
                            titleText = "Movie Title",
                            value = selectedData.original_title
                        )
                        MovieDetailRow(titleText = "Release Date", value = selectedData.release_date)
                        MovieDetailRow(titleText = "IMDB Ratting", value = selectedData.vote_average.toString())
                        MovieDetailRow(
                            titleText = "Movie Overview",
                            value = selectedData.overview
                        )
                    }
                }
            }
        }

    }
}

// 詳細情報画面
@Composable
fun MovieDetailRow(titleText: String,value: String) {
    Row(
        Modifier
            .fillMaxWidth()) {
        Box(
            Modifier
                .weight(1.3f)) {
            Text(text = titleText,
                style = TextStyle(
                    fontSize = MovieTitleFontSize,
                    fontWeight = FontWeight.Bold
                )
            )
        }
        Box(
            Modifier
                .weight(0.1f)) {
            Text(":",
                style = TextStyle(
                    fontSize = MovieTitleFontSize,
                    fontWeight = FontWeight.Bold
                )
            )
        }
        Box(
            Modifier
                .weight(2.5f)
                .padding(start = DetailScreenDataPadding)) {
            Text(text = value,
                style = TextStyle(
                    fontSize = ListSubTitleFontSize,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}