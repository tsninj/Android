
@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.translator

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun TranslateTopAppBar(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    imageVector: ImageVector
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onPrimary
            )
        },
        modifier = modifier,
        actions = {
            IconButton(onClick = onClick) {
                Icon(
                    imageVector = imageVector,
                    contentDescription = "Config",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color(0xFF692FF0)
        )
    )
}



