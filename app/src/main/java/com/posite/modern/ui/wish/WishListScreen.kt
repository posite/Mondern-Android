package com.posite.modern.ui.wish

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.posite.modern.R
import com.posite.modern.data.remote.model.wish.WishModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun WishListScreen(viewModel: WishViewModel, navController: NavController) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            AppBarView("WishList") {
                Toast.makeText(context, "Back clicked", Toast.LENGTH_SHORT).show()
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(WishScreen.AddWish.route)
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
                    bottom = it.calculateBottomPadding()
                )
        ) {
            /*items(10) {
                WishItem(
                    wish = WishModel(
                        title = "Title $it",
                        description = "Description $it"
                    )
                ) {
                    Toast.makeText(context, "Item $it clicked", Toast.LENGTH_SHORT).show()
                }
            }*/

        }
    }
}


@Composable
fun WishItem(wish: WishModel, onClick: () -> Unit) {
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