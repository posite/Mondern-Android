package com.posite.modern.ui.wish

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.posite.modern.R
import com.posite.modern.data.local.entity.WishEntity

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun WishListScreen(viewModel: WishContractViewModel, navController: NavController) {
    val context = LocalContext.current
    viewModel.getWishes()
    val wishes = viewModel.currentState
    Scaffold(
        topBar = {
            AppBarView("Wish List") {
                Toast.makeText(context, "Back clicked", Toast.LENGTH_SHORT).show()
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(WishScreen.AddWish.route + "/0L")
                },
                contentColor = Color.White,
                containerColor = colorResource(id = R.color.sky_blue)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "")
            }
        }) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = 12.dp,
                    end = 12.dp,
                    top = it.calculateTopPadding(),
                    bottom = it.calculateBottomPadding() + 16.dp
                )
        ) {
            items(wishes.wishList.wishList, key = { wish -> wish.id }) { wish ->
                val dismissState = rememberSwipeToDismissBoxState(
                    confirmValueChange = { dismissValue ->
                        if (dismissValue == SwipeToDismissBoxValue.EndToStart) {
                            viewModel.deleteWish(wish)
                        } else {
                            false
                        }
                        true
                    }
                )
                SwipeToDismissBox(state = dismissState, backgroundContent = {
                    val color by animateColorAsState(
                        if (dismissState.dismissDirection == SwipeToDismissBoxValue.EndToStart) Color.Red else Color.Transparent,
                        label = ""
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(shape = RoundedCornerShape(15.dp), color = color)
                            .padding(horizontal = 16.dp),
                        contentAlignment = Alignment.CenterEnd,
                    ) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = Color.White
                        )
                    }
                }, enableDismissFromStartToEnd = false) {
                    WishItem(wish) {
                        navController.navigate(WishScreen.AddWish.route + "/${wish.id}")
                    }
                }


            }

        }
    }
}


@Composable
fun WishItem(wish: WishEntity, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() }, colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
            Text(text = wish.title, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = wish.description)

        }
    }
}