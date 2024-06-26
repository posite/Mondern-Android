package com.posite.modern.ui.shopping

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Indication
import androidx.compose.foundation.IndicationInstance
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import com.posite.modern.ui.theme.ModernTheme
import com.posite.modern.util.LocationUtil
import com.posite.modern.util.PermissionUtil

class ShoppingListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val viewModel by viewModels<ShoppingViewModelImpl>()
        setContent {
            ModernTheme {
                ShoppingNavigation(viewModel)
            }
        }
    }
}

@Composable
fun ShoppingNavigation(viewModel: ShoppingViewModel) {
    val navController = rememberNavController()
    val selectLocation = viewModel.locationButtonSelect.value
    var count = 0
    NavHost(navController = navController, startDestination = "shoppinglist") {
        composable("shoppinglist") {
            ShoppingList(
                viewModel,
                LocationUtil(LocalContext.current),
                navController,
                LocalContext.current,
                viewModel.address.value.firstOrNull()?.formatted_address ?: "No Address"
            )
        }
        dialog("LocationScreen") { backStack ->
            viewModel.location.value?.let { currentLocation->
                FindAddressScreen(currentLocation, onLocationSelected = { location ->
                    viewModel.fetchAddress("${location.latitude},${location.longitude}")
                    viewModel.locationButtonSelect()
                    navController.popBackStack()
                })
            }
        }
    }

    ShoppingList(
        viewModel,
        LocationUtil(LocalContext.current),
        navController,
        LocalContext.current,
        ""
    )
}

@Composable
fun ShoppingList(
    viewModel: ShoppingViewModel,
    util: LocationUtil,
    navController: NavController,
    context: Context,
    address: String
) {
    var shoppingItems by remember { mutableStateOf(listOf<ShoppingItem>()) }
    var showDialog by remember { mutableStateOf(false) }
    var itemName by remember { mutableStateOf("") }
    var itemQuantity by remember { mutableStateOf("") }
    var standardId by remember { mutableStateOf(0) }
    val location = viewModel.location.value

    val requestPermission =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestMultiplePermissions(),
            onResult = { permissions ->
                if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true && permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true) {
                    //util.requestLocationUpdate(viewModel)
                } else {
                    requireLocationPermission(context)
                }
            })

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
        if (PermissionUtil.hasLocationPermission(context)) {
            //util.requestLocationUpdate(viewModel)
        } else {
            Text(text = "Requesting Location Permission")
            SideEffect {
                requestPermission.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                )
            }
            Log.d("request", requestPermission.contract.toString())
        }
        Icon(
            imageVector = Icons.Default.AddCircle,
            contentDescription = "",
            tint = Color(0xFF00b0f0),
            modifier = Modifier
                .height(52.dp)
                .width(52.dp)
                .align(Alignment.BottomEnd)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = CustomIndication
                ) {
                    showDialog = true
                }
        )

        LazyColumn(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 24.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(shoppingItems) { shoppingItem ->
                if (shoppingItem.itemEditing) {
                    ShoppingItemEditor(currentItem = shoppingItem) {
                        shoppingItems = shoppingItems.map { item ->
                            if (item.id == it.id) {
                                it
                            } else {
                                item
                            }
                        }
                    }
                } else {
                    ShoppingListItems(shoppingItem, onEditClick = {
                        shoppingItems =
                            shoppingItems.map { it.copy(itemEditing = it.id == shoppingItem.id) }
                    }, onDeleteClick = {
                        shoppingItems = shoppingItems - shoppingItem
                    })
                }
            }
        }
    }

    if (showDialog) {
        AlertDialog(onDismissRequest = { showDialog = false },
            confirmButton = {
                Row(modifier = Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                    Button(
                        onClick = {
                            if (itemName.isNotEmpty() && itemQuantity.isNotEmpty()) {
                                shoppingItems = shoppingItems + ShoppingItem(
                                    id = standardId++,
                                    name = itemName,
                                    quantity = itemQuantity.toDouble().toInt(),
                                    address = viewModel.address.value.firstOrNull()?.formatted_address ?: "No Address"
                                )
                                itemName = ""
                                itemQuantity = ""
                                showDialog = false
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00b0f0))
                    ) {
                        Text(text = "Add")
                    }
                    Button(
                        onClick = {
                            itemName = ""
                            itemQuantity = ""
                            showDialog = false
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00b0f0))
                    ) {
                        Text(text = "Cancel")
                    }
                }
            },
            title = {
                Text(
                    text = "Add Shopping Item"
                )
            }, text = {
                Column {
                    OutlinedTextField(
                        value = itemName,
                        onValueChange = { itemName = it },
                        label = { Text("Item Name") },
                        maxLines = 1,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = itemQuantity,
                        onValueChange = { itemQuantity = it },
                        label = { Text("Quantity") },
                        maxLines = 1,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)

                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = {
                            util.requestLocationUpdate(viewModel)
                            navController.navigate("LocationScreen") {
                                this.launchSingleTop
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00b0f0))
                    ) {
                        Text(text = "find address")
                    }
                }

            }, containerColor = Color(0xFFeef0ff)
        )


    }
}

@Composable
fun ShoppingListItems(
    shoppingItem: ShoppingItem,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                border = BorderStroke(1.dp, Color(0xFF00b0f0)),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 12.dp, vertical = 8.dp),
        /*verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween*/
    ) {
        Column {
            Row {
                Text(text = shoppingItem.name)
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = shoppingItem.quantity.toString())
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row {
                Icon(imageVector = Icons.Default.LocationOn, contentDescription = "")
                Text(text = shoppingItem.address)
            }
        }
        Row(modifier = Modifier.align(alignment = Alignment.CenterEnd)) {
            IconButton(onClick = onEditClick) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "")
            }
            //Spacer(modifier = Modifier.width(4.dp))
            IconButton(onClick = onDeleteClick) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "")
            }
        }
    }
}

@Composable
fun ShoppingItemEditor(currentItem: ShoppingItem, onItemChange: (ShoppingItem) -> Unit) {
    var itemName by remember { mutableStateOf(currentItem.name) }
    var itemQuantity by remember { mutableStateOf(currentItem.quantity.toString()) }
    var itemEdited by remember { mutableStateOf(currentItem.itemEditing) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp),
    ) {
        Column(modifier = Modifier.align(alignment = Alignment.CenterStart)) {
            OutlinedTextField(
                modifier = Modifier.width(200.dp),
                value = itemName,
                onValueChange = { itemName = it },
                label = { Text("Item Name") },
                maxLines = 1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                modifier = Modifier.width(200.dp),
                value = itemQuantity,
                onValueChange = { itemQuantity = it },
                label = { Text("Quantity") },
                maxLines = 1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
        Button(modifier = Modifier.align(alignment = Alignment.CenterEnd), onClick = {
            if (itemName.isNotEmpty() && itemQuantity.isNotEmpty()) {
                itemEdited = false
                onItemChange(
                    currentItem.copy(
                        name = itemName,
                        quantity = itemQuantity.toDouble().toInt(),
                        itemEditing = itemEdited
                    )
                )
            }
        }) {
            Text(text = "Save")
        }
    }
}


object CustomIndication : Indication {
    private class DefaultDebugIndicationInstance(
        private val isPressed: State<Boolean>,
    ) : IndicationInstance {
        override fun ContentDrawScope.drawIndication() {
            drawContent()
            if (isPressed.value) {
                drawCircle(color = Color.Gray.copy(alpha = 0.1f))
            }
        }
    }

    @Composable
    override fun rememberUpdatedInstance(interactionSource: InteractionSource): IndicationInstance {
        val isPressed = interactionSource.collectIsPressedAsState()
        return remember(interactionSource) {
            DefaultDebugIndicationInstance(isPressed)
        }
    }
}

fun requireLocationPermission(context: Context) {
    val requestPermission =
        ActivityCompat.shouldShowRequestPermissionRationale(
            context as ShoppingListActivity,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) || ActivityCompat.shouldShowRequestPermissionRationale(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    if (requestPermission) {
        Toast.makeText(context, "Location Permission Required", Toast.LENGTH_SHORT).show()
    } else {

    }
}


@Preview(showBackground = true)
@Composable
fun ShoppingListPreview() {
    ModernTheme {
        val navController = rememberNavController()
        ShoppingList(
            ShoppingViewModelImpl(),
            LocationUtil(LocalContext.current),
            navController,
            LocalContext.current,
            ""
        )
    }
}