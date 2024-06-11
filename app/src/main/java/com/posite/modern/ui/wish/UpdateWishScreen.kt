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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.posite.modern.R

@Composable
fun UpdateWishScreen(id: Long, viewModel: WishViewModel, navController: NavController) {
    Scaffold(
        topBar = { AppBarView(title = if (id == 0L) "Update Wish" else "Add Wish") { navController.navigateUp() } },
        modifier = Modifier.fillMaxSize()
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

                        } else {

                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.sky_blue))
            ) {
                Text(text = if (id == 0L) "Update Wish" else "Add Wish")
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
