package com.dsb.meowmeow.ui.screens.home

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dsb.meowmeow.Greeting
import com.dsb.meowmeow.ui.navigation.Screen

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(@StringRes title: Int) {
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
            Column(Modifier.padding(16.dp)) {
                Text(text = stringResource(id = title))
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("gifs")
                }
            }
        },
        backLayerBackgroundColor = MaterialTheme.colors.primaryVariant,
        frontLayerScrimColor = Color.Transparent,
        peekHeight = 46.dp,
        headerHeight = 56.dp,
    )
}

@Composable
@Preview(showBackground = true)
fun PreviewHomeScreen() {
    HomeScreen(Screen.Home.title)
}