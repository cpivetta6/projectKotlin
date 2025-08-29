package com.example.buylistapp.model

data class GrShoppingList(
    val listid: String,
    val name: String,
    val createdat: String,
    val items: MutableList<GrItem> = mutableListOf()
)
