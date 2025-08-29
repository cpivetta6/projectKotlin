package com.example.buylistapp.model

data class GrUser (
    var userid: String,
    val lists: MutableList<GrShoppingList> = mutableListOf()
)
