package com.posite.modern.ui.music

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.posite.modern.R

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MusicHomeView(
    viewModel: MusicViewModel,
    navController: NavHostController
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Home",
                        modifier = Modifier.padding(start = 8.dp)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = colorResource(id = R.color.sky_blue))
            )
        },
        bottomBar = {
            BottomBarView(viewModel, navController)
        },
    ) { paddingValues ->

        Text(
            text = "Music Home Screen",
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MusicBrowseView(
    viewModel: MusicViewModel,
    navController: NavHostController
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Browse",
                        modifier = Modifier.padding(start = 8.dp)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = colorResource(id = R.color.sky_blue))
            )
        },
        bottomBar = {
            BottomBarView(viewModel, navController)
        },
    ) { paddingValues ->

        Text(
            text = "Music Browse Screen",
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MusicLibraryView(
    viewModel: MusicViewModel,
    navController: NavHostController
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Library",
                        modifier = Modifier.padding(start = 8.dp)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = colorResource(id = R.color.sky_blue))
            )
        },
        bottomBar = {
            BottomBarView(viewModel, navController)
        },
    ) { paddingValues ->

        Text(
            text = "Music Library Screen",
            modifier = Modifier.padding(paddingValues)
        )
    }
}