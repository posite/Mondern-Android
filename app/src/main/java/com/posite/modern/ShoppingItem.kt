package com.posite.modern

data class ShoppingItem(
    val id: Int,
    var name: String,
    var quantity: Int,
    var idEdited: Boolean = false
)
