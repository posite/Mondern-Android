package com.posite.modern.ui.music

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.posite.modern.R
import com.posite.modern.ui.music.MusicScreen.MusicBottomScreen.Browse.BottomList
import com.posite.modern.ui.music.MusicScreen.MusicDrawerScreens.Account.DrawerList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun MusicView(
    viewModel: MusicViewModel,
    navController: NavHostController,
    drawerState: DrawerState
) {
    val title = viewModel.currentDrawerScreen.value.title
    val drawerList = DrawerList()
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet(modifier = Modifier.fillMaxWidth(0.6f)) {
                Spacer(modifier = Modifier.height(12.dp))
                drawerList.forEach {
                    MusicDrawerItem(it, title, scope, viewModel, drawerState, navController)
                }
                Spacer(modifier = Modifier.padding(12.dp))
            }
        },
        drawerState = drawerState,
    ) {
        MusicNavigation(viewModel, navController)
    }
}


@Composable
fun BottomBarView(viewModel: MusicViewModel, navController: NavHostController) {
    NavigationBar(containerColor = colorResource(id = R.color.sky_blue)) {
        val bottomList = BottomList()
        bottomList.forEach {
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = it.icon),
                        contentDescription = null
                    )
                },
                label = { Text(text = it.title) },
                selected = false,
                onClick = {
                    viewModel.onBottomBarClicked(it)
                    navController.navigate(it.route)
                }
            )
        }
    }
}

@Composable
private fun MusicDrawerItem(
    musicDrawerScreen: MusicScreen.MusicDrawerScreens,
    title: String,
    scope: CoroutineScope,
    viewModel: MusicViewModel,
    drawerState: DrawerState,
    navController: NavHostController
) {
    NavigationDrawerItem(
        label = { Text(text = musicDrawerScreen.title) },
        selected = title == musicDrawerScreen.title,
        onClick = {
            scope.launch {
                drawerState.close()
                if (musicDrawerScreen == MusicScreen.MusicDrawerScreens.Home) {
                    viewModel.onDrawerItemClicked(musicDrawerScreen)
                    navController.navigate(viewModel.currentBottomScreen.value.route)
                } else {
                    viewModel.onDrawerItemClicked(musicDrawerScreen)
                    navController.navigate(musicDrawerScreen.route)
                }
            }
        },
        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
        icon = {
            Icon(
                painter = painterResource(id = musicDrawerScreen.icon),
                contentDescription = null
            )
        }
    )
}