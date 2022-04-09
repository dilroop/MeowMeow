package com.dsb.meowmeow.ui.screens.home

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dsb.meowmeow.Greeting
import com.dsb.meowmeow.ui.navigation.Screen

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun HomeScreen(@StringRes title: Int, viewModel: HomeViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState

    LoadCats(viewModel = viewModel)

    BackdropScaffold(
        appBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = title)) },
            )
        },
        backLayerContent = {
            Column(Modifier.padding(16.dp)) {
                Text(text = stringResource(id = title))
                Surface(
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Filters")
                }
            }
        },
        frontLayerContent = {
            Foreground(uiState = uiState, title = title)
        },
        backLayerBackgroundColor = MaterialTheme.colors.primaryVariant,
        frontLayerScrimColor = Color.Transparent,
        peekHeight = 46.dp,
        headerHeight = 56.dp,
    )
}

@Composable
internal fun LoadCats(viewModel: HomeViewModel) {
    LaunchedEffect(key1 = viewModel) {
        viewModel.loadCats()
    }
}

@Composable
fun Foreground(uiState: HomeScreenState, @StringRes title: Int) {
    when (uiState) {
        HomeScreenState.LOADING -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
        }
        HomeScreenState.LOADED -> {
            Column(Modifier.padding(16.dp)) {
                Text(text = stringResource(id = title))
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("gifs")
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewHomeScreen() {
    HomeScreen(Screen.Home.title)
}