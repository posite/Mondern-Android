package com.posite.modern.ui.wish

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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.posite.modern.R
import com.posite.modern.data.local.entity.WishEntity
import kotlinx.coroutines.launch
import java.util.Date

@Composable
fun UpdateWishScreen(id: Long, viewModel: WishViewModel, navController: NavController) {
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    if (id != 0L) {
        viewModel.getWishById(id)
        viewModel.wishTitleChanged(viewModel.currentWish.value.title)
        viewModel.wishDescriptionChanged(viewModel.currentWish.value.description)
    }
    Scaffold(
        topBar = { AppBarView(title = if (id != 0L) "Update Wish" else "Add Wish") { navController.navigateUp() } },
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
                viewModel.wishTitle.value
            ) { viewModel.wishTitleChanged(it) }
            Spacer(modifier = Modifier.height(8.dp))
            WishTextFields(
                "Wish Description",
                viewModel.wishDescription.value
            ) { viewModel.wishDescriptionChanged(it) }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    if (viewModel.wishTitle.value.isNotEmpty() && viewModel.wishDescription.value.isNotEmpty()) {
                        if (id == 0L) {
                            //add
                            viewModel.addWish(
                                WishEntity(
                                    title = viewModel.wishTitle.value,
                                    description = viewModel.wishDescription.value,
                                    date = Date(System.currentTimeMillis())
                                )
                            )
                            scope.launch {
                                /*snackBarHostState.showSnackbar(
                                    message = "Wish has been added!",
                                    duration = SnackbarDuration.Short
                                )*/
                                finishEditWish(viewModel, navController)
                            }
                        } else {
                            //update
                            viewModel.updateWish(
                                WishEntity(
                                    id = id,
                                    title = viewModel.wishTitle.value,
                                    description = viewModel.wishDescription.value,
                                    date = Date(System.currentTimeMillis())
                                )
                            )
                            scope.launch {
                                /*snackBarHostState.showSnackbar(
                                    message = "Wish has been updated!",
                                    duration = SnackbarDuration.Short
                                )*/
                                finishEditWish(viewModel, navController)
                            }
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.sky_blue))
            ) {
                Text(text = if (id != 0L) "Update Wish" else "Add Wish")
            }

        }
    }
}

private fun finishEditWish(
    viewModel: WishViewModel,
    navController: NavController
) {
    viewModel.wishTitleChanged("")
    viewModel.wishDescriptionChanged("")
    navController.navigateUp()
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
