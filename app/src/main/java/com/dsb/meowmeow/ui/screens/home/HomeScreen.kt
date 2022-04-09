package com.dsb.meowmeow.ui.screens.home

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Tune
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dsb.meowmeow.R
import com.dsb.meowmeow.ui.components.FilterChip
import com.dsb.meowmeow.ui.components.GifImage
import com.dsb.meowmeow.ui.navigation.Screen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun HomeScreen(@StringRes title: Int, viewModel: HomeViewModel = hiltViewModel()) {
    LoadCats(viewModel = viewModel)

    val uiState by viewModel.uiState
    val backdropState = rememberBackdropScaffoldState(initialValue = BackdropValue.Concealed)

    BackdropScaffold(
        scaffoldState = backdropState,
        appBar = {
            HomeScreenAppbar(backdropState = backdropState, title = title)
        },
        backLayerContent = {
            AmazingCatFilters(viewModel = viewModel)
        },
        frontLayerContent = {
            Foreground(uiState = uiState)
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreenAppbar(backdropState: BackdropScaffoldState, @StringRes title: Int) {
    val scope = rememberCoroutineScope()
    TopAppBar(
        title = { Text(text = stringResource(id = title)) },
        actions = {
            Icon(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .clickable {
                        scope.launch {
                            with(backdropState) { if (isConcealed) reveal() else conceal() }
                        }
                    },
                imageVector = Icons.Default.FilterAlt,
                contentDescription = null,
            )
        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun AmazingCatFilters(viewModel: HomeViewModel) {
    val animatedImageFilter by viewModel.animatedImageFilter
    val staticImageFilter by viewModel.staticImageFilter
    val noHatsFilter by viewModel.noHatsFilter
    val onlyHatsFilter by viewModel.onlyHatsFilter
    val scrollState = rememberScrollState()

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.horizontalScroll(state = scrollState)
    ) {
        Icon(Icons.Filled.Tune, "Filters")
        FilterChip(
            onClick = { viewModel.updateAnimatedImageFilter(enable = !animatedImageFilter.enable) },
            isSelected = animatedImageFilter.enable,
            text = R.string.cat_screen_image_type_animated,
        )

        FilterChip(
            onClick = { viewModel.updateStaticImageFilter(enable = !staticImageFilter.enable) },
            isSelected = staticImageFilter.enable,
            text = R.string.cat_screen_image_type_static
        )

        FilterChip(
            onClick = { viewModel.updateOnlyHatsImageFilter(enable = !onlyHatsFilter.enable) },
            isSelected = onlyHatsFilter.enable,
            text = R.string.cat_screen_filter_only_hats
        )

        FilterChip(
            onClick = { viewModel.updateNoHatsImageFilter(enable = !noHatsFilter.enable) },
            isSelected = noHatsFilter.enable,
            text = R.string.cat_screen_filter_no_hats
        )
    }
}

@Composable
fun Foreground(uiState: HomeScreenState) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        when (uiState) {
            HomeScreenState.Loading -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(Modifier.align(Alignment.Center))
                }
            }
            is HomeScreenState.Content -> {
                val cats = uiState.cats
                if (cats.isEmpty()) {
                    EmptyList()
                } else {
                    Text(text = stringResource(id = R.string.cat_screen_item_count, cats.size))
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 16.dp)
                    ) {
                        items(cats.size) { index ->
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Center
                            ) {
                                GifImage(url = cats[index].url)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EmptyList() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 64.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(Icons.Default.Pets, "", Modifier.size(64.dp), tint = MaterialTheme.colors.secondary)
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(id = R.string.cat_screen_filter_zero_results_title),
            fontSize = MaterialTheme.typography.subtitle1.fontSize,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = stringResource(id = R.string.cat_screen_filter_zero_results_message),
            fontSize = MaterialTheme.typography.subtitle2.fontSize,
            textAlign = TextAlign.Center,
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
@Preview(showBackground = true)
fun PreviewEmptyList() {
    EmptyList()
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
//@Preview(showBackground = true)
fun PreviewFilters() {
    AmazingCatFilters(viewModel = hiltViewModel())
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
//@Preview(showBackground = true)
fun PreviewForeground() {
    val state = rememberBackdropScaffoldState(initialValue = BackdropValue.Concealed)
    HomeScreenAppbar(backdropState = state, title = Screen.Home.title)
}

@Composable
//@Preview(showBackground = true)
fun PreviewHomeScreen() {
    HomeScreen(Screen.Home.title)
}