package com.example.news_compose_c40.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.news_compose_c40.ui.theme.green
import com.example.news_compose_c40.widgets.NewsList
import kotlinx.serialization.Serializable

@Serializable
object SearchRoute

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchScreen(
    vm: SearchViewModel = hiltViewModel(),
    onNewsClick: (String, String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val searchTextFieldFocusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        searchTextFieldFocusRequester.requestFocus()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(vertical = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight(0.1f)
                .fillMaxWidth()
                .clip(RoundedCornerShape(bottomEnd = 50.dp, bottomStart = 50.dp))
                .background(color = green)
                .padding(horizontal = 30.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            TextField(
                value = vm.searchQuery,
                onValueChange = { vm.setSearchQuery(it) },
                shape = RoundedCornerShape(70.dp),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .focusRequester(searchTextFieldFocusRequester),
                placeholder = {
                    Text(text = "Search Articles", color = green, fontSize = 14.sp)
                },
                leadingIcon = {
                    IconButton(onClick = {
                        if (vm.searchQuery.isNotEmpty())
                            vm.setSearchQuery("")
                        else
                            focusManager.clearFocus()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Close Icon",
                            tint = green
                        )
                    }
                },
                trailingIcon = {
                    IconButton(onClick = {
                        vm.getNews()
                        focusManager.clearFocus()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search Icon",
                            tint = green
                        )
                    }
                }
            )
        }

        // Display the list of articles
        NewsList(vm.articlesList, shouldDisplayNoArticlesFound = false, loadingState = false) { title, sourceName ->
            onNewsClick(title, sourceName)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchScreenPrev() {
    SearchScreen { _, _ -> }
}
