package com.posite.modern.shopping

data class ShoppingItem(
    val id: Int,
    var name: String,
    var quantity: Number,
    var itemEditing: Boolean = false
)
