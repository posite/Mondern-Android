package com.posite.modern.ui.shopping

data class ShoppingItem(
    val id: Int,
    var name: String,
    var quantity: Number,
    var itemEditing: Boolean = false
)
