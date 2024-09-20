package com.example.portablemoviestudio.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.portablemoviestudio.data.SearchBarVerticalPadding
import com.example.portablemoviestudio.data.SearchIcon
import com.example.portablemoviestudio.data.SearchIconSize

// 映画検索バー
@Composable
fun SearchView(
    state: MutableState<TextFieldValue>
) {
    Box(Modifier.padding(vertical = SearchBarVerticalPadding)) {
        TextField(
            leadingIcon = {
                Icon(
                imageVector = SearchIcon,
                contentDescription = "",
                tint = Color.DarkGray,
                modifier = Modifier.size(SearchIconSize)
            )
            },
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .fillMaxWidth(),
            value = state.value,
            onValueChange = { value ->
                state.value = value
            }
        )
    }
}