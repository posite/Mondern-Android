package com.posite.modern

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.posite.modern.ui.theme.ModernTheme

class ShoppingListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ModernTheme {
                ShoppingList()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingList() {
    var shoppingItems by remember { mutableStateOf(listOf<ShoppingItem>()) }
    var showDialog by remember { mutableStateOf(false) }
    var itemName by remember { mutableStateOf("") }
    var itemQuantity by remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
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
                .padding(top = 16.dp)
        ) {
            items(shoppingItems) { shoppingItem ->
                ShoppingListItems(shoppingItem, {}, {})
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
                                    id = shoppingItems.size,
                                    name = itemName,
                                    quantity = itemQuantity.toDouble().toInt()
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
                        onClick = { showDialog = false },
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
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                border = BorderStroke(1.dp, Color(0xFF00b0f0)),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(8.dp),
        /*verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween*/
    ) {
        Text(text = shoppingItem.name)
        Text(text = shoppingItem.quantity.toString())
        Icon(
            modifier = Modifier.clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = CustomIndication
            ) { onEditClick() },
            imageVector = Icons.Default.Edit,
            contentDescription = ""
        )
        Icon(
            modifier = Modifier.clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = CustomIndication
            ) { onDeleteClick() },
            imageVector = Icons.Default.Delete,
            contentDescription = ""
        )
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

@Preview(showBackground = true)
@Composable
fun ShoppingListPreview() {
    ModernTheme {
        ShoppingList()
    }
}