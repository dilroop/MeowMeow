package com.dsb.meowmeow.ui.navigation

import androidx.annotation.StringRes
import com.dsb.meowmeow.R

sealed class Screen(val route: String, @StringRes val title: Int) {

    object Home : Screen(
        route = "main",
        title = R.string.app_name
    )
}