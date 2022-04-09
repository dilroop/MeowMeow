package com.dsb.meowmeow.ui.components

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FilterChip(
    onClick: () -> Unit,
    isSelected: Boolean,
    @StringRes text: Int
) {
    Chip(
        onClick = { onClick() },
        leadingIcon = {
            AnimatedVisibility(visible = isSelected) {
                Icon(Icons.Filled.Done, contentDescription = "")
            }
        },
    ) {
        Text(
            text = stringResource(id = text),
            fontSize = MaterialTheme.typography.body2.fontSize
        )
    }
}