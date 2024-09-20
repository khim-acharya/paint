package com.example.portablemoviestudio.screens

import android.content.SharedPreferences
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.portablemoviestudio.data.BaseImageUrl
import com.example.portablemoviestudio.data.FavoriteIcon
import com.example.portablemoviestudio.data.Gray
import com.example.portablemoviestudio.data.ListDetailFontSize
import com.example.portablemoviestudio.data.ListIemInnerPadding
import com.example.portablemoviestudio.data.ListItemBottomMargin
import com.example.portablemoviestudio.data.ListItemImagePadding
import com.example.portablemoviestudio.data.ListItemLoveIconSize
import com.example.portablemoviestudio.data.ListScreenHorizontalPadding
import com.example.portablemoviestudio.data.ListSubTitleFontSize
import com.example.portablemoviestudio.data.ListTitleFontSize
import com.example.portablemoviestudio.data.MovieListHeight
import com.example.portablemoviestudio.data.Red
import com.example.portablemoviestudio.data.White
import com.example.portablemoviestudio.data.sharedPrefMovieList
import com.example.portablemoviestudio.navigation.MainScreenNavigationItems
import com.example.portablemoviestudio.viewModels.HomeScreenViewModel

// 映画一覧リスト画面作成
@Composable
fun LiveDataList(viewModel: HomeScreenViewModel,
                 mainNavController: NavHostController,
                 sharedPreferences: SharedPreferences
)
{
    val listData = viewModel.movieLists
    val textState = remember { mutableStateOf(TextFieldValue("")) }
    val searchedText = textState.value.text
    Column(Modifier.padding(horizontal = ListScreenHorizontalPadding)) {
        SearchView(state = textState)
        //映画リスト作成
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(ListItemBottomMargin)
        ) {
            items(items = listData.filter {
                it.movies.original_title.contains(searchedText, ignoreCase = true)
            }) { item ->
                //　お気に入りアイコンの色設定
                var fevIconColor = Gray
                if (item.isLiked) {
                    fevIconColor = Red
                }
                val imagePath = item.movies.poster_path
                Box(
                    Modifier
                        .height(MovieListHeight)
                        .background(color = White)
                        .clickable {
                            mainNavController.navigate("${MainScreenNavigationItems.Detail.route}/${item.movies.id}")
                        },
                ) {
                    Row {
                        Box(
                            Modifier
                                .weight(1f)
                                .padding(ListItemImagePadding)
                        ) {
                             AsyncImage(
                                model = "$BaseImageUrl$imagePath",
                                contentDescription = null,
                                contentScale = ContentScale.Fit
                            )

                        }
                        Column(
                            Modifier
                                .weight(3.5f)
                                .padding(
                                    start = ListIemInnerPadding,
                                    top = ListIemInnerPadding,
                                    bottom = ListIemInnerPadding
                                )
                        ) {
                            Text(
                                text = item.movies.original_title,
                                style = TextStyle(
                                    fontSize = ListTitleFontSize,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Text(
                                text = " Release: ${item.movies.release_date}",
                                style = TextStyle(
                                    fontSize = ListSubTitleFontSize,
                                    fontWeight = FontWeight.Bold
                                ),
                            )
                            Text(
                                text = item.movies.overview,
                                style = TextStyle(
                                    fontSize = ListDetailFontSize,
                                ),
                            )
                        }
                        Box(
                            Modifier
                                .weight(0.2f)
                                .padding(end = 2.dp))
                        {
                            IconButton(onClick = {
                                viewModel.updateFeb(
                                    sharedPreferences,
                                    item
                                )
                            }) {
                                Icon(
                                    imageVector = FavoriteIcon,
                                    contentDescription = "",
                                    tint = fevIconColor,
                                    modifier = Modifier.size(ListItemLoveIconSize)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// お気に入り映画リスト画面作成
@Composable
fun LikedDataList(
    viewModel: HomeScreenViewModel,
    sharedPreferences: SharedPreferences,
    mainNavController: NavHostController
)
{
    val listData = viewModel.movieLists
    val textState = remember { mutableStateOf(TextFieldValue("")) }
    val searchedText = textState.value.text
    Column(Modifier.padding(horizontal = ListScreenHorizontalPadding)) {
        if(sharedPrefMovieList.size>0)SearchView(state = textState)
        //映画リスト作成
        LazyColumn(verticalArrangement = Arrangement.spacedBy(ListItemBottomMargin)) {
            items(items = listData.filter {
                it.movies.original_title.contains(searchedText, ignoreCase = true) && it.isLiked
            }) { item ->
                val imagePath = item.movies.poster_path
                    Box(
                        Modifier
                            .height(MovieListHeight)
                            .background(color = Color.White)
                            .clickable() {
                                mainNavController.navigate("${MainScreenNavigationItems.Detail.route}/${item.movies.id}")
                            }
                ) {
                    Row {
                        Box(
                            Modifier
                                .weight(1f)
                                .padding(ListItemImagePadding)
                        ) {
                            //Image(image, contentDescription = null, modifier.padding(vertical = 2.dp), contentScale = ContentScale.FillBounds)
                            AsyncImage(
                                model = "$BaseImageUrl$imagePath",
                                contentDescription = null,
                                contentScale = ContentScale.Fit
                            )

                        }
                        Column(
                            Modifier
                                .weight(3.5f)
                                .padding(
                                    start = ListIemInnerPadding,
                                    top = ListIemInnerPadding,
                                    bottom = ListIemInnerPadding)
                        ) {
                            Text(
                                text = item.movies.original_title,
                                style = TextStyle(
                                    fontSize = ListTitleFontSize,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Text(
                                text = " Release: releaseDate",
                                style = TextStyle(
                                    fontSize = ListSubTitleFontSize,
                                    fontWeight = FontWeight.Bold
                                ),
                            )
                            Text(
                                text = item.movies.overview,
                                style = TextStyle(
                                    fontSize = ListDetailFontSize,
                                ),
                            )
                        }
                        // お気に入りボタン
                        Box(
                            Modifier
                                .weight(0.2f)
                                .padding(end = 2.dp)
                        ) {
                            IconButton(onClick = {
                                viewModel.updateFeb(
                                    sharedPreferences,
                                    item
                                )
                            }) {
                                Icon(
                                    imageVector = FavoriteIcon,
                                    contentDescription = "",
                                    tint = Red,
                                    modifier = Modifier.size(ListItemLoveIconSize)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

