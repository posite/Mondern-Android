package com.posite.modern.ui.wish

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.posite.modern.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarView(title: String, onBackClicked: () -> Unit = {}) {
    TopAppBar(
        title = { Text(text = title, modifier = Modifier.padding(start = 8.dp)) },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = colorResource(id = R.color.sky_blue)),
        navigationIcon = {
            if (title.contains("WishList").not()) {
                IconButton(
                    modifier = Modifier.padding(start = 12.dp),
                    onClick = { onBackClicked() }
                ) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "")
                }
            }
        }
    )
}