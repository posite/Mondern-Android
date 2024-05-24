package com.posite.modern

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.posite.modern.ui.theme.ModernTheme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ModernTheme {
                ConvertUnit()
            }
        }
    }
}

@Composable
fun ConvertUnit() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
    ) {
        val inputValue = remember {
            mutableStateOf("")
        }
        val outputValue = remember {
            mutableStateOf("")
        }
        val inputUnit = remember {
            mutableStateOf("")
        }
        val outputUnit = remember {
            mutableStateOf("")
        }
        val inputTypeMenuExpand = remember {
            mutableStateOf(false)
        }
        val outputTypeMenuExpand = remember {
            mutableStateOf(false)
        }
        val conversionFacter = remember {
            mutableStateOf(0.0)
        }
        val inputFactor = remember {
            mutableStateOf(1.0)
        }
        val outputFactor = remember {
            mutableStateOf(1.0)
        }

        fun convertUnit() {
            conversionFacter.value = inputFactor.value / outputFactor.value
            val doubleValue = inputValue.value.toDoubleOrNull() ?: 0.0
            val reuslt = (doubleValue * conversionFacter.value *100.0).roundToInt()  /100.0
            outputValue.value = reuslt.toString()
        }

        Text(text = "Unit Converter")
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = inputValue.value,
            onValueChange = { inputValue.value = it },
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text(text = "Enter Number") },
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            Box {
                Button(onClick = { inputTypeMenuExpand.value = true }) {
                    Text(text = "Select")
                    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "")
                }
                DropdownMenu(
                    expanded = inputTypeMenuExpand.value,
                    onDismissRequest = { inputTypeMenuExpand.value = false }) {
                    DropdownMenuItem(
                        text = { Text(text = "CentiMeter") },
                        onClick = {
                            inputUnit.value = "CentiMeter"
                            inputTypeMenuExpand.value = false
                            inputFactor.value = 1.0
                            convertUnit()
                        })
                    DropdownMenuItem(
                        text = { Text(text = "Meter") },
                        onClick = {
                            inputUnit.value = "Meter"
                            inputTypeMenuExpand.value = false
                            inputFactor.value = 100.0
                            convertUnit()
                        })
                    DropdownMenuItem(
                        text = { Text(text = "Feet") },
                        onClick = {
                            inputUnit.value = "Feet"
                            inputTypeMenuExpand.value = false
                            inputFactor.value = 30.48
                            convertUnit()
                        })
                }
            }
            Spacer(modifier = Modifier.width(12.dp))
            Box {
                Button(onClick = { outputTypeMenuExpand.value = true }) {
                    Text(text = "Select")
                    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "")
                }
                DropdownMenu(
                    expanded = outputTypeMenuExpand.value,
                    onDismissRequest = { outputTypeMenuExpand.value = false }) {
                    DropdownMenuItem(
                        text = { Text(text = "CentiMeter") },
                        onClick = {
                            outputUnit.value = "CentiMeter"
                            outputTypeMenuExpand.value = false
                            outputFactor.value = 1.0
                            convertUnit()
                        })
                    DropdownMenuItem(
                        text = { Text(text = "Meter") },
                        onClick = {
                            outputUnit.value = "Meter"
                            outputTypeMenuExpand.value = false
                            outputFactor.value = 100.0
                            convertUnit()
                        })
                    DropdownMenuItem(
                        text = { Text(text = "Feet") },
                        onClick = {
                            outputUnit.value = "Feet"
                            outputTypeMenuExpand.value = false
                            outputFactor.value = 30.48
                            convertUnit()
                        })
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Convert Results: ${outputValue.value}")
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ModernTheme {
        ConvertUnit()
    }
}