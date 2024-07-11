package com.posite.modern.ui.wish

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.posite.modern.R
import kotlinx.coroutines.launch

@Composable
fun UpdateWishScreen(id: Long, viewModel: WishContractViewModel, navController: NavController) {
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val wish = viewModel.currentState
    Log.d("wish id", id.toString())
    LaunchedEffect(key1 = id) {
        if (id != 0L) {
            viewModel.getSingleWish(id)
        } else {
            viewModel.clearWish()
        }
    }
    Log.d("wish wish", wish.wish.wish.toString())
    val context = LocalContext.current
    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collect {
            when (it) {
                is WishContract.Effect.ShowWishBlankToast -> {
                    Toast.makeText(context, "Title or Description is empty", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
    Scaffold(
        topBar = {
            AppBarView(title = if (id != 0L) "Update Wish" else "Add Wish") {
                viewModel.clearWish()
                navController.navigateUp()
            }
        },
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { paddingValue ->
        Column(
            modifier = Modifier.padding(
                top = paddingValue.calculateTopPadding(),
                start = 12.dp,
                end = 12.dp
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            WishTextFields(
                "Wish Title",
                wish.wish.wish.title
            ) { viewModel.wishTitleChanged(it) }
            Spacer(modifier = Modifier.height(8.dp))
            WishTextFields(
                "Wish Description",
                wish.wish.wish.description
            ) { viewModel.wishDescriptionChanged(it) }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    Log.d("UpdateWishScreen", "id: $id, wish: ${wish.wish.wish}")
                    if (wish.wish.wish.title.isNotEmpty() && wish.wish.wish.description.isNotEmpty()) {
                        if (id == 0L) {
                            //add
                            viewModel.addWish(
                                wish.wish.wish
                            )
                            scope.launch {
                                /*snackBarHostState.showSnackbar(
                                    message = "Wish has been added!",
                                    duration = SnackbarDuration.Short
                                )*/
                            }
                        } else {
                            //update
                            viewModel.updateWish(
                                wish.wish.wish
                            )
                            scope.launch {
                                /*snackBarHostState.showSnackbar(
                                    message = "Wish has been updated!",
                                    duration = SnackbarDuration.Short
                                )*/
                            }
                        }
                        viewModel.clearWish()
                        navController.navigateUp()
                    } else {
                        Log.d("UpdateWishScreen", "Title or Description is empty")
                        viewModel.showWishBlankToast()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.sky_blue))
            ) {
                Text(text = if (id != 0L) "Update Wish" else "Add Wish")
            }

        }
    }
}


@Composable
fun WishTextFields(label: String, value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.Black,
            cursorColor = colorResource(id = R.color.sky_blue),
            unfocusedLabelColor = Color.Black,
            focusedLabelColor = Color.Black
        )
    )

}

@Composable
@Preview(showBackground = true)
fun WishTextFieldPreview() {
    WishTextFields(label = "Title", value = "Title") {}
}
