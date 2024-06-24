package com.posite.modern.ui.music

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.posite.modern.R
import com.posite.modern.ui.music.MusicScreen.MusicDrawerScreens.Account.DrawerList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MusicHomeScreen(
    viewModel: MusicViewModel,
    navController: NavHostController,
    drawerState: DrawerState
) {
    val title = viewModel.title.value
    val drawerList = DrawerList()
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                drawerList.forEach {
                    MusicDrawerItem(it, title, scope, drawerState)
                }
                Spacer(modifier = Modifier.padding(12.dp))
            }
        },
        drawerState = drawerState
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = title, modifier = Modifier.padding(start = 8.dp)) },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = colorResource(id = R.color.sky_blue)),
                    navigationIcon = {
                        if (title.contains("home").not()) {
                            IconButton(
                                modifier = Modifier.padding(start = 12.dp),
                                onClick = { }
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = ""
                                )
                            }
                        }
                    }
                )
            },


            ) { paddingValues ->
            Text(text = "Music Home Screen", modifier = Modifier.padding(paddingValues))
        }
    }
}

@Composable
private fun MusicDrawerItem(
    it: MusicScreen.MusicDrawerScreens,
    title: String,
    scope: CoroutineScope,
    drawerState: DrawerState
) {
    NavigationDrawerItem(
        label = { Text(text = it.title) },
        selected = title == it.title,
        onClick = {
            scope.launch { drawerState.close() }
        },
        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
        icon = {
            Icon(
                painter = painterResource(id = it.icon),
                contentDescription = null
            )
        }
    )
}